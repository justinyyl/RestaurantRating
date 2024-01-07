package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


// This class represents that each dish with name has list of scores
// in each sub-standards: tastes, appearances, qualities.
// // each sub-standard is in [1, 9]
public class Dish {
    private List<Integer> tastes;
    private List<Integer> appearances;
    private List<Integer> qualities;
    private String name;

    public Dish(String name) {
        this.name = name;
        this.tastes = new ArrayList<>();
        this.appearances = new ArrayList<>();
        this.qualities = new ArrayList<>();
    }

    public Dish(String name, int taste, int appearance, int quality) {
        this.name = name;
        this.tastes = new ArrayList<>();
        this.appearances = new ArrayList<>();
        this.qualities = new ArrayList<>();
        tastes.add(taste);
        appearances.add(appearance);
        qualities.add(quality);
    }

    //REQUIRES: taste, appearance and quality are all in [1, 9]
    // MODIFIES: this
    // EFFECTS: add taste into List<Integer> tastes,
    //          add appearance into List<Integer> appearances,
    //          add quality into List<Integer> qualities
    public void addScore(int taste, int appearance, int quality) {
        tastes.add(taste);
        appearances.add(appearance);
        qualities.add(quality);
        EventLog.getInstance().logEvent(new Event("Add new score for " + name));
    }

    // EFFECTS: if tastes.size() != 0
    //              calculate the total value of tastes, appearances and qualities,
    //              add divided by 3 * total size of tastes
    //              since the size of tastes, appearances and qualities are same
    //          else return -1
    public double rateDish() {
        double totalTaste = 0;
        double totalAppearance = 0;
        double totalQuality = 0;
        int total = tastes.size();
        if (total == 0) {
            EventLog.getInstance().logEvent(new Event("No dish score for " + name));
            return -1;
        } else {
            for (int i : tastes) {
                totalTaste = totalTaste + i;
            }
            for (int j : appearances) {
                totalAppearance = totalAppearance + j;
            }
            for (int k : qualities) {
                totalQuality = totalQuality + k;
            }
            double totalScore = (totalTaste + totalAppearance + totalQuality) / (3 * total);
            EventLog.getInstance().logEvent(new Event("Average score for " + name + " : " + totalScore));
            return totalScore;
        }

    }

    public String getName() {
        return name;
    }

    public List<Integer> getAppearances() {
        return appearances;
    }

    public List<Integer> getQualities() {
        return qualities;
    }

    public List<Integer> getTastes() {
        return tastes;
    }

    // EFFECTS: returns dish as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tastes", tastesToJson());
        json.put("appearances", appearancesToJson());
        json.put("qualities", qualitiesToJson());
        return json;
    }

    // EFFECTS: returns tastes in this dish as a JSON array
    private JSONArray tastesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Integer t : tastes) {
            jsonArray.put(t);
        }

        return jsonArray;
    }

    // EFFECTS: returns appearances in this dish as a JSON array
    private JSONArray appearancesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Integer t : appearances) {
            jsonArray.put(t);
        }

        return jsonArray;
    }

    // EFFECTS: returns qualities in this dish as a JSON array
    private JSONArray qualitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Integer t : qualities) {
            jsonArray.put(t);
        }

        return jsonArray;
    }
}
