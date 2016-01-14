package com.example.atulkumar.mycaloriescounter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.CustomAdapter;
import data.DataBaseHandler;
import model.Food;
import utils.Utils;

public class itemListActivity extends Activity {

    private ListView listView;
    private TextView totalCal, totalFood;
    private DataBaseHandler dba;
    private CustomAdapter customAdapter;
    private ArrayList<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        totalCal = ( TextView ) findViewById( R.id.listTotalCalories );
        totalFood = ( TextView ) findViewById( R.id.listTotalFood );

        listView = ( ListView ) findViewById( R.id.list );

        refreshData();


    }

    private void refreshData() {
        dba = new DataBaseHandler( getApplicationContext() );
        totalCal.setText( "Total Calories: " + Utils.formatNumber( dba.getTotalCalories() ) );
        totalFood.setText( "Total Food: " + dba.getTotalItems() );

        foodList.clear();
        foodList = dba.getFood();
        dba.close();

        customAdapter = new CustomAdapter( itemListActivity.this, R.layout.list_row, foodList );
        listView.setAdapter( customAdapter );
        customAdapter.notifyDataSetChanged();

    }
}
