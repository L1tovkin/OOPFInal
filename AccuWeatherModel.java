package project;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AccuWeatherModel implements WeatherModel {

    //http://dataservice.accuweather.com/forecasts/v1/daily/1day/{locationKey}
    //http://dataservice.accuweather.com/forecasts/v1/daily/5day/{locationKey}
    private static final String PROTOCOL = "https";
    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECASTS = "forecasts";
    private static final String VERSION = "v1";
    private static final String DAILY = "daily";
    private static final String ONE_DAY = "1day";
    private static final String FIVE_DAYS = "5day";
    private static final String API_KEY = "P9JYzzCqkd8WA47xFQQiqfxGvTGr3zCQ";
    private static final String API_KEY_QUERY_PARAM = "apikey";
    private static final String LOCATIONS = "locations";
    private static final String CITIES = "cities";
    private static final String AUTOCOMPLETE = "autocomplete";

    private static final OkHttpClient okHttpClient = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ToPrint output = new ToPrint();
    private WeatherServices model = WeatherServices.ACCU_WEATHER;
    public void getWeather(String selectedCity, Period period) throws IOException {
        String selectedPeriod = "";
        switch (period) {
            case ONE_DAY:
                selectedPeriod = ONE_DAY;   break;
            case FIVE_DAYS:
                selectedPeriod = FIVE_DAYS; break;
        }

        HttpUrl httpUrl = getHttpUrl(selectedPeriod, selectedCity);

        Request request5 = new Request.Builder()
                .url(httpUrl)
                .build();

        Response forecastResponse = okHttpClient.newCall(request5).execute();
        String weatherResponse = forecastResponse.body().string();
        output.outResults(weatherResponse, model, period);
    }

    private String detectCityKey(String selectCity) throws IOException {
        //http://dataservice.accuweather.com/locations/v1/cities/autocomplete
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(BASE_HOST)
                .addPathSegment(LOCATIONS)
                .addPathSegment(VERSION)
                .addPathSegment(CITIES)
                .addPathSegment(AUTOCOMPLETE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter("q", selectCity)
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String responseString = response.body().string();

        String cityKey = objectMapper.readTree(responseString).get(0).at("/Key").asText();
        return cityKey;
    }

    private HttpUrl getHttpUrl(String specifiedPeriod, String specifiedCity) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(BASE_HOST)
                .addPathSegment(FORECASTS)
                .addPathSegment(VERSION)
                .addPathSegment(DAILY)
                .addPathSegment(specifiedPeriod)
                .addPathSegment(detectCityKey(specifiedCity))
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .build();
        return httpUrl;
    }
}