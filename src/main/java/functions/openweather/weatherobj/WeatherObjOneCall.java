package functions.openweather.weatherobj;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObjOneCall {

        private Long uvi;           /*Current UV index*/
        private Integer clouds;     /*Current clouds %*/
        private Integer visibility;
        private List<Weather> weather;
        private Long pop;               /*Probability of precipitation*/
        private Long wind_speed;
        private Integer wind_deg;
        private Integer pressure;    /*current hpa pressure*/
        private Integer humidity;   /* current humidty in %*/
        private Long dew_point;     /* current dew-point */
        private Long dt;            /* Date of Current Weather info*/
        private Long sunrise;       /*Unix Timestamp for Time of Sunrise*/
        private Long sunset;        /*Unix Timestamp for Time of Sunset*/


        public Long getPop() {
                return pop;
        }

        public void setPop(Long pop) {
                this.pop = pop;
        }


        public Long getWind_speed() {
                return wind_speed;
        }

        public void setWind_speed(Long wind_speed) {
                this.wind_speed = wind_speed;
        }

        public Integer getWind_deg() {
                return wind_deg;
        }

        public void setWind_deg(Integer wind_deg) {
                this.wind_deg = wind_deg;
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

        public Long getDew_point() {
                return dew_point;
        }

        public void setDew_point(Long dew_point) {
                this.dew_point = dew_point;
        }

        public Long getDt() {
                return dt;
        }

        public void setDt(Long dt) {
                this.dt = dt;
        }

        public Long getSunrise() {
                return sunrise;
        }

        public void setSunrise(Long sunrise) {
                this.sunrise = sunrise;
        }

        public Long getSunset() {
                return sunset;
        }

        public void setSunset(Long sunset) {
                this.sunset = sunset;
        }



        public Long getUvi() {
                return uvi;
        }

        public void setUvi(Long uvi) {
                this.uvi = uvi;
        }

        public Integer getClouds() {
                return clouds;
        }

        public void setClouds(Integer clouds) {
                this.clouds = clouds;
        }

        public Integer getVisibility() {
                return visibility;
        }

        public void setVisibility(Integer visibility) {
                this.visibility = visibility;
        }



        public List<Weather> getWeather() {
                return weather;
        }

        public void setWeather(List<Weather> weather) {
                this.weather = weather;
        }

        @Override
        public String toString() {
                String returnString =  "WeatherObjOneCall{" +
                        "uvi=" + Long.toString(uvi) +
                        ", clouds=" + Integer.toString(clouds) +
                        ", visibility=" + Integer.toString(visibility) +
                        ", pop=" + Long.toString(pop) +
                        ", wind_speed=" + Long.toString(wind_speed) +
                        ", wind_deg=" + Integer.toString(wind_deg) +
                        ", pressure=" + Integer.toString(pressure) +
                        ", humidity=" + Integer.toString(humidity) +
                        ", dew_point=" + Long.toString(dew_point) +
                        ", dt=" + Long.toString(dt) +
                        ", sunrise=" + Long.toString(sunrise) +
                        ", sunset=" + Long.toString(sunset) +
                        ", weather={" + weather.toString() +"}"+

                        '}';
                return returnString;
        }
}
