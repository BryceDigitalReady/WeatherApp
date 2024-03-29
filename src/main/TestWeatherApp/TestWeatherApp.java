import com.example.weatherexample.WeatherApp;
import com.example.weatherexample.WeatherFetch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestWeatherApp extends WeatherApp {
    @Test
    public void testWeather() throws IOException {
        double lat = 21.76086;
        double lon = 29.08888;
        Assertions.assertNotNull(WeatherFetch.fetchWeather(lat, lon));
    }
    @Test
    public void testCityToLatLonBoston() throws IOException {
        String city = "boston";
        String stateCode = "MA";
        Assertions.assertNotNull(WeatherFetch.CityToLatLon("Boston", "MA"));
    }
    @Test
    public void testCityToLatLonNewYork() throws IOException {
        String city = "New Work";
        String stateCode = "NY";
        Assertions.assertNull(null);
    }
}

