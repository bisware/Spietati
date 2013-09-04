package com.bisware.spietati.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bisware.spietati.R;
import com.bisware.spietati.adapter.RecensioniAdapter;
import com.bisware.spietati.bean.ElencoRecensioniItem;
import com.bisware.spietati.utils.Costanti;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.bisware.spietati.MESSAGE";
    public final static String EXTRA_IDFILM = "com.gmail.superbisco.spietati.IDRECENSIONE";

    public void beginSearch(View view) {
        Intent intent = new Intent(this, RicercaActivity.class);

        EditText editText = (EditText) findViewById(R.id.txtRicerca);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DownloadRecensioniTask().execute();

        Toast.makeText(this, "Caricamento elenco ultime recensioni" , Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void apriRecensione(ElencoRecensioniItem r) {
        Intent intent = new Intent(this, DisplayRecensioneActivity.class);
        intent.putExtra(EXTRA_IDFILM, r.getIdFilm());
        startActivity(intent);
    }


    private class DownloadRecensioniTask extends AsyncTask<Void, Void, List<ElencoRecensioniItem>> {

        @Override
        protected List<ElencoRecensioniItem> doInBackground(Void... params) {
            List<ElencoRecensioniItem> listaFilm = new LinkedList<ElencoRecensioniItem>();
            Document doc;
            try {
                doc = Jsoup.connect(Costanti.URL_BASE_SPIETATI + Costanti.URL_RECENSIONI).get();
                Elements films = doc.select(".tableRecensioni a");
                for (Element film : films)
                {
                    String nome = film.text();
                    String idfilm = film.attr("href");
                    listaFilm.add(new ElencoRecensioniItem(nome, idfilm));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return listaFilm;
        }


        @Override
        protected void onPostExecute(List<ElencoRecensioniItem> result) {

            RecensioniAdapter adapter = new RecensioniAdapter(getApplicationContext(),
                    R.layout.row_elenco_film, result);

            final ListView listView = (ListView)findViewById (R.id.listView);
            listView.setAdapter(adapter);

            // Evento click
            AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View view,
                                        int position, long id) {
                    ElencoRecensioniItem r = (ElencoRecensioniItem)adapter.getItemAtPosition(position);

                    apriRecensione(r);

                    Toast.makeText(getApplicationContext(),
                            "Apertura recensione " + r.getTitolo(), Toast.LENGTH_LONG)
                            .show();
                }
            };
            listView.setOnItemClickListener(clickListener);
        }

    }
}
