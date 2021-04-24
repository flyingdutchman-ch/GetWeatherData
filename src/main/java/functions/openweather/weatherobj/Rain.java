package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

public class Rain {

    public Rain(Long rain_1h) {
        this.rain_1h = rain_1h;
    }

    public Rain(){
        this.rain_1h = 0L;
    }
    @JsonProperty("1h")
    private Long rain_1h;

    public Long getRain_1h() {
        return rain_1h;
    }

    public void setRain_1h(Long rain_1h) {
        this.rain_1h = rain_1h;
    }

    @Override
    public String toString() {
        return "Rain{" +
                "rain_1h=" + Long.toString(rain_1h) +
                '}';
    }
}
