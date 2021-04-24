package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Daily extends WeatherObjOneCall{

    private FeelsLike feels_like;
    private Temp temp;
    private Long rain;
    private Long snow;
    public Daily(){
        this.snow = 0L;
        this.rain = 0L;
    }
    public FeelsLike getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(FeelsLike feels_like) {
        this.feels_like = feels_like;
    }

    public Temp getTemp() {
        return temp;
    }

    public Long getRain() {
        return rain;
    }

    public void setRain(Long rain) {
        this.rain = rain;
    }

    public Long getSnow() {
        return snow;
    }

    public void setSnow(Long snow) {
        this.snow = snow;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Daily{" +
                "feels_like=" + feels_like.toString() +
                ", temp=" + temp.toString() +
                ", snow=" + Long.toString(snow) +
                ", rain=" + Long.toString(rain) +
                '}';
    }
}
