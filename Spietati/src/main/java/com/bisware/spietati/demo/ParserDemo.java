package com.bisware.spietati.demo;

import com.bisware.spietati.bean.Recensione;
import com.bisware.spietati.bean.SchedaFilm;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParserDemo {

    private final static String urlRecensioni = "http://www.spietati.it/z_recensioni.asp";
    private final static String urlScheda = "http://www.spietati.it/z_scheda_dett_film.asp?idFilm=";

    public static void main(String... args) throws Exception {

        //testElenco();
        testRecensione("4830");
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
