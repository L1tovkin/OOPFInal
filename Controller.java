package project;

import java.io.IOException;
import java.util.HashMap;

public class Controller {
    private WeatherModel weatherModel = new AccuWeatherModel();
    private WeatherApiCom weatherApiModel = new WeatherApiCom();
    private HashMap<Integer, Period> variants = new HashMap<>();
    private HashMap<Integer, WeatherServices> models = new HashMap<>();

    public Controller() {
        variants.put(1, Period.ONE_DAY);
        variants.put(5, Period.FIVE_DAYS);
        models.put(1, WeatherServices.ACCU_WEATHER);
        models.put(2, WeatherServices.WEATHER_API);
    }
    public void getWeather(String command, String city, String source) throws IOException {
        Integer userCommand = Integer.parseInt(command);
        Integer dataSource = Integer.parseInt(source);
        switch (variants.get(userCommand)) {
            case ONE_DAY:
                switch (models.get(dataSource)) {
                    case ACCU_WEATHER: weatherModel.getWeather(city, Period.ONE_DAY);break;
                    case WEATHER_API: weatherApiModel.getWeather(city, Period.ONE_DAY);break;
                }
                break;
            case FIVE_DAYS:
                switch (models.get(dataSource)) {
                    case ACCU_WEATHER: weatherModel.getWeather(city, Period.FIVE_DAYS);break;
                    case WEATHER_API: weatherApiModel.getWeather(city, Period.FIVE_DAYS);break;
                }
                break;
        }
    }
}