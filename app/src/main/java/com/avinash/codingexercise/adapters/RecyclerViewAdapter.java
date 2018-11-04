package com.avinash.codingexercise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avinash.codingexercise.R;
import com.avinash.codingexercise.pojo.Recipe;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final List<Recipe> recipes;
    private final Context context;


    public RecyclerViewAdapter(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // view for item in list
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recipe_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder viewHolder, final int i) {
        viewHolder.title.setText(recipes.get(i).getName());
        viewHolder.course.setText(recipes.get(i).getCourse());
        viewHolder.noOfIngredients.setText(recipes.get(i).getNoOfIngredients() + "Ingredients");
        viewHolder.time.setText(recipes.get(i).getTime() + "Minutes");
        // commented as its failing to get google user content
//        Glide.with(context).load(recipes.get(i).getImage()).into(viewHolder.img);

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private TextView course;

        private TextView noOfIngredients;

        private TextView time;

        private ImageView img;

        // view holder to reuse views
        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_txt);
            course = view.findViewById(R.id.course_txt);
            noOfIngredients = view.findViewById(R.id.no_of_ingredients_txt);
            time = view.findViewById(R.id.time_txt);
            img = view.findViewById(R.id.img);
        }
    }
}
