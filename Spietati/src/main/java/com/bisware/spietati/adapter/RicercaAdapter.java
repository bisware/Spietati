package com.bisware.spietati.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bisware.spietati.R;
import com.bisware.spietati.bean.ItemFilm;
import com.bisware.spietati.utils.Utils;

import java.util.List;


public class RicercaAdapter extends ArrayAdapter<ItemFilm> {

    public RicercaAdapter(Context context, int textViewResourceId, List<ItemFilm> values) {
        super(context, textViewResourceId, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {

        final ImageView locandina;

        ViewHolder viewHolder;

        if (convertView == null) {
            locandina = (ImageView)convertView.findViewById(R.id.ricercaImg);
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_elenco_film, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.locandina = locandina;
            viewHolder.titolo = (TextView)convertView.findViewById(R.id.ricercaTitolo);
            viewHolder.estratto = (TextView)convertView.findViewById(R.id.ricercaEstratto);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            locandina = viewHolder.locandina;
        }

        ItemFilm r = getItem(position);

        final String src = "";//r.getLocandina();

        new Thread(new Runnable() {
            public void run() {
                try {
                    final Bitmap b = Utils.getBitmapFromURL(src);
                    locandina.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                locandina.setImageBitmap(b);
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

        viewHolder.titolo.setText(r.getTitolo());
        viewHolder.estratto.setText(r.getIdFilm());
        return convertView;
    }

    private class ViewHolder {
        public ImageView locandina;
        public TextView titolo;
        public TextView estratto;
    }
}
