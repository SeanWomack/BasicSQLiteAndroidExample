package com.example.a2sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

//class extends SQLiteOpenHelper class library for SQLite functionality
public class MyDatabaseHelper extends SQLiteOpenHelper {

    //static variables declared for table and columns, with db name given
    // for referencing in other classes
    private Context context;
    private static final String DATABASE_NAME = "recipeList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_List";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_RECIPE = "recipe_name";
    private static final String COLUMN_MEAL = "meal_type";
    private static final String COLUMN_DIET = "diet_type";
    private static final String COLUMN_INGREDIENT = "ingredients_list";
    private static final String COLUMN_INSTRUCTIONS = "recipe_instruct";

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //Create table query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_RECIPE + " TEXT, " +
                        COLUMN_MEAL + " TEXT, " +
                        COLUMN_DIET + " TEXT, " +
                        COLUMN_INGREDIENT + " TEXT, " +
                        COLUMN_INSTRUCTIONS + " TEXT);";
        db.execSQL(query);
    }

    //Called when the database file already exists, but its version is lower than
    //the one specified in the current version of the app. Upgrades database to current iteration.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //add data query
    void addRecipe(String recipeName, String meal, String diet, String ingredients, String instructions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RECIPE, recipeName);
        cv.put(COLUMN_MEAL, meal);
        cv.put(COLUMN_DIET, diet);
        cv.put(COLUMN_INGREDIENT, ingredients);
        cv.put(COLUMN_INSTRUCTIONS, instructions);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to Update...", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //read all data query
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //update data query
    void updateData(String row_id, String recipeName, String meal, String diet, String ingredients, String instructions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_RECIPE, recipeName);
        cv.put(COLUMN_MEAL, meal);
        cv.put(COLUMN_DIET, diet);
        cv.put(COLUMN_INGREDIENT, ingredients);
        cv.put(COLUMN_INSTRUCTIONS, instructions);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Update...", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();

        }

    }

    //delete single row query
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete...", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }

    //delete all rows query
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
