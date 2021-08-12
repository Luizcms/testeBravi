package com.luiz.saindodotedio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String JSON_URl = "https://www.boredapi.com/api/activity";
    String activity,type;
    Spinner spinner;
    List<String> types;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(this);

        //filtrar pesquisa
        spinner = findViewById(R.id.spinner);

        types= new ArrayList<>();
        types.add("education");
        types.add("social");
        types.add("music");
        types.add("charity");
        types.add("relaxation");
        types.add("recreational");
        types.add("cooking");
        types.add("diy");
        types.add("busywork");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,types);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                sendjson(types.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //fazer busca da atividade
    private void sendjson(String s) {
        Button bt = (Button)findViewById(R.id.start);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                function(s);
            }
        });


    }
    private void function(String s) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URl, null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        activity = response.getString("activity");
                        type = response.getString("type");
                        String key = response.getString("key");

                        if(type.contains(s)){

                            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                            intent.putExtra("description",activity);
                            intent.putExtra("type",type);
                            intent.putExtra("key",key);
                            startActivity(intent);
                            finish();

                        }else{
                            function(s);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
        }
    });
        requestQueue.add(jsonObjectRequest);

    }
}