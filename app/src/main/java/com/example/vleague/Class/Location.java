package com.example.vleague.Class;

public class Location {
    public long Location_id;
    public String Location_Name;
    public int Location_Total_Seat;

    public Location() {
    }

    public Location(long location_id, String location_Name, int location_Total_Seat) {
        Location_id = location_id;
        Location_Name = location_Name;
        Location_Total_Seat = location_Total_Seat;
    }

    public long getLocation_id() {
        return Location_id;
    }

    public String getLocation_Name() {
        return Location_Name;
    }

    public int getLocation_Total_Seat() {
        return Location_Total_Seat;
    }

    public void setLocation_id(long location_id) {
        Location_id = location_id;
    }

    public void setLocation_Name(String location_Name) {
        Location_Name = location_Name;
    }

    public void setLocation_Total_Seat(int location_Total_Seat) {
        Location_Total_Seat = location_Total_Seat;
    }

}
