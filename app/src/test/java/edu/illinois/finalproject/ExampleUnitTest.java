package edu.illinois.finalproject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void randomTest() throws Exception {
        String newLine = "Hello world!\n";
        String helloWorld = "Hello world!";
        assertEquals(newLine.substring(0, newLine.length() - 1), helloWorld);
    }
}