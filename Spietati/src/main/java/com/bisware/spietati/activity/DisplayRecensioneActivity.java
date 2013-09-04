package com.bisware.spietati.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bisware.spietati.R;
import com.bisware.spietati.adapter.RecensioniAdapter;
import com.bisware.spietati.bean.ElencoRecensioniItem;
import com.bisware.spietati.bean.Recensione;
import com.bisware.spietati.bean.SchedaFilm;
import com.bisware.spietati.utils.Costanti;
import com.bisware.spietati.utils.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class DisplayRecensioneActivity extends Activity {

    private WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recensione);

        Intent intent = getIntent();

        //String idFilm = intent.getStringExtra(MainActivity.EXTRA_IDFILM);
        //String url = Costanti.URL_BASE_SPIETATI + Costanti.URL_SCHEDA_DETT + idFilm;

        String urlSchedaFilm = intent.getStringExtra(MainActivity.EXTRA_IDFILM);
        String urlDaCaricare = Costanti.URL_BASE_SPIETATI + urlSchedaFilm;

        Toast.makeText(this, "Apertura recensione in corso...", Toast.LENGTH_LONG).show();

        new ParseSchedaFilmTask().execute(urlSchedaFilm);

//        browser = (WebView) findViewById(R.id.webView);
//
//        browser.getSettings().setJavaScriptEnabled(true);
//        browser.loadUrl(urlDaCaricare);
    }

    private class ParseSchedaFilmTask extends AsyncTask<String, Void, SchedaFilm> {

        private Exception e;

        @Override
        protected SchedaFilm doInBackground(String... urlSchedaFilm)  {
            SchedaFilm film = new SchedaFilm();

            try {
                Document doc = Jsoup.connect(Costanti.URL_BASE_SPIETATI + urlSchedaFilm[0]).get();

                String locandina = doc.select("#schedaFilm img").attr("src");
                String titolo = doc.select("#mainContent h4").text();
                String regista = doc.select(".regista").text();

                film.setLocandina(locandina);
                film.setTitolo(titolo);
                film.setRegista(regista);

                Recensione recensione = new Recensione();

                Elements righe = doc.select("#schedaFilmContent p");
                List<String> paragrafi = new ArrayList<String>();
                String trama = "";
                boolean isTrama = true;
                for (Element p : righe) {
                    if (isTrama) {
                        trama = p.text();
                        isTrama = false;
                    } else {
                        paragrafi.add(p.text());
                    }
                }
                String corpo = TextUtils.join("\n\n", paragrafi.toArray());

                recensione.setTrama(trama);
                recensione.setCorpo(corpo);

                String autore = doc.select(".nomeVoto").text();
                String voto = doc.select(".voto").text();
                String data = doc.select(".dataVoto").text();

                recensione.setAutore(autore);
                recensione.setVoto(voto);
                recensione.setData(data);

                film.setRecensione(recensione);
            } catch (IOException e) {
                this.e = e;

                Toast.makeText(getApplicationContext(),
                        "Errore durante il parsing della scheda" , Toast.LENGTH_LONG).show();
            }

            return film;
        }


        @Override
        protected void onPostExecute(SchedaFilm schedaFilm) {

            final ImageView imageLocandina = (ImageView)findViewById(R.id.imageSchedaLocandina);
            final TextView textTitolo = (TextView)findViewById (R.id.textSchedaTitolo);
            final TextView textRegista = (TextView)findViewById (R.id.textSchedaRegista);
            final TextView textTrama = (TextView)findViewById (R.id.textSchedaTrama);
            final TextView textCorpo = (TextView)findViewById (R.id.textSchedaCorpo);
            final TextView textAutore = (TextView)findViewById (R.id.textSchedaAutore);

            final String src = Costanti.URL_BASE_SPIETATI + schedaFilm.getLocandina();

            new Thread(new Runnable() {
                public void run() {
                    try {
                        final Bitmap b = Utils.getBitmapFromURL(src);
                        imageLocandina.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    imageLocandina.setImageBitmap(b);
                                } catch (Exception e1) {
                                    System.out.println(e1.getMessage());
                                }
                            }
                        });
                    } catch (Exception e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            }).start();


            textTitolo.setText(schedaFilm.getTitolo());
            textRegista.setText(schedaFilm.getRegista());

            Recensione recensione =schedaFilm.getRecensione();
            textTrama.setText(recensione.getTrama());
            textCorpo.setText(recensione.getCorpo());
            textAutore.setText(recensione.getAutore() +
                    " - "+ recensione.getVoto() + " " + recensione.getData());
        }
    }
}
