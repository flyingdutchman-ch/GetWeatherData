package functions;

import com.google.cloud.datastore.*;


import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import functions.openweather.CurrentWeather;
import functions.openweather.OneCallWeather;
import functions.openweather.weatherobj.*;

import java.util.logging.Logger;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StoreOpenWeatherData  implements HttpFunction {

    private String openWeatherUri = "http://api.openweathermap.org/data/2.5/weather?";
    private String oneCallWeatherUri = "https://api.openweathermap.org/data/2.5/onecall?";
    private String openWeatherAPIKey = "1c7159d6798504c4664dc78c53f015ac";
    private String units = "metric";

    private static final Logger log = Logger.getLogger(StoreOpenWeatherData.class.getName());
    private static final Gson gson = new Gson();


    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {

        String city = httpRequest.getFirstQueryParameter("city").orElse("Zurich");
        try {
            JsonElement requestParsed = gson.fromJson(httpRequest.getReader(), JsonElement.class);
            JsonObject requestJson = null;
            if (requestParsed != null && requestParsed.isJsonObject()) {
                requestJson = requestParsed.getAsJsonObject();
            }

            if (requestJson != null && requestJson.has("city")) {
                city = requestJson.get("city").getAsString();
            }
        } catch (JsonParseException e) {
            log.severe("Error parsing JSON: " + e.getMessage());

        }

        String key = storeRecordInDatastore(city);

        BufferedWriter writer = httpResponse.getWriter();
        writer.write("RecordStored "+ key);
    }

    public CurrentWeather getOpenWeatherApiResponse(String city){
        String uri = openWeatherUri + "q=" + city + "&appid=" + openWeatherAPIKey + "&units=" + units;
        RestTemplate restTemplate = new RestTemplate();
        CurrentWeather currentWeather = restTemplate.getForObject(uri, CurrentWeather.class);
        return currentWeather;
    }

    public OneCallWeather getOneCallWeatherApiResponse(long lon, long lat){
        String uri = oneCallWeatherUri + "lon=" + Long.toString(lon) + "&lat="+ Long.toString(lat) +"&appid=" + openWeatherAPIKey + "&units=" + units;
        RestTemplate restTemplate = new RestTemplate();
        OneCallWeather oneCallWeather = restTemplate.getForObject(uri, OneCallWeather.class);
        return oneCallWeather;
    }
    public String storeForecast(String station, long lon, long lat){
        OneCallWeather oneCallWeather = getOneCallWeatherApiResponse(lon, lat);
        Datastore db = DatastoreOptions.getDefaultInstance().getService();
        String kind = "ForecastWeatherData_OW";
        String main_key_str = station + Long.toString(oneCallWeather.getCurrent().getDt());
        Key main_forecast_key = db.newKeyFactory().setKind(kind).newKey(main_key_str);

        Weather weather = new Weather();
        try{
            weather = oneCallWeather.getCurrent().getWeather().get(0);
        } catch(ArrayIndexOutOfBoundsException aioobe){
            weather.setDescription("N/A");
            weather.setIcon("0");
            weather.setMain("N/A");
            weather.setId(0);
        }

        Entity main_forecast_entity = Entity.newBuilder(main_forecast_key)
                .set("Station", station)
                .set("Lat", lat)
                .set("Lon", lon)
                .set("Timezone", oneCallWeather.getTimezone())
                .set("TimezoneOffset", oneCallWeather.getTimezone_offset())
                .set("current.dt", oneCallWeather.getCurrent().getDt())
                .set("current.sunrise", oneCallWeather.getCurrent().getSunrise())
                .set("current.sunset", oneCallWeather.getCurrent().getSunset())
                .set("current.temp", oneCallWeather.getCurrent().getTemp())
                .set("current.feels_like", oneCallWeather.getCurrent().getFeels_like())
                .set("current.pressure", oneCallWeather.getCurrent().getPressure())
                .set("current.humidity", oneCallWeather.getCurrent().getHumidity())
                .set("current.dewpoint", oneCallWeather.getCurrent().getDew_point())
                .set("current.uvi", oneCallWeather.getCurrent().getUvi())
                .set("current.cloud", oneCallWeather.getCurrent().getClouds())
                .set("current.visibility", oneCallWeather.getCurrent().getVisibility())
                .set("current.wind_speed", oneCallWeather.getCurrent().getWind_speed())
                .set("current.wind_deg", oneCallWeather.getCurrent().getWind_deg())
                .set("current.weather.id", weather.getId())
                .set("current.weather.main", weather.getMain())
                .set("current.weather.description", weather.getDescription())
                .set("current.weather.icon", weather.getIcon())
                .build();
        db.put(main_forecast_entity);

        String hourly_keys = storeHourlyForecastInDatabase(oneCallWeather, station, main_key_str);
        String daily_keys = storeDailyForecastInDatabase(oneCallWeather, station, main_key_str);



        return "WeatherDataStored: " + main_key_str + "\n HOURLY DATA: "+hourly_keys+ "\n DAILY DATA: "+daily_keys;
    }

    public String storeHourlyForecastInDatabase(OneCallWeather oneCallWeather, String station, String mainKey){
        Datastore db = DatastoreOptions.getDefaultInstance().getService();
        String returnVal = "";
        for(Current hourly : oneCallWeather.getHourly()){
            KeyFactory keyFactory = db.newKeyFactory().addAncestors(PathElement.of("ForecastWeatherData_OW", mainKey))
                    .setKind("ForecastHourlyData_OW");
            Key hourly_key = keyFactory.newKey("Hourly"+station+Long.toString(hourly.getDt()));
            Weather hourly_weather = new Weather();
            try{
                hourly_weather = hourly.getWeather().get(0);
            } catch(ArrayIndexOutOfBoundsException aioobe){
                hourly_weather.setDescription("N/A");
                hourly_weather.setIcon("0");
                hourly_weather.setMain("N/A");
                hourly_weather.setId(0);
            }
            if(hourly.getSnow() == null){hourly.setSnow(new Snow());}
            if(hourly.getRain() == null){hourly.setRain(new Rain());}
            Entity hourly_forecast_entity = Entity.newBuilder(hourly_key)
                    .set("hourly.dt", hourly.getDt())
                    .set("hourly.temp", hourly.getTemp())
                    .set("hourly.feels_like", hourly.getFeels_like())
                    .set("hourly.pressure", hourly.getPressure())
                    .set("hourly.humidity", hourly.getHumidity())
                    .set("hourly.dew_point", hourly.getDew_point())
                    .set("hourly.uvi", hourly.getUvi())
                    .set("hourly.clouds", hourly.getClouds())
                    .set("hourly.visibility", hourly.getVisibility())
                    .set("hourly.wind_speed", hourly.getWind_speed())
                    .set("hourly.wind_deg", hourly.getWind_deg())
                    .set("hourly.wind_gust", hourly.getWind_gust())
                    .set("hourly.weather.id", hourly_weather.getId())
                    .set("hourly.weather.icon", hourly_weather.getIcon())
                    .set("hourly.weather.description", hourly_weather.getDescription())
                    .set("hourly.weather.main", hourly_weather.getMain())
                    .set("hourly.pop", hourly.getPop())
                    .set("hourly.rain.1h", hourly.getRain().getRain_1h())
                    .set("hourly.snow.1h", hourly.getSnow().getSnow_1h()).build();

            db.put(hourly_forecast_entity);
            returnVal = returnVal + " - " + hourly_key;
        }
        return returnVal;
    }

    public String storeDailyForecastInDatabase(OneCallWeather oneCallWeather, String station, String mainKey){
        Datastore db = DatastoreOptions.getDefaultInstance().getService();
        String returnVal = "";
        for(Daily daily : oneCallWeather.getDaily()) {
            KeyFactory keyFactory = db.newKeyFactory().addAncestors(PathElement.of("ForecastWeatherData_OW", mainKey))
                    .setKind("ForecastDailyData_OW");
            Key daily_key = keyFactory.newKey("Daily" + station + Long.toString(daily.getDt()));
            Weather daily_weather = new Weather();
            try {
                daily_weather = daily.getWeather().get(0);
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                daily_weather.setDescription("N/A");
                daily_weather.setIcon("0");
                daily_weather.setMain("N/A");
                daily_weather.setId(0);
            }

            Entity daily_forecast_entity = Entity.newBuilder(daily_key)
                    .set("daily.dt", daily.getDt())
                    .set("daily.sunrise", daily.getSunrise())
                    .set("daily.sunset", daily.getSunrise())
                    .set("daily.temp.day", daily.getTemp().getDay())
                    .set("daily.temp.min", daily.getTemp().getMin())
                    .set("daily.temp.max", daily.getTemp().getMax())
                    .set("daily.temp.night", daily.getTemp().getNight())
                    .set("daily.temp.eve", daily.getTemp().getEve())
                    .set("daily.temp.morn", daily.getTemp().getMorn())
                    .set("daily.feels_like.day", daily.getFeels_like().getDay())
                    .set("daily.feels_like.night", daily.getFeels_like().getNight())
                    .set("daily.feels_like.eve", daily.getFeels_like().getEve())
                    .set("daily.feels_like.morn", daily.getFeels_like().getMorn())
                    .set("daily.pressure", daily.getPressure())
                    .set("daily.humidity", daily.getHumidity())
                    .set("daily.dew_point", daily.getDew_point())
                    .set("daily.uvi", daily.getUvi())
                    .set("daily.clouds", daily.getClouds())
                    .set("daily.wind_speed", daily.getWind_speed())
                    .set("daily.wind_deg", daily.getWind_deg())
                    .set("daily.weather.id", daily_weather.getId())
                    .set("daily.weather.icon", daily_weather.getIcon())
                    .set("daily.weather.description", daily_weather.getDescription())
                    .set("daily.weather.main", daily_weather.getMain())
                    .set("daily.pop", daily.getPop()).build();
            db.put(daily_forecast_entity);
            returnVal = returnVal + " - " + daily_key;
        }
        return returnVal;
    }

    public String storeRecordInDatastore(String city){
        CurrentWeather weatherData = getOpenWeatherApiResponse(city);
        Datastore db = DatastoreOptions.getDefaultInstance().getService();
        String kind = "OpenWeatherData";
        String key = weatherData.getName() + Long.toString(weatherData.getDt());
        Key taskKey = db.newKeyFactory().setKind(kind).newKey(key);
        Weather currentWeatherInfo = new Weather();
        try{
           currentWeatherInfo = weatherData.getWeather().get(0);
        } catch(ArrayIndexOutOfBoundsException aioobe){
            currentWeatherInfo.setDescription("N/A");
            currentWeatherInfo.setIcon("0");
            currentWeatherInfo.setMain("N/A");
            currentWeatherInfo.setId(0);
        }

        Entity task = Entity.newBuilder(taskKey)
                .set("Station", weatherData.getName())
                .set("UnitUsed", units)
                .set("Timezone", weatherData.getTimezone())
                .set("Country", weatherData.getSys().getCountry())
                .set("Lon", weatherData.getCoord().getLon())
                .set("Lat", weatherData.getCoord().getLat())
                .set("UpdateTS", weatherData.getDt())
                .set("UpdateTS_Human", convertUnixTime(weatherData.getDt()))
                .set("Main.Temp", weatherData.getMain().getTemp())
                .set("Main.MaxTemp", weatherData.getMain().getTemp_max())
                .set("Main.MinTemp", weatherData.getMain().getTemp_min())
                .set("Main.Pressure", weatherData.getMain().getPressure())
                .set("Main.Humidity", weatherData.getMain().getHumidity())
                .set("Main.Visibility", weatherData.getVisibility())
                .set("WeatherCondition", currentWeatherInfo.getMain())
                .set("WeatherDescription", currentWeatherInfo.getDescription())
                .set("WeatherOWIconID", currentWeatherInfo.getIcon())
                .set("Wind.Speed", weatherData.getWind().getSpeed())
                .set("Wind.Degree", weatherData.getWind().getDeg())
                .set("Clouds", weatherData.getClouds().getAll())
                .set("Sunrise", weatherData.getSys().getSunrise())
                .set("Sunset", weatherData.getSys().getSunset())
                .build();
        //asynchronously write data
        String forecast = storeForecast(weatherData.getName(), weatherData.getCoord().getLon(), weatherData.getCoord().getLat());
        db.put(task);

        return "Entity Stored: Key" + key + "\n"+forecast;
    }

    public String convertUnixTime(Long unixts){
        Date dateTimeOfGen = new Date(unixts*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(dateTimeOfGen);
        return formattedDate;
    }

}