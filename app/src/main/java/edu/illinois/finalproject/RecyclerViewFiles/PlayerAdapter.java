package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.Position;
import edu.illinois.finalproject.SimulationFiles.RandomUtils;

/**
 * Created by vijay on 12/6/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private List<Player> players;
    private List<Player> playersCopy;
    private Context context;

    //Todo 3: Create the Comparators!
    private Comparator comparator;

    public PlayerAdapter(List<Player> players, Context context) {
        this.players = players;
        playersCopy = new ArrayList<>();
        playersCopy.addAll(players);
        this.context = context;
        //sortData();
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.player_list_item, parent, false);

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

    void sortData() {
        Collections.sort(players, comparator);
        this.notifyDataSetChanged();
    }

//    void filter(String text) {
//        //Below code found and then edited from
//        //https://stackoverflow.com/questions/30398247/how-to-filter-a-recyclerview-with-a-searchview
//        leagues.clear();
//        if (text.isEmpty()) {
//            leagues.addAll(leaguesCopy);
//        } else {
//            text = text.toLowerCase();
//            for (League item : leaguesCopy) {
//                if (item.get.contains(text)) {
//                    leagues.add(item);
//                }
//            }
//        }
//        sortData();
//    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        ImageView playerFace;
        TextView nameView;
        TextView heightView;
        TextView posView;
        TextView ovrView;
        TextView potView;
        TextView ageView;

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
        }

        /**
         * Binds a Player to the ViewHolder
         *
         * @param player The Player to bind
         */
        void bind(final Player player) {
            nameView.setText(player.getName());

            //Todo 5: Figure out how to put quotes here
            String height = "6'" + RandomUtils.randInt(0, 11) + "''";
            heightView.setText(height);

            posView.setText(Position.asAbbreviatedString(player.getPosition()));

            String ovrText = "Overall: " + player.overall();
            ovrView.setText(ovrText);

            //Todo 6: Improve method of obtaining attributes
            String potText = "Potential: " + player.getAttributes().get("potential").intValue();
            potView.setText(potText);

            String ageText = "Age: " + player.getAge();
            ageView.setText(ageText);
        }
    }
}
