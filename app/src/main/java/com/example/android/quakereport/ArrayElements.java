package com.example.android.quakereport;

public class ArrayElements {
    private long magnitude;
    private String location;
    private String time;
    private String near;
    private String primaryLocation;
    private String urlAddress;

    public ArrayElements(long mag,String loc,String t,String url){
        magnitude = mag;
        urlAddress = url;
        int find = loc.indexOf("of");
        int end = loc.length();
        if (find == -1){
            near = "Near the";
        }
        else{
            near = loc.substring(0,find+2);
        }
        primaryLocation = loc.substring(find+3,end);
        find = t.indexOf("GMT");
        time = t.substring(0,find-1);
    }

    public long getMagnitude() {
        return magnitude;
    }

    public String getPrimaryLocation() {
        return primaryLocation;
    }

    public String getNear() {
        return near;
    }

    public String getTime() {
        return time;
    }

    public String getUrlAddress() {
        return urlAddress;
    }
}
