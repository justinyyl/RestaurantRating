package model;

import org.json.JSONObject;

// This class represents environment score with three sub-standards: ambience, serviceQuality, cleanliness.
// each sub-standard is in [1, 9]
public class EnvironmentScore {
    private int ambience;
    private int serviceQuality;
    private int cleanliness;

    public EnvironmentScore(int ambience, int serviceQuality, int cleanliness) {
        this.ambience = ambience;
        this.serviceQuality = serviceQuality;
        this.cleanliness = cleanliness;
    }

    public int getAmbience() {
        return ambience;
    }

    public int getCleanliness() {
        return cleanliness;
    }

    public int getServiceQuality() {
        return serviceQuality;
    }

    // EFFECTS: returns environment score as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("ambience", ambience);
        json.put("serviceQuality", serviceQuality);
        json.put("cleanliness", cleanliness);
        return json;
    }
}
