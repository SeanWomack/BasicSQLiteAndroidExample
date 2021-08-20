package com.example.a2sqlitedemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//extends AppCompatActivity class which instatiates newer actionbar navigation options such as recyclerView
public class UpdateActivity extends AppCompatActivity {

    //variables declared
    EditText recipe_input, meal_input, diet_input, ingredients_input, instructions_input;
    Button update_button, delete_button;

    String id, name, meal, diet, ingredients, instructions;

    //onCreate method initialises activity and sets content view as per XML files.
    //variables are mapped to corresponding input fields from XML file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        recipe_input = findViewById(R.id.name_Input2);
        meal_input = findViewById(R.id.mealType_Input2);
        diet_input = findViewById(R.id.dietType_Input2);
        ingredients_input = findViewById(R.id.ingredients_Input2);
        instructions_input = findViewById(R.id.instructions_Input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);


        //Calls getAndSetIntentData method from below with corresponding data for the selected row
        getAndSetIntentData();
        //Sets the correct title in the action bar for the corresponding data, using the name variable
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        //update button writes input data using getText methods once clicked
        update_button.setOnClickListener(v -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            name = recipe_input.getText().toString().trim();
            meal = meal_input.getText().toString().trim();
            diet = diet_input.getText().toString().trim();
            ingredients = ingredients_input.getText().toString().trim();
            instructions = instructions_input.getText().toString().trim();
            myDB.updateData(id, name, meal, diet, ingredients, instructions);

            //finish returns user to mainActivity once myDB.updateData has completed onClick
            finish();

        });

        //delete button simply initiates confirmDialog method from below, which creates an alert
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });


    }

    //getAndSetIntentData method pulls data from row intent created in Add Activity, and stored in SQLite DB
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("meal") && getIntent().hasExtra("diet")
                && getIntent().hasExtra("ingredients") && getIntent().hasExtra("instructions")){

            //getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            meal = getIntent().getStringExtra("meal");
            diet = getIntent().getStringExtra("diet");
            ingredients = getIntent().getStringExtra("ingredients");
            instructions = getIntent().getStringExtra("instructions");

            //Setting new Intent Data
            recipe_input.setText(name);
            meal_input.setText(meal);
            diet_input.setText(diet);
            ingredients_input.setText(ingredients);
            instructions_input.setText(instructions);

            //else statement returns "no data" if no SQLite data is found for entry (should not happen)
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    //alertDialog box created using Builder class, when Delete button is clicked. On selecting 'Yes', SQL query to delete
    //entry is called from MyDatabaseHelper class. Entry is removed, user is returned to MainActivity.
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });

        //on clicking 'no', alertdialog is closed.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        builder.create().show();
    }
}