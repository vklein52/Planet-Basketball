package edu.illinois.finalproject.SimulationFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vijay on 11/13/2017.
 */

public class StringGenerator {

    private final static String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static String NUMBERS = "0123456789";

    /**
     * Helper function to create a random string of length, length
     *
     * @param length The desired length of the random String.
     * @return An all lower case string contanting length random characters
     */
    public static String genRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                stringBuilder.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
            } else {
                stringBuilder.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
            }
        }
        return stringBuilder.toString();
    }

    public static String genRandomFaceKey() {
        return LETTERS.charAt(RandomUtils.randInt(26, LETTERS.length() - 1)) + "" + genRandomString(1);
    }

    /**
     * @param numStrings   The number of Strings to be generated
     * @param stringLength The length of the Strings to be generated
     * @return A list containing numStrings Strings all of length stringLength
     */
    static List<String> genRandomStrings(int numStrings, int stringLength) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < numStrings; i++) {
            strings.add(genRandomString(stringLength));
        }
        return strings;
    }
}