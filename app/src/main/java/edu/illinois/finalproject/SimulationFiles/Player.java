package edu.illinois.finalproject.SimulationFiles;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.finalproject.R;

/**
 * Created by vijay on 11/28/2017.
 */

public class Player implements Comparable<Player>, Parcelable {
    private static final String[] attributeNames = {"speed", "layup", "inside", "close", "midrange", "threes", "dunks", "passing", "dribbling", "defending", "steal", "block", "rebounding", "awareness",
            "strength", "vertical", "size", "stamina", "potential"};
    private static final String[] offensiveStats = {"speed", "layup", "inside", "close", "midrange", "threes", "dunks", "passing", "dribbling", "awareness", "vertical", "stamina"};
    private static final String[] defensiveStats = {"speed", "defending", "steal", "block", "rebounding", "awareness", "strength", "vertical", "size", "stamina"};

    private static final double PG_MEAN_HEIGHT = 74;
    private static final double PG_STD_DEV_HEIGHT = 1;
    private static final double SG_MEAN_HEIGHT = 78;
    private static final double SG_STD_DEV_HEIGHT = 2;
    private static final double SF_MEAN_HEIGHT = 81;
    private static final double SF_STD_DEV_HEIGHT = 1;
    private static final double PF_MEAN_HEIGHT = 82;
    private static final double PF_STD_DEV_HEIGHT = 1;
    private static final double C_MEAN_HEIGHT = 84;
    private static final double C_STD_DEV_HEIGHT = 1;

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 33;

    private static Map<String, Double> offensiveArchetypeBoosts;
    private static Map<String, Double> defensiveArchetypeBoosts;
    private static Map<String, Double> athleticArchetypeBoosts;
    private static Map<String, Double> pointGuardBoosts;
    private static Map<String, Double> shootingGuardBoosts;
    private static Map<String, Double> smallForwardBoosts;
    private static Map<String, Double> powerForwardBoosts;
    private static Map<String, Double> centerBoosts;

    private static List<String> firstNames;
    private static List<String> lastNames;

    private Map<String, Double> attributes;
    private String name;
    private int age;
    private double height;
    private Position position;
    private String key;

    public Player() {

    }

    public Player(Position position) {
        this.position = position;
        populateAttributes();
        name = genName();
        age = RandomUtils.randInt(MIN_AGE, MAX_AGE);
        key = StringGenerator.genRandomFaceKey();
    }

    public Map<String, Double> getAttributes() {
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

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String displayHeight() {
        //For rounding
        int heightInt = (int) (height + 0.5);
        return (heightInt / 12) + "'" + (heightInt % 12) + "''";
    }

    public double offensiveRating() {
        double ovr = 0.0;
        for (String offensiveStat : offensiveStats) {
            ovr += attributes.get(offensiveStat);
        }
        return formatStat(ovr / offensiveStats.length);
    }

    public double defensiveRating() {
        double ovr = 0.0;
        for (String defensiveStat : defensiveStats) {
            ovr += attributes.get(defensiveStat);
        }
        return formatStat(ovr / defensiveStats.length);
    }

    public int overall() {
        return (int) (offensiveRating() + defensiveRating() + 0.5) / 2;
    }

    public String genName() {
        return RandomUtils.randomElementOf(firstNames) + " " + RandomUtils.randomElementOf(lastNames);
    }

    /**
     * Generates archetypical players, randomly
     */
    private void populateAttributes() {
        if (offensiveArchetypeBoosts == null) {
            populateBoosts();
        }

        Map<String, Double> archetypeBoosts;
        switch (RandomUtils.randInt(0, 2)) {
            case 0: {
                archetypeBoosts = offensiveArchetypeBoosts;
                break;
            }
            case 1: {
                archetypeBoosts = defensiveArchetypeBoosts;
                break;
            }
            case 2: {
                archetypeBoosts = athleticArchetypeBoosts;
                break;
            }
            default: {
                archetypeBoosts = athleticArchetypeBoosts;
            }
        }

        Map<String, Double> positionalBoosts;
        switch (position) {
            case POINT_GUARD: {
                height = RandomUtils.randGaussian(PG_MEAN_HEIGHT, PG_STD_DEV_HEIGHT);
                positionalBoosts = pointGuardBoosts;
                break;
            }
            case SHOOTING_GUARD: {
                height = RandomUtils.randGaussian(SG_MEAN_HEIGHT, SG_STD_DEV_HEIGHT);
                positionalBoosts = shootingGuardBoosts;
                break;
            }
            case SMALL_FORWARD: {
                height = RandomUtils.randGaussian(SF_MEAN_HEIGHT, SF_STD_DEV_HEIGHT);
                positionalBoosts = smallForwardBoosts;
                break;
            }
            case POWER_FORWARD: {
                height = RandomUtils.randGaussian(PF_MEAN_HEIGHT, PF_STD_DEV_HEIGHT);
                positionalBoosts = powerForwardBoosts;
                break;
            }
            case CENTER: {
                height = RandomUtils.randGaussian(C_MEAN_HEIGHT, C_STD_DEV_HEIGHT);
                positionalBoosts = centerBoosts;
                break;
            }
            default: {
                height = RandomUtils.randGaussian(SG_MEAN_HEIGHT, SG_STD_DEV_HEIGHT);
                positionalBoosts = shootingGuardBoosts;
                break;
            }
        }
        attributes = new LinkedHashMap<>();

        for (String name : attributeNames) {
            double val = formatStat(RandomUtils.randGaussian(20, 10) + positionalBoosts.get(name) + archetypeBoosts.get(name));
            attributes.put(name, val);
        }
    }

    private double formatStat(double val) {
        if (val < 0.0) {
            return 0.0;
        } else if (val > 100.0) {
            return 100.0;
        } else {
            return val;
        }
    }

    /**
     * Extremely ugly, but necessary, method that populates the positional and archetypical boosts
     */
    private void populateBoosts() {
        offensiveArchetypeBoosts = new LinkedHashMap<>();
        offensiveArchetypeBoosts.put("speed", 10.0);
        offensiveArchetypeBoosts.put("layup", 35.0);
        offensiveArchetypeBoosts.put("inside", 30.0);
        offensiveArchetypeBoosts.put("close", 27.0);
        offensiveArchetypeBoosts.put("midrange", 25.0);
        offensiveArchetypeBoosts.put("threes", 22.0);
        offensiveArchetypeBoosts.put("dunks", 25.0);
        offensiveArchetypeBoosts.put("passing", 25.0);
        offensiveArchetypeBoosts.put("dribbling", 25.0);
        offensiveArchetypeBoosts.put("defending", 5.0);
        offensiveArchetypeBoosts.put("steal", 5.0);
        offensiveArchetypeBoosts.put("block", 5.0);
        offensiveArchetypeBoosts.put("rebounding", 10.0);
        offensiveArchetypeBoosts.put("awareness", 15.0);
        offensiveArchetypeBoosts.put("strength", 15.0);
        offensiveArchetypeBoosts.put("vertical", 10.0);
        offensiveArchetypeBoosts.put("size", 10.0);
        offensiveArchetypeBoosts.put("stamina", 10.0);
        offensiveArchetypeBoosts.put("potential", 15.0);

        defensiveArchetypeBoosts = new LinkedHashMap<>();
        defensiveArchetypeBoosts.put("speed", 15.0);
        defensiveArchetypeBoosts.put("layup", 10.0);
        defensiveArchetypeBoosts.put("inside", 8.0);
        defensiveArchetypeBoosts.put("close", 6.0);
        defensiveArchetypeBoosts.put("midrange", 4.0);
        defensiveArchetypeBoosts.put("threes", 2.0);
        defensiveArchetypeBoosts.put("dunks", 10.0);
        defensiveArchetypeBoosts.put("passing", 10.0);
        defensiveArchetypeBoosts.put("dribbling", 10.0);
        defensiveArchetypeBoosts.put("defending", 25.0);
        defensiveArchetypeBoosts.put("steal", 25.0);
        defensiveArchetypeBoosts.put("block", 25.0);
        defensiveArchetypeBoosts.put("rebounding", 20.0);
        defensiveArchetypeBoosts.put("awareness", 20.0);
        defensiveArchetypeBoosts.put("strength", 20.0);
        defensiveArchetypeBoosts.put("vertical", 15.0);
        defensiveArchetypeBoosts.put("size", 15.0);
        defensiveArchetypeBoosts.put("stamina", 15.0);
        defensiveArchetypeBoosts.put("potential", 20.0);

        athleticArchetypeBoosts = new LinkedHashMap<>();
        athleticArchetypeBoosts.put("speed", 35.0);
        athleticArchetypeBoosts.put("layup", 30.0);
        athleticArchetypeBoosts.put("inside", 20.0);
        athleticArchetypeBoosts.put("close", 10.0);
        athleticArchetypeBoosts.put("midrange", 5.0);
        athleticArchetypeBoosts.put("threes", 2.0);
        athleticArchetypeBoosts.put("dunks", 25.0);
        athleticArchetypeBoosts.put("passing", 10.0);
        athleticArchetypeBoosts.put("dribbling", 10.0);
        athleticArchetypeBoosts.put("defending", 20.0);
        athleticArchetypeBoosts.put("steal", 20.0);
        athleticArchetypeBoosts.put("block", 20.0);
        athleticArchetypeBoosts.put("rebounding", 15.0);
        athleticArchetypeBoosts.put("awareness", 5.0);
        athleticArchetypeBoosts.put("strength", 35.0);
        athleticArchetypeBoosts.put("vertical", 35.0);
        athleticArchetypeBoosts.put("size", 30.0);
        athleticArchetypeBoosts.put("stamina", 30.0);
        athleticArchetypeBoosts.put("potential", 35.0);

        pointGuardBoosts = new LinkedHashMap<>();
        pointGuardBoosts.put("speed", 20.0);
        pointGuardBoosts.put("layup", 30.0);
        pointGuardBoosts.put("inside", 20.0);
        pointGuardBoosts.put("close", 20.0);
        pointGuardBoosts.put("midrange", 15.0);
        pointGuardBoosts.put("threes", 15.0);
        pointGuardBoosts.put("dunks", 0.0);
        pointGuardBoosts.put("passing", 35.0);
        pointGuardBoosts.put("dribbling", 35.0);
        pointGuardBoosts.put("defending", 10.0);
        pointGuardBoosts.put("steal", 15.0);
        pointGuardBoosts.put("block", -5.0);
        pointGuardBoosts.put("rebounding", 0.0);
        pointGuardBoosts.put("awareness", 30.0);
        pointGuardBoosts.put("strength", 5.0);
        pointGuardBoosts.put("vertical", 10.0);
        pointGuardBoosts.put("size", 0.0);
        pointGuardBoosts.put("stamina", 20.0);
        pointGuardBoosts.put("potential", 0.0);

        shootingGuardBoosts = new LinkedHashMap<>();
        shootingGuardBoosts.put("speed", 20.0);
        shootingGuardBoosts.put("layup", 25.0);
        shootingGuardBoosts.put("inside", 25.0);
        shootingGuardBoosts.put("close", 20.0);
        shootingGuardBoosts.put("midrange", 20.0);
        shootingGuardBoosts.put("threes", 20.0);
        shootingGuardBoosts.put("dunks", 10.0);
        shootingGuardBoosts.put("passing", 15.0);
        shootingGuardBoosts.put("dribbling", 20.0);
        shootingGuardBoosts.put("defending", 15.0);
        shootingGuardBoosts.put("steal", 15.0);
        shootingGuardBoosts.put("block", 0.0);
        shootingGuardBoosts.put("rebounding", 5.0);
        shootingGuardBoosts.put("awareness", 20.0);
        shootingGuardBoosts.put("strength", 10.0);
        shootingGuardBoosts.put("vertical", 15.0);
        shootingGuardBoosts.put("size", 5.0);
        shootingGuardBoosts.put("stamina", 15.0);
        shootingGuardBoosts.put("potential", 0.0);

        smallForwardBoosts = new LinkedHashMap<>();
        smallForwardBoosts.put("speed", 15.0);
        smallForwardBoosts.put("layup", 20.0);
        smallForwardBoosts.put("inside", 20.0);
        smallForwardBoosts.put("close", 20.0);
        smallForwardBoosts.put("midrange", 15.0);
        smallForwardBoosts.put("threes", 15.0);
        smallForwardBoosts.put("dunks", 15.0);
        smallForwardBoosts.put("passing", 15.0);
        smallForwardBoosts.put("dribbling", 15.0);
        smallForwardBoosts.put("defending", 20.0);
        smallForwardBoosts.put("steal", 15.0);
        smallForwardBoosts.put("block", 10.0);
        smallForwardBoosts.put("rebounding", 10.0);
        smallForwardBoosts.put("awareness", 15.0);
        smallForwardBoosts.put("strength", 15.0);
        smallForwardBoosts.put("vertical", 15.0);
        smallForwardBoosts.put("size", 10.0);
        smallForwardBoosts.put("stamina", 15.0);
        smallForwardBoosts.put("potential", 0.0);

        powerForwardBoosts = new LinkedHashMap<>();
        powerForwardBoosts.put("speed", 10.0);
        powerForwardBoosts.put("layup", 25.0);
        powerForwardBoosts.put("inside", 25.0);
        powerForwardBoosts.put("close", 20.0);
        powerForwardBoosts.put("midrange", 15.0);
        powerForwardBoosts.put("threes", 5.0);
        powerForwardBoosts.put("dunks", 20.0);
        powerForwardBoosts.put("passing", 10.0);
        powerForwardBoosts.put("dribbling", 10.0);
        powerForwardBoosts.put("defending", 15.0);
        powerForwardBoosts.put("steal", 10.0);
        powerForwardBoosts.put("block", 20.0);
        powerForwardBoosts.put("rebounding", 20.0);
        powerForwardBoosts.put("awareness", 15.0);
        powerForwardBoosts.put("strength", 20.0);
        powerForwardBoosts.put("vertical", 10.0);
        powerForwardBoosts.put("size", 15.0);
        powerForwardBoosts.put("stamina", 10.0);
        powerForwardBoosts.put("potential", 0.0);

        centerBoosts = new LinkedHashMap<>();
        centerBoosts.put("speed", 5.0);
        centerBoosts.put("layup", 25.0);
        centerBoosts.put("inside", 25.0);
        centerBoosts.put("close", 20.0);
        centerBoosts.put("midrange", 0.0);
        centerBoosts.put("threes", 0.0);
        centerBoosts.put("dunks", 25.0);
        centerBoosts.put("passing", 0.0);
        centerBoosts.put("dribbling", 0.0);
        centerBoosts.put("defending", 25.0);
        centerBoosts.put("steal", 10.0);
        centerBoosts.put("block", 30.0);
        centerBoosts.put("rebounding", 30.0);
        centerBoosts.put("awareness", 0.0);
        centerBoosts.put("strength", 30.0);
        centerBoosts.put("vertical", 20.0);
        centerBoosts.put("size", 30.0);
        centerBoosts.put("stamina", 0.0);
        centerBoosts.put("potential", 0.0);
    }
    
    @Override
    public int compareTo(@NonNull Player o) {
        return overall() - o.overall();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (age != player.age) return false;
        if (Double.compare(player.height, height) != 0) return false;
        if (attributes != null ? !attributes.equals(player.attributes) : player.attributes != null)
            return false;
        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        if (position != player.position) return false;
        return key != null ? key.equals(player.key) : player.key == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = attributes != null ? attributes.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        temp = Double.doubleToLongBits(height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    public static void initializeNames(Context context) {
        firstNames = new ArrayList<>();
        BufferedReader br;
        String result;
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.first_names);
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            result = new String(bytes);

            br = new BufferedReader(new StringReader(result));
            String temp;

            while ((temp = br.readLine()) != null) {
                String name = temp.substring(0, temp.indexOf(' '));
                name = name.substring(0, 1) + name.substring(1).toLowerCase();
                firstNames.add(name);
            }
            br.close();
        } catch (Exception e) {
            firstNames.add("Smith");
            firstNames.add("Jones");
        }

        lastNames = new ArrayList<>();
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.last_names);
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            result = new String(bytes);

            br = new BufferedReader(new StringReader(result));
            String temp;

            while ((temp = br.readLine()) != null) {
                String name = temp.substring(0, temp.indexOf(' '));
                name = name.substring(0, 1) + name.substring(1).toLowerCase();
                lastNames.add(name);
            }
            br.close();
        } catch (Exception e) {
            lastNames.add("Smith");
            lastNames.add("Jones");
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.attributes.size());
        for (Map.Entry<String, Double> entry : this.attributes.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
        dest.writeString(this.name);
        dest.writeInt(this.age);
        dest.writeDouble(this.height);
        dest.writeInt(this.position == null ? -1 : this.position.ordinal());
        dest.writeString(this.key);
    }

    protected Player(Parcel in) {
        int attributesSize = in.readInt();
        this.attributes = new HashMap<String, Double>(attributesSize);
        for (int i = 0; i < attributesSize; i++) {
            String key = in.readString();
            Double value = (Double) in.readValue(Double.class.getClassLoader());
            this.attributes.put(key, value);
        }
        this.name = in.readString();
        this.age = in.readInt();
        this.height = in.readDouble();
        int tmpPosition = in.readInt();
        this.position = tmpPosition == -1 ? null : Position.values()[tmpPosition];
        this.key = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
