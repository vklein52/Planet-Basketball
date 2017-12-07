package edu.illinois.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class GameDetailActivity extends AppCompatActivity {

    public static final String GAME = "GAME";

    private TextView homeName;
    private TextView homeScore;
    private TextView awayName;
    private TextView awayScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);


    }
}
