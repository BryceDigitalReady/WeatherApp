package com.example.weatherexample;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetch {
    private static final String API_KEY = System.getenv("OPENWEATHER_API_KEY");
    private static final String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/3.0/onecall";

    public static JSONObject fetchJSON(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String jsonResponse = response.toString().replaceAll("^\\[|]$", "");
            return new JSONObject(jsonResponse);
        } else {
            throw new IOException("Error fetching data. Response code: " + responseCode);
        }
    }

    public static JSONObject fetchWeather(double lat, double lon) throws IOException {
        String apiURL = String.format("%s?lat=%.2f&lon=%.2f&units=imperial&appid=%s",
                WEATHER_API_BASE_URL,
                lat,
                lon,
                API_KEY);
        URL url = new URL(apiURL);
        return fetchJSON(url);
    }
    public static double[] CityToLatLon(String city, String stateCode) throws IOException {
        String apiURL = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s,%s,US&limit=1&appid=%s",
                city,
                stateCode,
                API_KEY);
        URL url = new URL(apiURL);
        JSONObject json = fetchJSON(url);
        double lat = json.getDouble("lat");
        double lon = json.getDouble("lon");
        return new double[]{lat, lon};
    }
}
