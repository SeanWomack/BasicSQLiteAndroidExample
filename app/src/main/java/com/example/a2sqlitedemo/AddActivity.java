package com.example.a2sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//class instantiates text input variables for 'add recipes' function, and upon pressing 'add' button
//with onClickListener, calls MyDaTabaseHelper function to write the input to the relevant fields.
public class AddActivity extends AppCompatActivity {

    EditText name_Input, mealType_Input, dietType_Input, ingredients_Input, instructions_Input;
    Button add_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_Input = findViewById(R.id.name_Input);
        mealType_Input = findViewById(R.id.mealType_Input);
        dietType_Input = findViewById(R.id.dietType_Input);
        ingredients_Input = findViewById(R.id.ingredients_Input);
        instructions_Input = findViewById(R.id.instructions_Input);
        add_Button = findViewById(R.id.add_Button);
        add_Button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
            myDB.addRecipe(name_Input.getText().toString().trim(),
                    mealType_Input.getText().toString().trim(),
                    dietType_Input.getText().toString().trim(),
                    ingredients_Input.getText().toString().trim(),
                    instructions_Input.getText().toString().trim());

        });



    }
}