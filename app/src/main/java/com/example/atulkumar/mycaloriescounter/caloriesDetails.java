package com.example.atulkumar.mycaloriescounter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DataBaseHandler;
import model.Food;
import utils.Utils;

public class caloriesDetails extends Activity {

    private TextView foodName, foodCal, date;
    private Button shareButton, deleteButton;
    private DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_details);

        foodName = ( TextView ) findViewById( R.id.detailsFoodName );
        foodCal = ( TextView ) findViewById( R.id.detailsCalories );
        date = ( TextView ) findViewById( R.id.detailsDate );

        shareButton = ( Button ) findViewById( R.id.shareButton );
        deleteButton = ( Button ) findViewById( R.id.deleteButton );

        final Food food = ( Food ) getIntent().getSerializableExtra( "dataPassed" );
        foodName.setText( food.getFoodName() );
        foodCal.setText(Utils.formatNumber( food.getFoodCal()));
        date.setText( food.getRecordDate() );

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareData();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder( caloriesDetails.this );
                dialog.setTitle("Delete?");
                dialog.setMessage("Are you sure you want to delete this?");
                dialog.setNegativeButton("No", null);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db = new DataBaseHandler(getApplicationContext());
                        db.deleteFood(food.getFoodId());


                        Toast.makeText(getApplicationContext(), "Item Deleted...", Toast.LENGTH_LONG).show();
                        startActivity(new Intent( caloriesDetails.this, itemListActivity.class ) );
                        caloriesDetails.this.finish();
                    }
                });
                dialog.show();

            }
        });

    }

    private void shareData() {
        StringBuilder builder = new StringBuilder();
        builder.append( "Food: " + foodName.getText().toString() + "\n" );
        builder.append( "Calories: " + foodCal.getText().toString() + "\n" );
        builder.append( "Eaten on: " + date.getText().toString() );

        String mailBody = builder.toString();

        Intent intent = new Intent( Intent.ACTION_SEND );

        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Caloric Intake");
        intent.putExtra(Intent.EXTRA_TEXT, mailBody);
        intent.putExtra( Intent.EXTRA_EMAIL, new String[] { "atulkumar5525@gmail.com" } );

        try{
            startActivity( Intent.createChooser( intent, "Send mail..." ));

        }catch( ActivityNotFoundException e ){
            Toast.makeText( getApplicationContext(), "Please install a mailing app...", Toast.LENGTH_LONG ).show();
        }
    }
}
