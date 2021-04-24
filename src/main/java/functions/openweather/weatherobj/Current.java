package functions.openweather.weatherobj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Current extends WeatherObjOneCall{

    private Long feels_like;
    private Long temp;
    private Snow snow;
    private Rain rain;
    private Double wind_gust;


    public Current(){
        this.setPop(0L);
        this.setSunrise(0L);
        this.setSunset(0L);
        this.setSnow(new Snow());
        this.setRain(new Rain());
    }

    public Double getWind_gust() {
        return wind_gust;
    }

    public void setWind_gust(Double wind_gust) {
        this.wind_gust = wind_gust;
    }

    public Long getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Long feels_like) {
        this.feels_like = feels_like;
    }


    public Long getTemp() {
        return temp;
    }

    public void setTemp(Long temp) {
        this.temp = temp;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public String toString(){
        String returnString = super.toString();
        returnString = returnString + "feels_like=" + Long.toString(feels_like) +
            "temp=" + Long.toString(temp)+", " +
                "snow={" + snow.toString() +"}"+
                ", rain={" + rain.toString() +"}";
        return returnString;
    }
}
