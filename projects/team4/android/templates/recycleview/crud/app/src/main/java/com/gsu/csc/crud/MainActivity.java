package com.gsu.csc.crud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import adapters.PetAdapter;
import models.PetModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<PetModel> petModelList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petModelList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://gsu-petman.herokuapp.com/pets/1";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("pets");

                    //Log.d("PETS", "Array size : " + jsonArray.length());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pet = jsonArray.getJSONObject(i);

                        //Log.d("PETS", "Pet ID : " + pet.getInt("id"));

                        petModelList.add(new PetModel(pet.getInt("id"),
                                                      pet.getString("name"),
                                                      pet.getString("sex"),
                                                      pet.getString("breed"),
                                                      pet.getInt("owner"),
                                                      pet.getString("bod"))
                        );
                    }

                    petAdapter = new PetAdapter(MainActivity.this, petModelList);
                    recyclerView.setAdapter(petAdapter);
                    //petAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }
}
