package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Объявление
    private RecyclerView showsRecyclerView;
    private ArrayList<ShowModel> showsList;
    private ShowAdapter showsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showsRecyclerView = findViewById(R.id.recyclerview_shows);
        showsList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://api.tvmaze.com/shows", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject showInfo = response.getJSONObject(i);
                                showsList.add(new ShowModel(
                                        showInfo.getString("name"),
                                        showInfo.getString("language"),
                                        showInfo.getString("premiered"),
                                        showInfo.getString("summary"),
                                        showInfo.getJSONObject("image").getString("medium"),
                                        showInfo.getString("id")
                                ));
                            }
                            if (response.length() > 0) {
                                showsAdapter = new ShowAdapter(showsList, MainActivity.this);
                                showsRecyclerView.setHasFixedSize(true);
                                showsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                                showsRecyclerView.setAdapter(showsAdapter);
                                showsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                            }
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
