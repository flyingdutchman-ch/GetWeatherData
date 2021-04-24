package functions.openweather.weatherobj;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.cloud.datastore.Key;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

public class Snow {

    public Snow(Long snow_1h) {
        this.snow_1h = snow_1h;
    }

    public Snow(){
        this.snow_1h = 0L;
    }
    @JsonProperty("1h")
    private Long snow_1h;

    public Long getSnow_1h() {
        return snow_1h;
    }

    public void setSnow_1h(Long snow_1h) {
        this.snow_1h = snow_1h;
    }

    @Override
    public String toString() {
        return "Snow{" +
                "snow_1h=" + Long.toString(snow_1h) +
                '}';
    }
}
