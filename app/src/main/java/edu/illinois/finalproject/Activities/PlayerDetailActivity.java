package edu.illinois.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.Position;

public class PlayerDetailActivity extends AppCompatActivity {

    public static final String PLAYER = "PLAYER";
    public static final String IS_DRAFT_LAYOUT = "IS_DRAFT_LAYOUT";

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
        Intent intent = getIntent();

        player = intent.getParcelableExtra(PLAYER);
        setTitle(player.getName());

        initializeViews();
        populateViews();
    }

    /**
     * Initializes all of the View references
     */
    private void initializeViews() {
        faceView = (ImageView) findViewById(R.id.player_detail_face);
        heightView = (TextView) findViewById(R.id.player_detail_height);
        ageView = (TextView) findViewById(R.id.player_detail_age);
        posView = (TextView) findViewById(R.id.player_detail_position);
        firstHalfAttributesView = (TextView) findViewById(R.id.player_detail_first_attributes);
        secondHalfAttributesView = (TextView) findViewById(R.id.player_detail_second_attributes);
    }

    /**
     * Populates the Views with the Player information passed to the Activity
     */
    private void populateViews() {
        StorageReference faceRef = FirebaseStorage.getInstance().getReference().child("faces/" + player.getKey());
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(faceRef)
                .into(faceView);
        heightView.setText(player.displayHeight());

        String ageText = "Age: " + player.getAge();
        ageView.setText(ageText);

        posView.setText(Position.asString(player.getPosition()));

        populateAttributeViews();
    }

    /**
     * Populates the two respective Attribute TextViews
     */
    private void populateAttributeViews() {
        Map<String, Double> attributes = player.getAttributes();
        int index = attributes.size() / 2;
        int i = 0;

        StringBuilder firstBuilder = new StringBuilder();
        String ovrLine = "overall: " + player.overall() + "\n";
        firstBuilder.append(ovrLine);
        StringBuilder secondBuilder = new StringBuilder();

        for (Map.Entry<String, Double> entry : attributes.entrySet()) {
            String line = entry.getKey() + ": " + entry.getValue().intValue() + "\n";
            if (i++ < index) {
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
