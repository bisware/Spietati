package com.bisware.spietati.demo;

import com.bisware.spietati.bean.Recensione;
import com.bisware.spietati.bean.SchedaFilm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParserDemo {

    private final static String urlRecensioni = "http://www.spietati.it/z_recensioni.asp";
    private final static String urlScheda = "http://www.spietati.it/z_scheda_dett_film.asp?idFilm=";

    public static void main(String... args) throws Exception {

        //testElenco();
        //testRecensione("4830");
        testRicerca2("la grande bellezza");
    }

    private static void testRicerca2(String queryRicerca) throws Exception {
        String key = "AIzaSyC8Sp8lJkKNOmcKilbW3wejjRLz-ktc_G0";
        String qry = "la+grande+bellezza";
        String cx = "partner-pub-4445160703635697%3Avo6do6-qhci";
        String siteSearch = "www.spietati.it";

        URL url = new URL(
                "https://www.googleapis.com/customsearch/v1?key="+key +
                        "&cx="+ cx + "&q="+ qry + "&alt=json");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {

            if(output.contains("\"link\": \"")){
                String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
                System.out.println(link);       //Will print the google search links
            }
        }

        conn.disconnect();
    }

    private static void testRecensione(String idfilm)throws IOException {

        Document doc = Jsoup.connect(urlScheda +idfilm).get();

        String titolo = doc.select("#mainContent h4").text();
        String regista = doc.select(".regista").text();

        SchedaFilm film = new SchedaFilm();
        film.setTitolo(titolo);
        film.setRegista(regista);

        Recensione recensione = new Recensione();

        Elements righe = doc.select("#schedaFilmContent p");
        String corpo = "";
        for (Element p : righe) {
            corpo += p.text() + "\n";
        }
        recensione.setCorpo(corpo);

        String autore = doc.select(".nomeVoto").text();
        String voto = doc.select(".voto").text();
        String data = doc.select(".dataVoto").text();

        recensione.setAutore(autore);
        recensione.setVoto(voto);
        recensione.setData(data);

        film.setRecensione(recensione);

        System.out.println(film);

    }

    private static void testElenco() throws IOException {
        Document document = Jsoup.connect(urlRecensioni).get();

        Elements base = document.select(".tableRecensioni");

        for (Element e: base.select(".redazioneTdTitle, a"))
        {
            if (e.tagName().equals("a")) {
                System.out.println(e.text());
                System.out.println(e.attr("href"));
            } else {
                System.out.println("MESE: " + e.text());
            }
        }
    }

}
