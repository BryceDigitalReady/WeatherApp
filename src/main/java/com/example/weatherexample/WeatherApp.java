package com.example.weatherexample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.weatherexample.WeatherFetch.CityToLatLon;
import static com.example.weatherexample.WeatherFetch.fetchWeather;

public class WeatherApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TextField cityBox = new TextField();
        cityBox.setPromptText("Enter city");

        TextField stateCode = new TextField();
        stateCode.setPromptText("Enter state");

        Button fetchButton = new Button("Fetch Weather");

        Label Temp = new Label();
        Temp.setText("Temp:");

        Label Weather = new Label();
        Weather.setText("Current Weather:");

        Label Hum = new Label();
        Hum.setText("Humidity:");

        GridPane panel = new GridPane();
        panel.setPadding(new Insets(20));

        panel.setVgap(10);
        panel.setHgap(10);

        panel.add(stateCode, 0,0);
        panel.add(cityBox, 1,0);
        panel.add(fetchButton,0,1,2,1);
        panel.add(Temp,0,2,2,1);
        panel.add(Weather, 0,3,2,1);
        panel.add(Hum, 1,1,2,1);

        Scene scene = new Scene(panel, 300,100);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.show();

        fetchButton.setOnAction(actionEvent -> {
            String City = cityBox.getText();
            String State = stateCode.getText();
                try {
                    double[] latLon =  CityToLatLon(City, State);
                    JSONObject weatherData = fetchWeather(latLon[0],latLon[1]);
                    double Temperature = weatherData
                    .getJSONObject("current")
                    .getDouble("temp");
                    Temp.setText("Temp:" + Temperature + "F");
                    String CurrentWeather = weatherData
                            .getJSONObject("current")
                            .getJSONArray("weather")
                            .getJSONObject(0)
                                    .getString("description");
                    Weather.setText("Current Weather:" + CurrentWeather);
                    double Humidity = weatherData
                            .getJSONObject("current")
                            .getDouble("humidity");
                    Hum.setText("Humidity:"  + Humidity + "%");








                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
                );
    }




    public static void main (String[]args){
                launch();
            }
}
