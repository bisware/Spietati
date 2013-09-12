package com.bisware.spietati.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bisware.spietati.R;
import com.bisware.spietati.adapter.RicercaAdapter;
import com.bisware.spietati.bean.ItemElencoRecensioni;
import com.bisware.spietati.utils.Costanti;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class RicercaActivity extends Activity {

    //@SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String qry = intent.getStringExtra(MainActivity.EXTRA_QUERY_RICERCA);

        // Create the text view
        //TextView textView = (TextView)findViewById(R.id.txtQryRicerca);
        //textView.setText(qry);

        setContentView(R.layout.activity_ricerca);

        new RicercaTitoliTask().execute(qry);

        // Show the Up button in the action bar.
        setupActionBar();
    }

    public void apriRecensione(ItemElencoRecensioni r) {
        Intent intent = new Intent(this, DisplayRecensioneActivity.class);
        intent.putExtra(MainActivity.EXTRA_QUERY_RICERCA, r.getFilm().getIdFilm());
        startActivity(intent);
    }


    private class RicercaTitoliTask extends AsyncTask<String, Void, List<ItemElencoRecensioni>> {

        protected ProgressDialog loadingWheel;
        protected Exception eccezione = null;

        @Override
        protected void onPreExecute() {
            loadingWheel = ProgressDialog.show(RicercaActivity.this,
                    "Caricamento", "Ricerca in corso di...", true, true);
        }


        @Override
        protected List<ItemElencoRecensioni> doInBackground(String... qry) {
            String query  = qry[0];

            try {
                ricercaTitoli(query);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private void ricercaTitoli(String qry) throws Exception {

            URL url = new URL(
                    "https://www.googleapis.com/customsearch/v1" +
                            "?key="+ Costanti.GOOGLE_APIS_KEY +
                            "&cx="+ Costanti.GOOGLE_APIS_CX +
                            "&q="+ qry + "&alt=json");

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

        @Override
        protected void onPostExecute(List<ItemElencoRecensioni> result) {
            loadingWheel.dismiss();

            if (this.eccezione != null) {
                showMessage("Errore: " + eccezione.getMessage());
                return;
            }

            RicercaAdapter adapter = new RicercaAdapter(RicercaActivity.this,
                    R.layout.row_ricerca, result);

            final ListView listView = (ListView)findViewById (R.id.listRicerca);
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

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ricerca, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
