package com.example.clase07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clase07.entity.Comment;
import com.example.clase07.entity.EmpleadoDto;
import com.example.clase07.entity.Profile;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void obtenerEmpleados(View view) {
        if (tengoInternet()) {

            String url = "https://3dkvh9wb90.execute-api.us-east-1.amazonaws.com/prod/";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("infoWS", response);

                            Gson gson = new Gson();
                            EmpleadoDto empleadoDto = gson.fromJson(response, EmpleadoDto.class);

                            EditText editText  = findViewById(R.id.editTextTextPersonName);
                            int employeeIdFontBold = Integer.parseInt(editText.getText().toString());

                            ListaEmpleadosAdapter adapter = new ListaEmpleadosAdapter(
                                    empleadoDto.getLista(),
                                    MainActivity.this,
                                    employeeIdFontBold);

                            RecyclerView recyclerView = findViewById(R.id.recyclerView);
                            recyclerView.setAdapter(adapter);

                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("api-key", "EaQibIyUgcoCAyelLnDwUAxR1OX6AH");

                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void setFontBoldItem(View view){
        EditText editText  = findViewById(R.id.editTextTextPersonName);
        int employeeIdFontBold = Integer.parseInt(editText.getText().toString());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ListaEmpleadosAdapter adapter  = (ListaEmpleadosAdapter) recyclerView.getAdapter();

        adapter.actualizarEmpleado(employeeIdFontBold);
    }

    public void validarUsername(View view) {

        if (tengoInternet()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://1a8jit3xd4.execute-api.us-east-1.amazonaws.com/prod?accion=validar";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("infoWS", response);

                            //TextView textView2 = findViewById(R.id.textView2);
                            //textView2.setText(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> parametros = new HashMap<>();

                    EditText editText = findViewById(R.id.editTextTextPersonName);
                    String username = editText.getText().toString();

                    parametros.put("username", username);
                    return parametros;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return super.getHeaders();
                }
            };

            requestQueue.add(stringRequest);
        }
    }


    public void obtenerComentarios(View view) {
        if (tengoInternet()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://my-json-server.typicode.com/typicode/demo/comments";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("infoWS", response);

                            Gson gson = new Gson();
                            Comment[] commentsArray = gson.fromJson(response, Comment[].class);

                            for (Comment comment : commentsArray) {
                                Log.d("infoWS", comment.getId() + " / " + comment.getBody() + " / " + comment.getPostId());
                            }

                            ArrayAdapter<Comment> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, commentsArray);

                            Spinner spinner = findViewById(R.id.spinner);
                            spinner.setAdapter(adapter);


                            //TextView textView = findViewById(R.id.textView);
                            //textView.setText(profile.getName());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(stringRequest);
        }
    }

    public void validarInternet(View view) {
        //Toast.makeText(this,tengoInternet()?"Si tengo internet":"No tengo internet",Toast.LENGTH_SHORT).show();
        if (tengoInternet()) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://my-json-server.typicode.com/typicode/demo/profile";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("infoWS", response);

                            Gson gson = new Gson();
                            Profile profile = gson.fromJson(response, Profile.class);

                            TextView textView = findViewById(R.id.textView);
                            textView.setText(profile.getName());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            requestQueue.add(stringRequest);
        }
    }

    public boolean tengoInternet() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
            return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network networks = connectivityManager.getActiveNetwork();
            if (networks == null)
                return false;

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(networks);
            if (networkCapabilities == null)
                return false;

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return true;
            return false;
        } else {

            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null)
                return false;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET)
                return true;
            return false;

        }
    }


}