package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView showNameText, showLanguageText, showPremiereDateText, showSummaryText;
    private ImageView showImageView;
    private RecyclerView castRecyclerView;
    private ArrayList<CastModel> castList;
    private CastAdapter castAdapter;
    ShowModel showModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        showNameText = findViewById(R.id.tv_show_name);
        showLanguageText = findViewById(R.id.tv_language);
        showPremiereDateText = findViewById(R.id.tv_premiered);
        showSummaryText = findViewById(R.id.tv_summary);
        showImageView = findViewById(R.id.ic_show_image);
        showModel = (ShowModel) getIntent().getSerializableExtra("showModel");
        Intent intent = getIntent();
        String showId = intent.getStringExtra("show_id");
        String showLanguage = intent.getStringExtra("show_language");
        String showPremieredDate = intent.getStringExtra("show_premiered");
        String showSummary = intent.getStringExtra("show_summary");
        String showImageUrl = intent.getStringExtra("show_img_url");
        String showName = intent.getStringExtra("show_name");
        showNameText.setText(showName);
        showLanguageText.setText(showLanguage);
        showPremiereDateText.setText(showPremieredDate);
        showSummaryText.setText(showSummary);
        Picasso.get().load(showImageUrl).into(showImageView);
        castRecyclerView = findViewById(R.id.recyclerview_cast);
        castList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, "https://api.tvmaze.com/shows/" + showId + "/cast", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("--->", response + "");
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject castInfo = response.getJSONObject(i);
                                castList.add(new CastModel(
                                        castInfo.getJSONObject("person").getString("name"),
                                        castInfo.getJSONObject("character").getJSONObject("image").getString("medium")
                                ));
                            }
                            if (response.length() > 0) {
                                castAdapter = new CastAdapter(castList, ShowDetailActivity.this);
                                castRecyclerView.setHasFixedSize(true);
                                castRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                castRecyclerView.setAdapter(castAdapter);
                                castAdapter.notifyDataSetChanged();
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
