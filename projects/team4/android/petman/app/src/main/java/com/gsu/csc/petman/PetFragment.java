package com.gsu.csc.petman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gsu.csc.adapters.PetAdapter;
import com.gsu.csc.models.PetModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PetFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private PetAdapter petAdapter;
    private List<PetModel> petModelList;
    private RequestQueue requestQueue;
    private GlobalVariables globalVariables;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GlobalVariables globalVariables = (GlobalVariables) getActivity().getApplicationContext();

        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPetsList(true, globalVariables.getUserId());

                petAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PetFragmentDetails.class);
                intent.putExtra("owner_id", globalVariables.getUserId());
                intent.putExtra("action", "add");

                //v.getContext().startActivityForResult(intent);
                // error here
                getActivity().startActivityForResult(intent, 1);
            }
        });

        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        petModelList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        loadPetsList(false, globalVariables.getUserId());
    }

    private void loadPetsList(final boolean reload, int owner_id) {

        String url = getString(R.string.API_URL).concat("/pets/" + owner_id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("pets");

                    if (reload) petModelList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject pet = jsonArray.getJSONObject(i);

                        try{
                            Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(pet.getString("dob"));

                            petModelList.add(new PetModel(pet.getInt("id"),
                                    pet.getString("name"),
                                    pet.getString("sex"),
                                    pet.getString("breed"),
                                    pet.getInt("owner"),
                                    dob,
                                    pet.getString("type"))
                            );

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    petAdapter = new PetAdapter(getContext(), petModelList);
                    recyclerView.setAdapter(petAdapter);

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

        request.setRetryPolicy(new DefaultRetryPolicy(Integer.parseInt(getString(R.string.API_FETCH_TIMEOUT)),  DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
    }



}
