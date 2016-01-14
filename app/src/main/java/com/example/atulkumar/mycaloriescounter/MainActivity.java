package com.example.atulkumar.mycaloriescounter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DataBaseHandler;
import model.Food;

public class MainActivity extends Activity {

    private EditText foodName, foodCal;
    private Button saveButton;
    private DataBaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodName = ( EditText ) findViewById( R.id.mainFoodName );
        foodCal = ( EditText ) findViewById( R.id.mainFoodCal );

        saveButton = ( Button ) findViewById( R.id.saveButton );
        dba = new DataBaseHandler( MainActivity.this );
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });

    }

    private void saveToDB() {
        if( foodName.getText().toString().equals( "" ) || foodCal.getText().toString().equals( "" ) ) {
            Toast.makeText( MainActivity.this, "Please enter both the fields...", Toast.LENGTH_LONG ).show();

        }else {
            Food food = new Food();
            food.setFoodName(foodName.getText().toString());
            food.setFoodCal(Integer.valueOf(foodCal.getText().toString()));
            dba.addFood(food);
            dba.close();

            foodName.setText( "" );
            foodCal.setText( "" );

            startActivity( new Intent( MainActivity.this, itemListActivity.class ) );

        }

    }
}
