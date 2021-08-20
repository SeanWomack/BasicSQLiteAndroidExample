
package com.example.a2sqlitedemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
//extends AppCompatActivity class which instatiates newer actionbar navigation options such as recyclerView. Recyclerview
//'recycles' system memory by only displaying 10 'views' at once, and facilitating a 'first in, first out' data list structure.
//when a user goes beyond 10 items in a list, the first is discarded from memory.
public class MainActivity extends AppCompatActivity {

    //RecyclerView instantiated, used for scrolling library of large amounts of data. Alternative to views such as ListView
    RecyclerView recyclerView;
    FloatingActionButton add_new;
    ImageView empty_imageview;
    TextView no_data;

    //other variables declared for class object orientation
    MyDatabaseHelper myDB;
    ArrayList<String> recipe_id, recipe_name, recipe_mealType, recipe_dietType, recipe_ingredients, recipe_instructions;
    CustomAdapter customAdapter;

    @Override
    //onCreate and setContentView intializes the app and calls the layout resources (XML etc)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //content view layout from XML layout
        setContentView(R.layout.activity_main);

        //calling objects from XML Layout
        recyclerView = findViewById(R.id.recyclerView);
        add_new = findViewById(R.id.add_new);
        empty_imageview = findViewById(R.id.empty_imageView);
        no_data = findViewById(R.id.no_data);

        //'add new' button Intent, starts "AddActivity" class
        add_new.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        //arraylist created for displaying recipes in rows/cardview
        myDB = new MyDatabaseHelper(MainActivity.this);
        recipe_id = new ArrayList<>();
        recipe_name = new ArrayList<>();
        recipe_mealType = new ArrayList<>();
        recipe_dietType = new ArrayList<>();
        recipe_ingredients = new ArrayList<>();
        recipe_instructions = new ArrayList<>();

        storeDataInArrays();

        //array is passed to customAdapter class where data from database/input is retrieved and displayed using recyclerview.
        //customAdapter is 'context' class, which is used for a live application to get information from an app
        customAdapter = new CustomAdapter(MainActivity.this, this, recipe_id, recipe_name, recipe_mealType, recipe_dietType,
                recipe_ingredients, recipe_instructions);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }
    //refreshes main activity every time main activity is initiated again
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    //array data returned and displayed after being passed from customAdapter class
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility((View.VISIBLE));
        }else{
            while (cursor.moveToNext()){
                recipe_id.add(cursor.getString(0));
                recipe_name.add(cursor.getString(1));
                recipe_mealType.add(cursor.getString(2));
                recipe_dietType.add(cursor.getString(3));
                recipe_ingredients.add(cursor.getString(4));
                recipe_instructions.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility((View.GONE));


        }
    }

    //Delete entries function - onCreateOptionsMenu inflater creates visible menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //method instantiates delete button functions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //alert dialog is created with options for yes or no - 'yes' access myDB class and runs appropriate deletion query,
    //no closes dialog box
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All");
        builder.setMessage("Are you sure you want to delete all entries?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //Refresh activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }
    }