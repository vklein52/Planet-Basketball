package edu.illinois.finalproject.SimulationFiles;

import java.util.List;
import java.util.Random;

/**
 * Created by vijay on 11/28/2017.
 */

public class RandomUtils {

    /**
     * @param mean   The mean of the distribution
     * @param stdDev The standard deviation of the distribution
     * @return A random gaussian value with mean mean and standard deviation stdDev
     */
    public static double randGaussian(double mean, double stdDev) {
        Random random = new Random();
        return (mean + (stdDev * random.nextGaussian()));
    }

    /**
     * @param low  The lower bound
     * @param high The upper bound
     * @return A random integer between the lower and upper bound
     */
    public static int randInt(int low, int high) {
        Random random = new Random();
        return low + random.nextInt(high - low + 1);
    }

    /**
     * @param list The list to get a random element from
     * @param <T>  The data type of the list
     * @return A random element in the list
     */
    public static <T> T randomElementOf(List<T> list) {
        int length = list.size();
        return list.get(randInt(0, length - 1));
    }
}
