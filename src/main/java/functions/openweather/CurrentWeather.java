package functions.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.*;
import functions.openweather.weatherobj.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather{
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Sys sys;
    private Wind wind;
    private Clouds clouds;
    private Integer visibility;
    private String base;
    private Long dt; //Unix Time when Data was generated.
    private Integer timezone; //TIme difference to UTC
    private String name; //Station Name;

    public CurrentWeather() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        Date dateTimeOfGen = new Date(getDt()*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(dateTimeOfGen);

        String retVal = "WEATHER INFORMATION from "+ formattedDate+ " " + getName() + " -- Difference To UTC (in sec): " + Integer.toString(getTimezone()) + "" +
            "\n" + getCoord().toString() + "\n" + getWeather().toString() + "\n" + getMain().toString() + "\n" + getWind().toString() + "\n" + getClouds().toString() + "\n" + getSys().toString() +"" +
            "\nVISIBILITY >> " + Integer.toString(getVisibility());
        return retVal;
    }







}
