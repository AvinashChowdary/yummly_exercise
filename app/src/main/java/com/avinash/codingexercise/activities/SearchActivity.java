package com.avinash.codingexercise.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avinash.codingexercise.R;
import com.avinash.codingexercise.adapters.RecyclerViewAdapter;
import com.avinash.codingexercise.helper.Constants;
import com.avinash.codingexercise.pojo.Recipe;
import com.avinash.codingexercise.services.Services;
import com.avinash.codingexercise.services.rest.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchActivity extends AppCompatActivity {

    private boolean isPictureRequired = true;

    private EditText searchEdt;

    private Button searchBtn;

    public void search() {
        String query = searchEdt.getText().toString();
        if(!TextUtils.isEmpty(query)) {
            // calling api service
            Services service = RestClient.getInstance().getYummlyService();
            service.getSearchResults(Constants.APP_ID, Constants.APP_KEY, query, isPictureRequired,
                    new Callback<Response>() {
                        @Override
                        public void success(Response result, Response response) {
                            // creating response string
                            BufferedReader reader = null;
                            List<Recipe> recipes = null;
                            StringBuilder sb = new StringBuilder();
                            try {
                                reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                                String line;
                                try {
                                    while ((line = reader.readLine()) != null) {
                                        sb.append(line);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String json = sb.toString();

                            // parsing response to recipe object
                            try {
                                JSONObject responseObj = new JSONObject(json);
                                JSONArray matches = responseObj.getJSONArray(Constants.JSON_KEY_MATCHES);
                                recipes = new ArrayList<>();
                                Recipe recipe;

                                for(int i=0; i<matches.length(); i++) {
                                    JSONObject match = matches.getJSONObject(i);

                                    recipe = new Recipe();
                                    recipe.setName(match.getString(Constants.JSON_KEY_RECIPENAME));

                                    JSONArray imgUrl = match.getJSONArray("smallImageUrls");
                                    recipe.setImage((String) imgUrl.get(0));

                                    String timeString = match.getString("totalTimeInSeconds");
                                    float timeFloat = Float.parseFloat(timeString);

                                    int time = (int) timeFloat;
                                    recipe.setTime(String.valueOf(time/60));

                                    JSONArray ingredients = match.getJSONArray("ingredients");
                                    recipe.setNoOfIngredients(String.valueOf(ingredients.length()));

                                    JSONObject attr = match.getJSONObject("attributes");
                                    JSONArray attrs = attr.getJSONArray("course");
                                    recipe.setCourse(attrs.get(0).toString());

                                    recipes.add(recipe);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if(recipes != null && recipes.size() > 0) {

                                // adding responses to recycler view via adapter
                                RecyclerView recipesList = findViewById(R.id.recipes_lst);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this);
                                recipesList.setLayoutManager(mLayoutManager);
                                RecyclerViewAdapter adapter = new RecyclerViewAdapter(recipes, SearchActivity.this);
                                recipesList.setAdapter(adapter);
                            } else {
                                Toast.makeText(SearchActivity.this, getResources().getString(R.string.no_items_found), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(SearchActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();

                        }
                    });

        } else {
            Toast.makeText(SearchActivity.this, getResources().getString(R.string.enter_search_term), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEdt = findViewById(R.id.search_edt);
        searchBtn = findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                hideKeyboard(SearchActivity.this);
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
