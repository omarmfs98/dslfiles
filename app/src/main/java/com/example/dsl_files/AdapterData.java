package com.example.dsl_files;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterData extends RecyclerView.Adapter<AdapterData.ViewHolderData> {

    ArrayList<String> listData;

    public AdapterData(ArrayList<String> listData) {
        this.listData = listData;
    }

    @Override
    public ViewHolderData onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item, viewGroup, false);
        ViewHolderData vh = new ViewHolderData(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolderData viewHolderData, int i) {
        viewHolderData.asignarDatos(listData.get(i));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolderData extends RecyclerView.ViewHolder {

        TextView name, address, time, geo;
        ImageView profile;

        public ViewHolderData(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            profile = itemView.findViewById(R.id.profile);
            time = itemView.findViewById(R.id.time);
            geo = itemView.findViewById(R.id.geo);
        }

        public void asignarDatos(String s) {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            try {
                JSONObject obj = new JSONObject(s);
                name.setText(obj.getString("name"));
                address.setText(obj.getString("address"));
                geo.setText(obj.getString("lat") + ", " + obj.getString("lng"));
                profile.setImageResource(R.drawable.boy);
                time.setText(formatter.format(date));

            } catch(JSONException e) {
                Log.e("Error", e.toString());
            }
        }
    }
}
