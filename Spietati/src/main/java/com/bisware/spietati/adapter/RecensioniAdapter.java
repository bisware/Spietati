package com.bisware.spietati.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bisware.spietati.R;
import com.bisware.spietati.bean.ElencoRecensioniItem;

import java.util.List;


public class RecensioniAdapter extends ArrayAdapter<ElencoRecensioniItem> {

    public RecensioniAdapter(Context context, int textViewResourceId, List<ElencoRecensioniItem> values) {
        super(context, textViewResourceId, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }
//        LayoutInflater inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        View rowView = inflater.inflate(R.layout.row_elenco_film, parent, false);
//        TextView nome = (TextView) rowView.findViewById(R.id.label);
//        TextView descrizione = (TextView) rowView.findViewById(R.id.secondLine);
//        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
//        ElencoRecensioniItem r = getItem(position);   //values[position]
//        nome.setText(r.getTitolo());
//        descrizione.setText(r.getIdFilm());
////        // Change the icon for Windows and iPhone
////        String s = values[position];
////        if (s.startsWith("iPhone")) {
////            imageView.setImageResource(R.drawable.no);
////        } else {
////            imageView.setImageResource(R.drawable.ok);
////        }
//
//        return rowView;
//    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_elenco_film, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.label);
            viewHolder.description = (TextView)convertView.findViewById(R.id.secondLine);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ElencoRecensioniItem r = getItem(position);
        viewHolder.name.setText(r.getTitolo());
        viewHolder.description.setText(r.getIdFilm());
        return convertView;
    }

    private class ViewHolder {
        public TextView name;
        public TextView description;
    }
}
