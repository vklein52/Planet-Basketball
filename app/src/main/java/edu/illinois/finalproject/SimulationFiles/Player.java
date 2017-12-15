package edu.illinois.finalproject.SimulationFiles;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private static String[] attributeNames = {"speed", "layup", "inside", "close", "midrange", "threes", "dunks", "passing", "dribbling", "defending", "steal", "block", "rebounding", "awareness",
            "strength", "vertical", "size", "stamina", "potential"};
    private static final double MEAN_HEIGHT = 79;
    private static final double STD_DEV_HEIGHT = 3.5;
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 33;

    private static List<String> firstNames;
    private static List<String> lastNames;

    private Map<String, Double> attributes;
    private String name;
    private int age;
    private double height;
    private Position position;
    private String key;
    private byte[] face;

    public Player() {

    }

    public Player(Position position) {
        populateAttributes();
        name = genName();
        age = RandomUtils.randInt(MIN_AGE, MAX_AGE);
        height = RandomUtils.randGaussian(MEAN_HEIGHT, STD_DEV_HEIGHT);
        this.position = position;
        key = StringGenerator.genRandomFaceKey();
    }

    private void populateAttributes() {
        attributes = new LinkedHashMap<>();
        for (String name : attributeNames) {
            attributes.put(name, Math.random() * 100);
        }
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

    public Drawable faceAsDrawable(Context context) {
        return Faces.byteArrayToDrawable(face, context);
    }

    public byte[] face() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }

    public String displayHeight() {
        //For rounding
        int heightInt = (int) (height + 0.5);
        return (heightInt / 12) + "'" + (heightInt % 12) + "''";
    }

    public int overall() {
        //Since ovr cant be 0 unless uninitialized
        double ovr = 0.0;
        for (double d : attributes.values()) {
            ovr += d;
        }
        return ((int) ovr) / attributeNames.length;
    }

    public String genName() {
        return RandomUtils.randomElementOf(firstNames) + " " + RandomUtils.randomElementOf(lastNames);
    }

//    public List<Byte> getFace() {
//        return face;
//    }
//
//    public void setFace(List<Byte> face) {
//        this.face = face;
//    }
//
//    public Drawable faceAsDrawable(Context context) {
//        return Faces.byteListToDrawable(face, context);
//    }

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
        dest.writeByteArray(this.face);
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
        this.face = in.createByteArray();
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
