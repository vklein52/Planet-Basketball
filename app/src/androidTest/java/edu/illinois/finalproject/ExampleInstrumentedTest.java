package edu.illinois.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import edu.illinois.finalproject.SimulationFiles.League;
import edu.illinois.finalproject.SimulationFiles.User;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.illinois.finalproject", appContext.getPackageName());
    }

    @Test
    public void createLeague() throws Exception {
        League league = new League();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Leagues").child(league.getId());

        final CountDownLatch writeSignal = new CountDownLatch(20);
        myRef.setValue(league).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // end as soon as the write completes, or
                        Log.d("TEST", "Wrote League");
                        writeSignal.countDown();
                    }
                });
        // give up after 10 seconds if the above call to countDown never happens
        writeSignal.await(10, TimeUnit.SECONDS);

    }

    @Test
    public void createUser() throws Exception {
        User user = new User("J08BhnwC2taieuG3LWQIbAitzbH2");
        user.appendLeagueIds("N3Kfy1u0626GO0z");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users").child(user.getUid());

        final CountDownLatch writeSignal = new CountDownLatch(20);
        myRef.setValue(user).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // end as soon as the write completes, or
                        Log.d("TEST", "Wrote League");
                        writeSignal.countDown();
                    }
                });
        // give up after 10 seconds if the above call to countDown never happens
        writeSignal.await(10, TimeUnit.SECONDS);

    }


}
