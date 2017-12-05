package edu.illinois.finalproject.SimulationFiles;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public class Player {
    //Will be put into JSON in final app, but opted for convenience as this is only tests
    private static String[] attributeNames = {"speed", "layup", "close", "midrange", "threes", "passing", "dribbling", "defending", "steal", "block", "rebounding", "awareness",
            "strength", "vertical", "size", "stamina", "potential"};

    private HashMap<String, Double> attributes;
    private String name;
    private int age;
    private Position position;

    public Player() {
        Random random = new Random();
        populateAttributes();
        name = StringGenerator.genRandomString(6);
        age = 17 + random.nextInt(5);
        position = Position.getRandomPosition();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
