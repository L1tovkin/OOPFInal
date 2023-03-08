package project;

public class JsonToData {
    private String json;
    private WeatherServices model;


    public JsonToData(String json, WeatherServices model) {
        this.json = json;
        this.model = model;
    }

    public void getRequestedData() {
        switch (model) {
            case ACCU_WEATHER:
                JsonAccuWeather dataAccuWeather = new JsonAccuWeather(json);
                dataAccuWeather.getData();
                break;
            case WEATHER_API:
                JsonWeatherApi dataWeatherApi = new JsonWeatherApi(json);
                dataWeatherApi.getData();
                break;
        }
    }


}