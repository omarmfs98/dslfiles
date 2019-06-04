package com.example.dsl_files;

import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements studentList.OnFragmentInteractionListener {

    ArrayList<String> listData = new ArrayList<String>();
    RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recycler = (RecyclerView) findViewById(R.id.listStudent);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        String archivos [] = fileList();


        if (encontrado(archivos,"listados.txt" )) {
            ConstraintLayout info_cont = (ConstraintLayout) findViewById(R.id.info_container);
            ImageView info_img = (ImageView) findViewById(R.id.info_img);
            TextView info_text = (TextView) findViewById(R.id.info_text);
            try {
                InputStreamReader archivo =  new InputStreamReader(openFileInput("listados.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";

                while (linea != null) {
                    todo = todo + linea +"\n";
                    linea = br.readLine();
                }

                br.close();
                archivo.close();

                try {

                    JSONObject obj = new JSONObject(todo);

                    JSONArray arr = obj.getJSONArray("students");


                    for (int i = 0; i < arr.length(); i++) {
                        String student = arr.getJSONObject(i).toString();
                        listData.add(student);
                    }

                    info_img.setVisibility(View.GONE);
                    info_text.setVisibility(View.GONE);
                    info_cont.setBackgroundColor(Color.TRANSPARENT);

                    AdapterData adapter = new AdapterData(listData);
                    recycler.setAdapter(adapter);

                } catch(JSONException e) {
                    Log.e("Error", e.toString());
                    info_img.setVisibility(View.VISIBLE);
                    info_text.setVisibility(View.VISIBLE);
                    info_cont.setBackgroundColor(Color.parseColor("#27B4A5"));
                }

            } catch (IOException e) {
                Log.e("Error", e.toString());
            }
        }
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterStudent.class);
        startActivity(intent);
    }

    private boolean encontrado(String archivos [], String  fichero) {


        for ( int i =0; i < archivos.length ; i++ ){

            if(fichero.equals(archivos[i])){

                return true;

            }


        }

        return  false;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
