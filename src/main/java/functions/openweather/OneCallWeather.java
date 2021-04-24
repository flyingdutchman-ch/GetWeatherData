package functions.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import functions.openweather.weatherobj.Current;
import functions.openweather.weatherobj.Daily;
import functions.openweather.weatherobj.WeatherObjOneCall;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallWeather {
    private String timezone; //TIme difference to UTC
    private Integer timezone_offset; //Offset to GMT Timezone
    private Long lat;
    private Long lon;
    private Current current; //Current Weather Object
    private List<Current> hourly;
    private List<Daily> daily;


    public void setCurrent(Current current) {
        this.current = current;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(Integer timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public Long getLat() {
        return lat;
    }

    public void setLat(Long lat) {
        this.lat = lat;
    }

    public Long getLon() {
        return lon;
    }

    public void setLon(Long lon) {
        this.lon = lon;
    }

    public Current getCurrent() {
        return current;
    }

    public List<Current> getHourly() {
        return hourly;
    }

    public void setHourly(List<Current> hourly) {
        this.hourly = hourly;
    }

    @Override
    public String toString() {
       String returnString =  "OneCallWeather{" +
                "timezone=" + timezone +
                ", timezone_offset=" + Integer.toString(timezone_offset) +
                ", lat=" + Long.toString(lat) +
                ", lon=" + Long.toString(lon) +
                ", current={" + current.toString() + "}";
       for(Current hour : hourly){
           returnString = returnString + "hourly={"+hour.toString()+"}";
       }
        for(Daily day : daily){
            returnString = returnString + "daily={"+day.toString()+"}";
        }
        return returnString;
    }
}
