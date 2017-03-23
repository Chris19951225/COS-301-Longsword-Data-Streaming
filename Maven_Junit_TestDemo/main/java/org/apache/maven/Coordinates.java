//package NavUP.Models;
package org.apache.maven;

public class Coordinates
{
    protected double latitude;
    protected double longitude;
    protected double altitude;

    public Coordinates() {
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.altitude = 0.0;
    }


    public Coordinates(double lat, double lon, double alt)
    {
        this.latitude = lat;
        this.longitude = lon;
        this.altitude = alt;
    }
    public void setLatitude(double latitude1)
    {
        this.latitude = latitude1;
    }
    public  void setLongitude(double longitude1)
    {
        this.longitude = longitude1;
    }
    public void setAltitude(double altitude1)
    {
        this.altitude = altitude1;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public double getAltitude()
    {
        return altitude;
    }
    public String getCoordinates() {
        String coordinatesString = "";
        coordinatesString = Double.toString(this.getLatitude()) + Double.toString(this.getLongitude()) + Double.toString(this.getAltitude());
        return coordinatesString;
    }
}
