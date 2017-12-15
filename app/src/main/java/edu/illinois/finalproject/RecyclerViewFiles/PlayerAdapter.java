package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.illinois.finalproject.Activities.AlertDialogFactory;
import edu.illinois.finalproject.Activities.PlayerDetailActivity;
import edu.illinois.finalproject.MenuFiles.PlayerComparator;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Draft;
import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.Position;

/**
 * Created by vijay on 12/6/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private Draft draft;
    private List<Player> players;
    private List<Player> playersCopy;
    private Context context;
    private boolean isDraftLayout;
    private Comparator comparator;

    /**
     * @param players       The players that will populate this adapter
     * @param comparator    The comparator for sorting the Players
     * @param isDraftLayout A boolean indicating whether this adapter should use the additional draft
     *                      capabilities
     * @param context       The context under which this adapter exists
     */
    public PlayerAdapter(List<Player> players, PlayerComparator comparator, boolean isDraftLayout, Context context) {
        this.players = players;
        playersCopy = new ArrayList<>();
        playersCopy.addAll(players);
        this.comparator = comparator;
        sortData();
        this.isDraftLayout = isDraftLayout;
        this.context = context;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        int layout = isDraftLayout ? R.layout.draft_player_list_item : R.layout.player_list_item;

        View view = inflater.inflate(layout, parent, false);

        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        holder.bind(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setDraft(Draft draft) {
        this.draft = draft;
        players = draft.getAvailablePlayers();
        playersCopy = new ArrayList<>();
        playersCopy.addAll(players);
        sortData();
        notifyDataSetChanged();
    }

    /**
     * Sorts the players according to the current settings of the Comparator
     */
    void sortData() {
        Collections.sort(players, comparator);
        this.notifyDataSetChanged();
    }

    /**
     * Filters the adapter for a given search query
     * @param text  The search query text
     */
    void filter(String text) {
        //Below code found and then edited from
        //https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
        players.clear();
        if (text.isEmpty()) {
            players.addAll(playersCopy);
        } else {
            text = text.toLowerCase();
            for (Player item : playersCopy) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    players.add(item);
                }
            }
        }
        sortData();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView playerFace;
        TextView nameView;
        TextView heightView;
        TextView posView;
        TextView ovrView;
        TextView potView;
        TextView ageView;
        Button draftButton;

        /**
         * Initializes the Views contained by this ViewHolder
         *
         * @param itemView The parent view (the entire cell this ViewHolder populates).
         */
        PlayerViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            playerFace = (ImageView) itemView.findViewById(R.id.player_face_view);
            nameView = (TextView) itemView.findViewById(R.id.player_name_text);
            heightView = (TextView) itemView.findViewById(R.id.player_height_text);
            posView = (TextView) itemView.findViewById(R.id.player_position_text);
            ovrView = (TextView) itemView.findViewById(R.id.player_overall_text);
            potView = (TextView) itemView.findViewById(R.id.player_potential_text);
            ageView = (TextView) itemView.findViewById(R.id.player_age_text);
            if (isDraftLayout) {
                draftButton = (Button) itemView.findViewById(R.id.draft_player_button);
            }
        }

        /**
         * Binds a Player to the ViewHolder
         *
         * @param player The Player to bind
         */
        void bind(final Player player) {
            playerFace.setImageDrawable(null);
            StorageReference faceRef = FirebaseStorage.getInstance().getReference().child("faces/" + player.getKey());
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(faceRef)
                    .into(playerFace);


            nameView.setText(player.getName());

            heightView.setText(player.displayHeight());

            posView.setText(Position.asAbbreviatedString(player.getPosition()));

            String ovrText = "Overall: " + player.overall();
            ovrView.setText(ovrText);

            //Todo 6: Improve method of obtaining attributes
            String potText = "Potential: " + player.getAttributes().get("potential").intValue();
            potView.setText(potText);

            String ageText = "Age: " + player.getAge();
            ageView.setText(ageText);

            if (isDraftLayout) {
                draftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

                        //If button is clicked, we know that the draft is not null
                        String currPick = draft.getCurrTeamSelectingEmail();
                        final String mEmail = fbUser.getEmail();

                        if (currPick.equals(mEmail)) {
                            //Curr user is selecting team
                            AlertDialog.OnClickListener positive = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    draft.draftPlayer(mEmail, player);

                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference draftRef = database.getReference("Drafts").child(draft.getKey());
                                    draftRef.setValue(draft);
                                }
                            };

                            AlertDialog.OnClickListener negative = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Did not draft player", Toast.LENGTH_SHORT).show();
                                }
                            };

                            String message = "Are you sure you want to draft " + player.getName() + "?";
                            AlertDialogFactory.buildAlertDialog(message, "Draft Player", positive, negative, context).show();
                        } else {
                            //Curr user cannot select at this time
                            String message = "It is not your turn to draft.";
                            AlertDialogFactory.buildAlertDialog(message, "Wait", context).show();
                        }
                    }
                });
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayerDetailActivity.class);
                    intent.putExtra(PlayerDetailActivity.PLAYER, player);
                    intent.putExtra(PlayerDetailActivity.IS_DRAFT_LAYOUT, isDraftLayout);
                    context.startActivity(intent);
                }
            });

        }
    }
}
