package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Food;

/**
 * Created by Atul Kumar on 11-12-2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper{

    public DataBaseHandler(Context context) {
        super( context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_REQUEST = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                              Constants.FOOD_NAME + " TEXT, " + Constants.FOOD_CAL + " INTEGER, " +
                              Constants.DATE_NAME + " LONG);";

        db.execSQL(SQL_REQUEST);
       // db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME );
        onCreate(db);
    }

    public ArrayList< Food > getFood(){

        ArrayList< Food > foodsFromDB = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query( Constants.TABLE_NAME, new String[] {Constants.KEY_ID, Constants.FOOD_NAME,
                     Constants.FOOD_NAME, Constants.FOOD_CAL, Constants.DATE_NAME }, null, null, null, null,
                Constants.DATE_NAME + " DESC" );

        if( cursor.moveToFirst() ){

            do{
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setFoodCal(cursor.getInt(cursor.getColumnIndex(Constants.FOOD_CAL)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String dateData = dateFormat.format( new Date( cursor.getLong( cursor.getColumnIndex( Constants.DATE_NAME)) ).getTime());
                food.setRecordDate( dateData );

                foodsFromDB.add( food );
            }while( cursor.moveToNext() );
        }
        cursor.close();
        db.close();


        return foodsFromDB;
    }

    public void addFood( Food food ){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put( Constants.FOOD_NAME, food.getFoodName() );
        values.put( Constants.FOOD_CAL, food.getFoodCal() );
        values.put( Constants.DATE_NAME, System.currentTimeMillis() );


        db.insert(Constants.TABLE_NAME, null, values);

        db.close();
    }

    public int getTotalItems(){

        int totalItems = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        String SQL_QUERY = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery( SQL_QUERY, null );

        if( cursor.moveToFirst() )
            totalItems = cursor.getCount();

        cursor.close();
        db.close();
        return totalItems;
    }

    public int getTotalCalories(){
        int totalCalories = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String SQL_QUERY = "SELECT SUM( " + Constants.FOOD_CAL + " ) FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery( SQL_QUERY, null );
        if( cursor.moveToFirst() ){
            totalCalories = cursor.getInt( 0 );
        }
        cursor.close();
        db.close();

        return totalCalories;
    }

    public void deleteFood( int id ){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete( Constants.TABLE_NAME, Constants.KEY_ID + " = ?", new String[] { String.valueOf( id )});
        db.close();
    }
}
