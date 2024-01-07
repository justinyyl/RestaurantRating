package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// This class contains a list of restaurants which have been added
public class ResApp {
    private List<Restaurant> restaurants;

    public ResApp() {
        restaurants = new ArrayList<>();
    }

    // REQUIRES: name is not empty
    // MODIFIES: this
    // EFFECTS: if the given name doesn't exist in the List<Restaurant> restaurants,
    //          use the given name to create a new restaurant add into List<Restaurant> restaurant,
    //          else do nothing.
    public void addRestaurant(String name) {
        int i = 0;
        for (Restaurant r : restaurants) {
            if (r.getName().equals(name)) {
                i = 1;
                break;
            }
        }
        if (i == 0) {
            restaurants.add(new Restaurant(name));
            EventLog.getInstance().logEvent(new Event("Add new restaurant: " + name));
        }
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    // EFFECTS: returns ResApp as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("restaurants", restaurantsToJson());
        return json;
    }

    // EFFECTS: returns restaurants in this ResApp as a JSON array
    private JSONArray restaurantsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Restaurant t : restaurants) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
