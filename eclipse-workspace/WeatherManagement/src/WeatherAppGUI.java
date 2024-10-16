import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAppGUI {

    // Method to fetch weather data from OpenWeatherMap API
    public static String getWeatherData(String city, String apiKey) {
        String result = "";
        try {
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            String weather = jsonResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            double temperature = jsonResponse.getJSONObject("main").getDouble("temp");

            result = "Weather: " + weather + "\nTemperature: " + temperature + " °C";
        } catch (Exception e) {
            result = "Error: Unable to fetch weather data!";
        }
        return result;
    }

    public static void main(String[] args) {
        // Set up API key
        String apiKey = "94babda0b771d3820403fb8ba558f7c4"; // Replace with your API key from OpenWeatherMap

        // Create frame for the GUI
        JFrame frame = new JFrame("Weather Management System");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create a label and text field for city input
        JLabel cityLabel = new JLabel("Enter City:");
        cityLabel.setBounds(50, 20, 100, 30);
        frame.add(cityLabel);

        JTextField cityTextField = new JTextField();
        cityTextField.setBounds(150, 20, 150, 30);
        frame.add(cityTextField);

        // Create a button to trigger the weather request
        JButton getWeatherButton = new JButton("Get Weather");
        getWeatherButton.setBounds(50, 70, 250, 30);
        frame.add(getWeatherButton);

        // Create a text area to display the weather data
        JTextArea weatherDisplay = new JTextArea();
        weatherDisplay.setBounds(50, 110, 300, 50);
        weatherDisplay.setLineWrap(true);
        weatherDisplay.setWrapStyleWord(true);
        weatherDisplay.setEditable(false);
        frame.add(weatherDisplay);

        // Action listener for the button click
        getWeatherButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the city name entered by the user
                String city = cityTextField.getText();
                
                // Fetch weather data from the API
                String weatherData = getWeatherData(city, apiKey);
                
                // Display the result in the text area
                weatherDisplay.setText(weatherData);
            }
        });

        // Make the frame visible
        frame.setVisible(true);
    }
}
