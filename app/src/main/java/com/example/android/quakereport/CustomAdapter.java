package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ArrayElements> {
    public CustomAdapter(Context context, int resource,List<ArrayElements> elements){
        super(context,0,elements);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (convertView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.common_list_layout, parent, false);
        }

        ArrayElements currentItem = getItem(position);
        TextView tv1 = (TextView) listView.findViewById(R.id.text_magnitude);
        TextView tv2 = (TextView) listView.findViewById(R.id.text_primary_location);
        TextView tv3 = (TextView) listView.findViewById(R.id.text_date_time);
        TextView tv4 = (TextView) listView.findViewById(R.id.text_near);
        tv1.setText(Long.toString(currentItem.getMagnitude()));
        tv2.setText(currentItem.getPrimaryLocation());
        tv3.setText(currentItem.getTime());
        tv4.setText(currentItem.getNear());


        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) tv1.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude

        //getting the double value from the string
        int magnitudeColor = getMagnitudeColor(Double.parseDouble(Long.toString(currentItem.getMagnitude())));
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listView;
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
