package com.example.dsl_files;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RegisterStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);
    }

    public void saveStudent(View view) {

        EditText name = (EditText) findViewById(R.id.name);
        EditText address = (EditText) findViewById(R.id.address);
        EditText lat = (EditText) findViewById(R.id.lat);
        EditText lng = (EditText) findViewById(R.id.lng);

        String nameVal = name.getText().toString();
        String addressVal = address.getText().toString();
        String latVal = lat.getText().toString();
        String lngVal = lng.getText().toString();

        if (!nameVal.equals("") || !addressVal.equals("") || !latVal.equals("") || !lngVal.equals("")) {

            try {

                InputStreamReader data =  new InputStreamReader(openFileInput("listados.txt"));
                BufferedReader br = new BufferedReader(data);
                String linea = br.readLine();
                String todo = "";

                while (linea != null) {
                    todo = todo + linea +"\n";
                    linea = br.readLine();
                }

                br.close();
                data.close();

                OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("listados.txt", Activity.MODE_PRIVATE));

                try {
                    JSONObject obj = new JSONObject(todo);

                    JSONArray arr = obj.getJSONArray("students");


                    JSONArray students = new JSONArray();
                    JSONObject response = new JSONObject();

                    for (int i = 0; i < arr.length(); i++) {
                        String name_arc = arr.getJSONObject(i).getString("name");
                        String address_arc = arr.getJSONObject(i).getString("address");
                        Double lat_arc = new Double(arr.getJSONObject(i).getString("lat"));
                        Double lng_arc = new Double(arr.getJSONObject(i).getString("lng"));
                        students.put(getStudent(name_arc,address_arc, lat_arc, lng_arc));
                    }

                    students.put(getStudent(nameVal,addressVal, new Double(latVal), new Double(lngVal)));

                    response.put("students", students);

                    String jsonText = response.toString();

                    Log.e("json", jsonText);

                    archivo.write(jsonText);
                    archivo.flush();
                    archivo.close();


                    Toast.makeText(this,"Estudiante Guardado",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    Log.e("Error", e.toString());
                }

            } catch (IOException e) {
                Log.e("Error", e.toString());
            }

        } else {
            Toast.makeText(this, "Llene todos los campos",Toast.LENGTH_SHORT).show();
        }
    }

    JSONObject getStudent(String n, String a, Double la, Double ln) {
        JSONObject student = new JSONObject();
        try {
            student.put("name", n);
            student.put("address", a);
            student.put("lat", new Double(la));
            student.put("lng", new Double(ln));
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        return student;
    }
}
