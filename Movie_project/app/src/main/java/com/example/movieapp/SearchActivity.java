package com.example.movieapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView searchResultsRecyclerView;
    private SearchResultsAdapter searchResultsAdapter;
    private ProgressBar progressBar;

    private List<ShowModel> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.search_edit_text);
        searchResultsRecyclerView = findViewById(R.id.search_results_recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        searchResultsAdapter = new SearchResultsAdapter(searchResults, this);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultsRecyclerView.setAdapter(searchResultsAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                if (!query.isEmpty()) {
                    searchShows(query);
                } else {
                    searchResults.clear();
                    searchResultsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void searchShows(String query) {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://api.tvmaze.com/search/shows?q=" + query;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        searchResults.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject showObject = response.getJSONObject(i).getJSONObject("show");
                                String name = showObject.getString("name");
                                String language = showObject.getString("language");
                                String premiered = showObject.getString("premiered");
                                String summary = showObject.getString("summary");
                                String imageUrl = showObject.getJSONObject("image").getString("medium");
                                String id = showObject.getString("id");

                                ShowModel show = new ShowModel(name, language, premiered, summary, imageUrl, id);
                                searchResults.add(show);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        searchResultsAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
