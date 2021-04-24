package functions.openweather.weatherobj;

public class Clouds{
    private Integer all;
    public Clouds(){}
    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

    @Override
    public String toString() {
        String retVal = "CLOUDS >> Cloudiness (%): " + Integer.toString(getAll()) + "%";
        return retVal;
    }

}
