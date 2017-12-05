package edu.illinois.finalproject.SimulationFiles;

import java.util.HashMap;

/**
 * Created by vijay on 11/28/2017.
 */

public class AttributeMap {
    private static String[] attributeNames = {"speed", "layup", "close", "midrange", "threes", "passing", "dribbling", "defending", "steal", "block", "rebounding", "awareness",
            "strength", "vertical", "size", "stamina", "potential"};

    private HashMap<String, Double> attributes;

    public AttributeMap() {
        populateAttributes();
    }

    private void populateAttributes() {
        attributes = new HashMap<>();
        for (String name : attributeNames) {
            attributes.put(name, Math.random() * 100);
        }
    }

    public HashMap<String, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Double> attributes) {
        this.attributes = attributes;
    }
}
