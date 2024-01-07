package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// This class represents that each restaurant has a name, a list of environment score and a list of dishes.
// This class also has a list of restaurant which contain all the restaurant input by users.
public class Restaurant {
    private List<EnvironmentScore> environmentScores;
    private List<Dish> dishes;
    private String name;

    public Restaurant(String name) {
        this.name = name;
        environmentScores = new ArrayList<>();
        dishes = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if the given name doesn't exist in List<Dish> dishes add it into dishes,
    //          else do nothing
    public void addDish(String name) {
        int i = 0;
        for (Dish d : dishes) {
            if (d.getName().equals(name)) {
                i = 1;
                break;
            }
        }
        if (i == 0) {
            dishes.add(new Dish(name));
            EventLog.getInstance().logEvent(new Event("Add new dish without score: "
                    + name + " at restaurant: " + this.name));
        }
    }

    // REQUIRES: taste, appearance and quality are in [1, 9]
    // MODIFIES: this
    // EFFECTS: if the given name exists in the List<Dish> dishes,
    //             add a new dish with taste, appearance and quality,
    //          else add scores for that dish with given name use taste, appearance and quality
    public void addGradedDish(String name, int taste, int appearance, int quality) {
        int index = -1;
        for (int i = 0; i < dishes.size(); i++) {
            if (dishes.get(i).getName().equals(name)) {
                index = i;
            }
        }
        if (index == -1) {
            dishes.add(new Dish(name, taste, appearance, quality));
            EventLog.getInstance().logEvent(new Event("Add new dish with score: "
                    + name + " at restaurant: " + this.name));
        } else {
            dishes.get(index).addScore(taste, appearance, quality);
            EventLog.getInstance().logEvent(new Event("Add new score to existed dish: "
                    + name + " at restaurant: " + this.name));
        }
    }

    // MODIFIES: this
    // EFFECTS: add a new environment score into List<EnvironmentScore> environmentScores
    public void addEnvironmentScore(int ambience, int serviceQuality, int cleanliness) {
        environmentScores.add(new EnvironmentScore(ambience, serviceQuality, cleanliness));
        EventLog.getInstance().logEvent(new Event("Add new environment score at restaurant: " + this.name));
    }

    // EFFECTS: calculate the total value of ambience, serviceQuality and cleanliness,
    //          add  divided by 3 * total size of environmentScores,
    //          no environment score in list return -1
    public double rateEnvironmentScores() {
        double totalAmbience = 0;
        double totalServiceQuality = 0;
        double totalCleanliness = 0;
        int total = environmentScores.size();
        if (total == 0) {
            EventLog.getInstance().logEvent(new Event("No environment score for " + name));
            return -1;
        } else {
            for (EnvironmentScore e : environmentScores) {
                totalAmbience = totalAmbience + e.getAmbience();
                totalServiceQuality = totalServiceQuality + e.getServiceQuality();
                totalCleanliness = totalCleanliness + e.getCleanliness();
            }
            double totalScore = (totalAmbience + totalServiceQuality + totalCleanliness) / (3 * total);
            EventLog.getInstance().logEvent(new Event("Average environment score for " + this.name + " : "
                    + totalScore));
            return totalScore;
        }
    }

    // EFFECTS: only calculate the average of average scores for those dishes which have graded),
    //          no dish in list return -1
    public double rateDishScores() {
        double dishScore = 0;
        int total = 0;
        if (dishes.size() == 0) {
            EventLog.getInstance().logEvent(new Event("No dishes score for " + name));
            return -1;
        } else {
            for (Dish d : dishes) {
                if (d.rateDish() != -1) {
                    dishScore = dishScore + d.rateDish();
                    total = total + 1;
                }
            }
            double totalScore = dishScore / total;
            EventLog.getInstance().logEvent(new Event("Average dishes score for " + this.name + " : "
                    + totalScore));
            return totalScore;
        }
    }

    // EFFECTS: calculate the average of dish score and environment score
    public double rateRestaurant() {
        if ((rateEnvironmentScores() == -1) || (rateDishScores() == -1)) {
            EventLog.getInstance().logEvent(new Event("No restaurant score for " + name));
            return -1;
        } else {
            double totalScore = (rateDishScores() + rateEnvironmentScores()) / 2;
            EventLog.getInstance().logEvent(new Event("Average score for " + this.name + " : "
                    + totalScore));
            return totalScore;
        }

    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<EnvironmentScore> getEnvironmentScores() {
        return environmentScores;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: returns restaurant as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("environment scores", environmentScoresToJson());
        json.put("dishes", dishesToJson());
        return json;
    }

    // EFFECTS: returns environment scores in this restaurant as a JSON array
    private JSONArray environmentScoresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (EnvironmentScore t : environmentScores) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns dishes in this restaurant as a JSON array
    private JSONArray dishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Dish t : dishes) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}
