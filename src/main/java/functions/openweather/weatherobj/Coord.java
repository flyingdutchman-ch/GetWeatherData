package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coord {
    private Long lon;
    private Long lat;
    public Coord() {
    }

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        String retVal = "COORDINATES >> Longitude: " + Long.toString(getLon()) + " -- Latitude: "+ Long.toString(getLat());
        return retVal;
    }
}