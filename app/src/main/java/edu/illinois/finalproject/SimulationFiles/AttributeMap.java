package edu.illinois.finalproject.SimulationFiles;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by vijay on 11/28/2017.
 */

public class AttributeMap implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.attributes);
    }

    protected AttributeMap(Parcel in) {
        this.attributes = (HashMap<String, Double>) in.readSerializable();
    }

    public static final Parcelable.Creator<AttributeMap> CREATOR = new Parcelable.Creator<AttributeMap>() {
        @Override
        public AttributeMap createFromParcel(Parcel source) {
            return new AttributeMap(source);
        }

        @Override
        public AttributeMap[] newArray(int size) {
            return new AttributeMap[size];
        }
    };
}
