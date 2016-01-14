package data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.atulkumar.mycaloriescounter.R;
import com.example.atulkumar.mycaloriescounter.caloriesDetails;

import java.util.ArrayList;
import java.util.zip.Inflater;

import model.Food;
import utils.Utils;

/**
 * Created by Atul Kumar on 11-12-2015.
 */
public class CustomAdapter extends ArrayAdapter<Food> {

    private Activity  activity;
    private ArrayList< Food > foodList = new ArrayList<>();
    int layoutResources;

    public CustomAdapter( Activity act, int resource, ArrayList< Food > data ) {
        super( act, resource, data );
        activity = act;
        layoutResources = resource;
        foodList = data;

    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Food getItem(int position) {
        return foodList.get( position );
    }

    @Override
    public int getPosition(Food item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        View row = convertView;

        ViewHolder holder = null;

        if( row == null || row.getTag() == null ){

            LayoutInflater inflater = LayoutInflater.from( activity ); // did a mistake here it's not inflater it's layout inflater
            row = inflater.inflate( layoutResources, null ); // now the row is holding list_row.xml

            holder = new ViewHolder();

            holder.foodName = ( TextView ) row.findViewById( R.id.list_rowFoodName );
            holder.foodCal = ( TextView ) row.findViewById( R.id.list_rowCal );
            holder.recordDate = ( TextView ) row.findViewById( R.id.list_rowDate );

            row.setTag( holder );

        }else{
            holder = ( ViewHolder ) row.getTag();
        }

        /**
         * since the holder is the tag to the row so we set the contents of holder
         * and return the row
         */

        /**
         * the following is going to be visible to the user so we need set the contents of the holder
         * and we can get the food item at the position which we are trying to create a view by using getItem() from above
         * we need this to set the contents of holder and since we can't directly access the contents so we get the food first
         * and then set the contents
         * also till now the user will see only blank textViews or that we have set in the xml file
         * for him to see actual content we need to set the views
        */
        holder.food = getItem( position );

        holder.foodName.setText( holder.food.getFoodName() );
        holder.foodCal.setText(Utils.formatNumber( holder.food.getFoodCal()));
        holder.recordDate.setText(holder.food.getRecordDate());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Food food = finalHolder.food;

                Bundle extras = new Bundle();
                extras.putSerializable("dataPassed", food);
                Intent intent = new Intent( activity, caloriesDetails.class );

                intent.putExtras( extras );
                activity.startActivity( intent );

            }
        });
        return row;
    }



    public class ViewHolder{

        Food food;
        TextView foodName;
        TextView foodCal;
        TextView recordDate;

    }
}
