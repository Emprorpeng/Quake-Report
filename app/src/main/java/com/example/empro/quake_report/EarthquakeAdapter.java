package com.example.empro.quake_report;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import android.graphics.drawable.GradientDrawable;
//Earthquake adapter that is called to set information into the list view
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(@NonNull Activity context, ArrayList<Earthquake> earthquake) {
        super(context,0, earthquake);
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Earthquake currentEarthquake = getItem(position);
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_layout,parent,false);
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        TextView Magnitude = listItemView.findViewById(R.id.Magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) Magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        Magnitude.setText(decimalFormat.format(currentEarthquake.getMagnitude()));
        TextView Place =listItemView.findViewById(R.id.Place);
        TextView Offest = listItemView.findViewById(R.id.Locationoffset);
        if (currentEarthquake.getPlace().contains("of")){
            String[] locations;
            locations = currentEarthquake.getPlace().split("of");
            Place.setText(locations[1]);
            Offest.setText(locations[0]+"of");
        }else{
            Offest.setText("Near the");
            Place.setText(currentEarthquake.getPlace());
        }
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());


        TextView dateView = listItemView.findViewById(R.id.Date);
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);


        TextView timeView = listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);
        return listItemView;
    }}
