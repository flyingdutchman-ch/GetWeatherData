package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Main{
    private Long temp;
    private Long feels_like;
    private Long temp_min;
    private Long temp_max;
    private Integer pressure;
    private Integer humidity;

    public Main(){}
    public Long getTemp() {
        return temp;
    }

    public void setTemp(Long temp) {
        this.temp = temp;
    }

    public Long getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Long feels_like) {
        this.feels_like = feels_like;
    }

    public Long getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Long temp_min) {
        this.temp_min = temp_min;
    }

    public Long getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Long temp_max) {
        this.temp_max = temp_max;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    @Override
    public String toString() {
        String retVal = "MAIN WEATHER >> Temperature: " + Long.toString(getTemp()) + " -- Feels Like: " + Long.toString(getFeels_like()) + " -- Temp Min: " + Long.toString(getTemp_min()) + "--" +
                " Temp Max: " + Long.toString(getTemp_max()) + " -- Pressure: " + Integer.toString(getPressure()) + " -- Humidity: " + Integer.toString(getHumidity());
        return retVal;
    }
}