package edu.illinois.finalproject.SimulationFiles;

import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public class RandomUtils {

    public static double randGaussian(double mean, double stdDev) {
        Random random = new Random();
        return (mean + (stdDev * random.nextGaussian()));
    }

    public static int randInt(int low, int high) {
        Random random = new Random();
        return low + random.nextInt(high - low + 1);
    }

    public static int randIntExclude(int low, int high, int exclude) {
        int temp;
        do {
            temp = randInt(low, high);
        } while (temp == exclude);
        return temp;
    }

}
