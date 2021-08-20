package com.example.a2sqlitedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

//customerAdapter extends recycler view adapter class for importing dynamic list views
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    //context class is called from other activities whenever an interaction or change takes place
    private Context context;
    Activity activity;
    private ArrayList recipe_id, recipe_name, recipe_mealType, recipe_dietType, recipe_ingredients, recipe_instructions;

    CustomAdapter(Activity activity, Context context, ArrayList recipe_id, ArrayList recipe_name, ArrayList recipe_mealType,
                  ArrayList recipe_dietType, ArrayList recipe_ingredients, ArrayList recipe_instructions){
        this.activity = activity;
        this.context = context;
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_mealType = recipe_mealType;
        this.recipe_dietType = recipe_dietType;
        this.recipe_ingredients = recipe_ingredients;
        this.recipe_instructions = recipe_instructions;

    }

    //onCreateViewHolder class begins when app is instantiated. MyViewHolder returns XML row layouts
    //with existing stored data.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    //OnBindViewHolder facilitates the 'recycling' function of recycle viewer with a count. Once
    //there have been 10 displayed items, data is recycled and variable values are 'dumped'.
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recipe_id_txt.setText(String.valueOf(recipe_id.get(position)));
        holder.recipe_name_txt.setText(String.valueOf(recipe_name.get(position)));
        holder.meal_type_txt.setText(String.valueOf(recipe_mealType.get(position)));
        holder.diet_type_txt.setText(String.valueOf(recipe_dietType.get(position)));
        holder.ingredients_list_txt.setText(String.valueOf(recipe_ingredients.get(position)));
        holder.recipe_instr_txt.setText(String.valueOf(recipe_instructions.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(recipe_id.get(position)));
                intent.putExtra("name", String.valueOf(recipe_name.get(position)));
                intent.putExtra("meal", String.valueOf(recipe_mealType.get(position)));
                intent.putExtra("diet", String.valueOf(recipe_dietType.get(position)));
                intent.putExtra("ingredients", String.valueOf(recipe_ingredients.get(position)));
                intent.putExtra("instructions", String.valueOf(recipe_instructions.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    //works with recycler view, counts row data entries
    @Override
    public int getItemCount() {
        return recipe_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView recipe_id_txt, recipe_name_txt, meal_type_txt, diet_type_txt, ingredients_list_txt, recipe_instr_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe_id_txt = itemView.findViewById(R.id.recipe_id_txt);
            recipe_name_txt = itemView.findViewById(R.id.recipe_name_txt);
            meal_type_txt = itemView.findViewById(R.id.meal_type_txt);
            diet_type_txt = itemView.findViewById(R.id.diet_type_txt);
            ingredients_list_txt = itemView.findViewById(R.id.ingredients_list_txt);
            recipe_instr_txt = itemView.findViewById(R.id.recipe_instr_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
