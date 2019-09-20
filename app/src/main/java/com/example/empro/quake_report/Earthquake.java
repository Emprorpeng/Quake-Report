package com.example.empro.quake_report;
//The earthquake POJO class
public class Earthquake {
    private double mMagnitude;
    private String mPlace;
    private long mTimeInMilliseconds;
    private String mURL;
    public Earthquake(double Magnitude,String Place ,long TimeInMilliseconds,String URL ){
        mMagnitude=Magnitude;
        mPlace=Place;
        mTimeInMilliseconds=TimeInMilliseconds;
        mURL = URL;
    }
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    public double getMagnitude(){
        return mMagnitude;
    }
    public String getPlace(){
        return mPlace;
    }
    public String getURL (){
        return mURL;
    }
}
