package com.bisware.spietati.demo;

import com.bisware.spietati.bean.Recensione;
import com.bisware.spietati.bean.SchedaFilm;
import com.bisware.spietati.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParserDemo {

    private final static String urlRecensioni = "http://www.spietati.it/z_recensioni.asp";
    private final static String urlScheda = "http://www.spietati.it/z_scheda_dett_film.asp?idFilm=";

    public static void main(String[] args) throws Exception {

        //testElenco();
        //testRecensione("4830");
        testRicerca2("la grande bellezza");
    }

    private static void testRicerca2(String queryRicerca) throws Exception {
        final String titoloDaRimuovere = "Spietati.it - ";

        String key = "AIzaSyC8Sp8lJkKNOmcKilbW3wejjRLz-ktc_G0";
        String qry = "la+grande+bellezza";
        String cx = "partner-pub-4445160703635697%3Avo6do6-qhci";

        String jsonString ="";

//        URL url = new URL(
//                "https://www.googleapis.com/customsearch/v1?key="+key +
//                        "&cx="+ cx + "&q="+ qry + "&alt=json");
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept", "application/json");
//
//        try {
//            InputStream is = conn.getInputStream();
//            jsonString = Utils.getStringFromInputStream(is);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        } finally {
//            conn.disconnect();
//        }

        InputStream inputStream = new FileInputStream("D:\\dati\\demo.txt");
        try {
            jsonString = Utils.getStringFromInputStream(inputStream);
        } finally {
            inputStream.close();
        }

        try {
            //JSONObject jsonMain = new JSONObject(jsonString);
            //JSONArray jsonArray = (JSONArray) jsonMain.get("items");

            JSONArray jsonArray = new JSONArray(jsonString);
            //JSONArray jsonArray = new JSONArray("[" + jsonString +"]");
            System.out.println("Number of entries " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                System.out.println(jsonObject.getString("title").replace(titoloDaRimuovere, ""));
                System.out.println(jsonObject.getString("link"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
