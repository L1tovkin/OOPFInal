package project;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import java.io.IOException;

public class JsonAccuWeather {
    private static final ObjectMapper mapper = new ObjectMapper();
    private String json;

    public JsonAccuWeather(String json) {
        this.json = json;
    }

    public void getData() {
        try {
            JsonNode root = mapper.readTree(json);
            boolean one = true;
            JsonNode forecast = root.path("DailyForecasts");
            for (JsonNode element : forecast) {
                String[] dateFullFormat = element.path("Date").asText().split("T");
                String date = dateFullFormat[0];
                JsonNode temperature = element.path("Temperature");
                    JsonNode maxTemperature = temperature.path("Maximum");
                    double maxT = maxTemperature.path("Value").asDouble();
                    JsonNode minTemperature = temperature.path("Minimum");
                    double minT = maxTemperature.path("Value").asDouble();
                    double toC = ((maxT - minT) / 2 - 32) / 1.8;
                Date today = new Date();
                String pathNow = (today.getHours() + 3 < 17) ? "Day" : "Night";
                JsonNode now = element.path(pathNow);
                String phrase = now.path("IconPhrase").asText();
                String link = element.path("Link").asText();
                int posBegin = link.indexOf("/en") + 7;
                int posEnd = link.indexOf("/", posBegin);
                String city = link.subSequence(posBegin,posEnd).toString();

                String result = (one)? String.format("\n%S\n", city) : "\n";

                result += String.format("%s\n", date) +
                          String.format("температура: %.1f C\n", toC) +
                          String.format("%s", phrase);
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