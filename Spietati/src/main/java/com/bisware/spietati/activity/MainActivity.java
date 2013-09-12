package com.bisware.spietati.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bisware.spietati.R;
import com.bisware.spietati.adapter.RecensioniAdapter;
import com.bisware.spietati.bean.ItemElencoRecensioni;
import com.bisware.spietati.bean.ItemFilm;
import com.bisware.spietati.utils.Costanti;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



public class MainActivity extends Activity {

    public final static String EXTRA_QUERY_RICERCA = "com.bisware.spietati.QUERY_RICERCA";
    public final static String EXTRA_IDFILM = "com.gmail.superbisco.spietati.IDRECENSIONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ricerca
        Button buttonSearch = (Button)findViewById(R.id.btnCerca);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginSearch();
            }
        });

        // aggiornamento elenco
        Button buttonAggiorna = (Button)findViewById(R.id.btnAggiornaElenco);
        buttonAggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aggiornaElenco(view);
            }
        });
    }

    public void beginSearch() {
        EditText editText = (EditText) findViewById(R.id.txtRicerca);

        String queryRicerca = editText.getText().toString();

        Intent intent = new Intent(this, RicercaActivity.class);
        intent.putExtra(EXTRA_QUERY_RICERCA, queryRicerca);
        //Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        //intent.putExtra(SearchManager.SUGGEST_URI_PATH_QUERY, queryRicerca);
        startActivity(intent);

        //showMessage("Ricerca " + queryRicerca + " in corso...");
    }

    private void aggiornaElenco(View view) {
        // elenzo ultime recensioni
        new DownloadRecensioniTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void apriRecensione(ItemElencoRecensioni r) {
        Intent intent = new Intent(this, DisplayRecensioneActivity.class);
        intent.putExtra(EXTRA_IDFILM, r.getFilm().getIdFilm());
        startActivity(intent);
    }


    private class DownloadRecensioniTask extends AsyncTask<Void, Void, List<ItemElencoRecensioni>> {

        protected ProgressDialog loadingWheel;
        protected Exception eccezione = null;

        @Override
        protected void onPreExecute() {
            loadingWheel = ProgressDialog.show(MainActivity.this,
               "Caricamento", "Caricamento recensioni in corso. Attendi...", true, true);
        }


        @Override
        protected List<ItemElencoRecensioni> doInBackground(Void... params) {
            List<ItemElencoRecensioni> listaFilm = new LinkedList<ItemElencoRecensioni>();
            Document doc;
            try {
                doc = Jsoup.connect(Costanti.URL_BASE_SPIETATI + Costanti.URL_RECENSIONI).get();
                Elements base = doc.select(".tableRecensioni");
                Elements films = base.select(".redazioneTdTitle, a");
                String mese = "";
                for (Element film : films)
                {
                    if (film.tagName().equals("a")) {
                        ItemFilm itemFilm = new ItemFilm(film.text(), film.attr("href"));
                        //String nome = film.text();
                        //String idfilm = film.attr("href");

                        listaFilm.add(new ItemElencoRecensioni(mese, itemFilm));
                    } else {
                        mese = film.text();
                    }
                }
            } catch (IOException e) {
                eccezione = e;
            }
            return listaFilm;
        }


        @Override
        protected void onPostExecute(List<ItemElencoRecensioni> result) {

            loadingWheel.dismiss();

            if (this.eccezione != null) {
                showMessage("Errore: " + eccezione.getMessage());
                return;
            }

            RecensioniAdapter adapter = new RecensioniAdapter(MainActivity.this,
                    R.layout.row_elenco_film, result);

            final ListView listView = (ListView)findViewById (R.id.listRecensioni);
            listView.setAdapter(adapter);

            // Evento click
            AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view,
                                        int position, long id) {
                    ItemElencoRecensioni r = (ItemElencoRecensioni)adapter.getItemAtPosition(position);

                    apriRecensione(r);

                }
            };
            listView.setOnItemClickListener(clickListener);
        }

    }

    private void showMessage(String message) {
        showMessage(message, Toast.LENGTH_LONG);
    }

    private void showMessage(String message, int len) {
        Toast.makeText(this, message, len).show();
    }
}
