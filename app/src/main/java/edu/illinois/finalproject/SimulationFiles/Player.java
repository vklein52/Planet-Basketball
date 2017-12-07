package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public class Player implements Comparable<Player>, Parcelable {
    //Will be put into JSON in final app, but opted for convenience as this is only tests
    private static String[] attributeNames = {"speed", "layup", "close", "midrange", "threes", "passing", "dribbling", "defending", "steal", "block", "rebounding", "awareness",
            "strength", "vertical", "size", "stamina", "potential"};

    private Map<String, Double> attributes;
    private String name;
    private int age;
    private Position position;
    private int overall;
//    private List<Byte> face;

    //public Player(){};

    public Player() {
        Random random = new Random();
        populateAttributes();
        name = StringGenerator.genRandomString(6);
        age = 17 + random.nextInt(5);
        position = Position.getRandomPosition();

//        int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, Resources.getSystem().getDisplayMetrics());
//        face = Faces.makeFace(dim, dim);
    }

    private void populateAttributes() {
        attributes = new HashMap<>();
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

    public int overall() {
        //Since ovr cant be 0 unless uninitialized
        double ovr = 0.0;
        for (double d : attributes.values()) {
            ovr += d;
        }
        return ((int) ovr) / attributeNames.length;
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
        dest.writeInt(this.position == null ? -1 : this.position.ordinal());
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
        int tmpPosition = in.readInt();
        this.position = tmpPosition == -1 ? null : Position.values()[tmpPosition];
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

    @Override
    public int compareTo(@NonNull Player o) {
        return overall() - o.overall();
    }
}
