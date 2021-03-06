package com.bisware.spietati.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bisware.spietati.R;
import com.bisware.spietati.bean.ItemElencoRecensioni;

import java.util.List;


public class RecensioniAdapter extends ArrayAdapter<ItemElencoRecensioni> {

    public RecensioniAdapter(Context context, int textViewResourceId, List<ItemElencoRecensioni> values) {
        super(context, textViewResourceId, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_elenco_film, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mese = (TextView)convertView.findViewById(R.id.mese);
            viewHolder.titolo = (TextView)convertView.findViewById(R.id.label);
            viewHolder.link_scheda = (TextView)convertView.findViewById(R.id.secondLine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemElencoRecensioni r = getItem(position);
        viewHolder.mese.setText(r.getMese());
        viewHolder.titolo.setText(r.getFilm().getTitolo());
        viewHolder.link_scheda.setText(r.getFilm().getIdFilm());
        return convertView;
    }

    private class ViewHolder {
        public TextView mese;
        public TextView titolo;
        public TextView link_scheda;
    }
}
