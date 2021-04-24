package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind{
    private Long speed;
    private Integer deg;
    private Long gust;

    public Wind(){}
    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Integer getDeg() {
        return deg;
    }

    public void setDeg(Integer deg) {
        this.deg = deg;
    }

    public Long getGust() {
        return gust;
    }

    public void setGust(Long gust) {
        this.gust = gust;
    }
    @Override
    public String toString() {
        String retVal = "WIND >> Speed: " + Long.toString(getSpeed()) + " -- Wind Direction (Degree): " + Integer.toString(getDeg());
        return retVal;
    }

}

