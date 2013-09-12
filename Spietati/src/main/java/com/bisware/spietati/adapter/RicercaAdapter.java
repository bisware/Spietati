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


public class RicercaAdapter extends ArrayAdapter<ItemElencoRecensioni> {

    public RicercaAdapter(Context context, int textViewResourceId, List<ItemElencoRecensioni> values) {
        super(context, textViewResourceId, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_elenco_film, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.mese = (TextView)convertView.findViewById(R.id.mese);
            viewHolder.name = (TextView)convertView.findViewById(R.id.label);
            viewHolder.description = (TextView)convertView.findViewById(R.id.secondLine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemElencoRecensioni r = getItem(position);
        viewHolder.mese.setText(r.getMese());
        viewHolder.name.setText(r.getFilm().getTitolo());
        viewHolder.description.setText(r.getFilm().getIdFilm());
        return convertView;
    }

    private class ViewHolder {
        public TextView mese;
        public TextView name;
        public TextView description;
    }
}
