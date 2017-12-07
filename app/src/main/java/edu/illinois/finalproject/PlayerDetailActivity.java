package edu.illinois.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.Position;
import edu.illinois.finalproject.SimulationFiles.RandomUtils;

public class PlayerDetailActivity extends AppCompatActivity {

    public static final String PLAYER = "PLAYER";

    private Player player;

    private ImageView faceView;
    private TextView heightView;
    private TextView ageView;
    private TextView posView;
    private TextView firstHalfAttributesView;
    private TextView secondHalfAttributesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);

        player = getIntent().getParcelableExtra(PLAYER);

        initializeViews();
        populateViews();
    }

    private void initializeViews() {
        faceView = (ImageView) findViewById(R.id.player_detail_face);
        heightView = (TextView) findViewById(R.id.player_detail_height);
        ageView = (TextView) findViewById(R.id.player_detail_age);
        posView = (TextView) findViewById(R.id.player_detail_position);
        firstHalfAttributesView = (TextView) findViewById(R.id.player_detail_first_attributes);
        secondHalfAttributesView = (TextView) findViewById(R.id.player_detail_second_attributes);
    }

    private void populateViews() {
        //Todo Faces stuff here and height stuff for the player

        String heightText = "6'" + RandomUtils.randInt(0, 11) + "''";
        heightView.setText(heightText);

        String ageText = "Age: " + player.getAge();
        ageView.setText(ageText);

        posView.setText(Position.asString(player.getPosition()));

        populateAttributeViews();
    }

    private void populateAttributeViews() {
        Map<String, Double> attributes = player.getAttributes();
        int index = attributes.size() / 2;
        int i = 0;
        StringBuilder firstBuilder = new StringBuilder();
        StringBuilder secondBuilder = new StringBuilder();
        for (Map.Entry<String, Double> entry : attributes.entrySet()) {
            String line = entry.getKey() + ": " + entry.getValue().intValue() + "\n";
            if (i++ <= index) {
                firstBuilder.append(line);
            } else {
                secondBuilder.append(line);
            }
        }
        String firstText = firstBuilder.toString();
        firstText = firstText.substring(0, firstText.length() - 1);
        String secondText = secondBuilder.toString();
        secondText = secondText.substring(0, secondText.length() - 1);

        firstHalfAttributesView.setText(firstText);
        secondHalfAttributesView.setText(secondText);
    }
}
