package project;

import java.util.HashMap;

public class ToPrint {

    private String queryResult;
    WeatherServices sourceModel;
    Period type;
    public void outResults(String queryResult, WeatherServices sourceModel, Period type) {
        JsonToData jsonData = new JsonToData(queryResult, sourceModel);
        jsonData.getRequestedData();
    }
}