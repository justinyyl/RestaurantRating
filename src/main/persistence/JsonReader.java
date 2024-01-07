package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads ResApp from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ResApp from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ResApp read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseResApp(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ResApp from JSON object and returns it
    private ResApp parseResApp(JSONObject jsonObject) {
        ResApp ra = new ResApp();
        addRestaurants(ra, jsonObject);
        return ra;
    }

    // MODIFIES: ra
    // EFFECTS: parses restaurants from JSON object and adds them to workroom
    private void addRestaurants(ResApp ra, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        int index = 0;
        for (Object json : jsonArray) {
            JSONObject nextRestaurant = (JSONObject) json;
            addRestaurant(ra, nextRestaurant, index);
            index++;
        }
    }

    // MODIFIES: ra
    // EFFECTS: parses restaurant from JSON object and adds it to ResApp
    private void addRestaurant(ResApp ra, JSONObject jsonObject, int index) {
        String name = jsonObject.getString("name");
        ra.addRestaurant(name);
        Restaurant rs = ra.getRestaurants().get(index);
        addEnvironmentScores(rs, jsonObject.getJSONArray("environment scores"));
        addDishes(rs,jsonObject.getJSONArray("dishes"));
    }

    // MODIFIES: rs
    // EFFECTS: parses environment scores from JSON array and adds it to ResApp
    private void addEnvironmentScores(Restaurant rs, JSONArray jsonArray) {
        for (Object json : jsonArray) {
            JSONObject nextEN = (JSONObject) json;
            addEnvironmentScore(rs, nextEN);
        }
    }

    // MODIFIES: rs
    // EFFECTS: parses environment score from JSON object and adds it to workroom
    private void addEnvironmentScore(Restaurant rs, JSONObject jsonObject) {
        int ambience = jsonObject.getInt("ambience");
        int serviceQuality = jsonObject.getInt("serviceQuality");
        int cleanliness = jsonObject.getInt("cleanliness");
        rs.addEnvironmentScore(ambience, serviceQuality, cleanliness);
    }

    // MODIFIES: rs
    // EFFECTS: parses dishes from JSON array and adds it to ResApp
    private void addDishes(Restaurant rs, JSONArray jsonArray) {
        int index = 0;
        for (Object json : jsonArray) {
            JSONObject nextD = (JSONObject) json;
            addDish(rs, nextD, index);
        }
    }

    // MODIFIES: rs, dish
    // EFFECTS: parses dish from JSON object and adds it to restaurant
    private void addDish(Restaurant rs, JSONObject jsonObject, int index) {
        String name = jsonObject.getString("name");
        rs.addDish(name);
        Dish dish = rs.getDishes().get(index);
        JSONArray tastes = jsonObject.getJSONArray("tastes");
        JSONArray appearances = jsonObject.getJSONArray("appearances");
        JSONArray qualities = jsonObject.getJSONArray("qualities");
        for (int i = 0; i < tastes.length(); i++) {
            int taste = (int) tastes.get(i);
            int appearance = (int) appearances.get(i);
            int quality = (int) qualities.get(i);
            dish.addScore(taste, appearance, quality);
        }
    }
}
