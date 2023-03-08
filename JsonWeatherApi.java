package project;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;

public class JsonWeatherApi {
    private static final ObjectMapper mapper = new ObjectMapper();

    private String json;

    public JsonWeatherApi(String json) {
        this.json = json;
    }

    public void getData() {
        try {
            JsonNode root = mapper.readTree(json);
            boolean one = true;
            JsonNode location = root.path("location");
            String name = location.path("name").asText();
            String country = location.path("country").asText();

            JsonNode forecast = root.path("forecast");
            JsonNode forecastday = forecast.path("forecastday");
            for (JsonNode element : forecastday) {
                String date = element.path("date").asText();
                JsonNode day = element.path("day");
                double temperature = day.path("avgtemp_c").asDouble();
                double wind = day.path("maxwind_kph").asDouble();
                double humidity = day.path("avghumidity").asDouble();
                JsonNode condition = element.path("condition");
                String text = condition.path("text").asText();

                String result = (one)? String.format("\n%S %s\n", name, country) : "\n";
                result += String.format("%s\n", date) +
                        String.format("температура: %.1f C\n", temperature) +
                        String.format("ветер: %.1f км/ч\n", wind) +
                        String.format("влажность: %.1f%%\n", humidity) +
                        String.format("%s", text);
                System.out.println(result);
                one = false;
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}