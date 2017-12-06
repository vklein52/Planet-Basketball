package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.illinois.finalproject.LeagueViewActvity;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;

/**
 * Created by vijay on 12/5/2017.
 */

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private List<League> leagues;
    private List<League> leaguesCopy;
    private Context context;

    //Todo 3: Create the Comparators!
    private Comparator comparator;

    public LeagueAdapter(List<League> leagues, Context context) {
        this.leagues = leagues;
        leaguesCopy = new ArrayList<>();
        leaguesCopy.addAll(leagues);
        this.context = context;
        //sortData();
    }

    @Override
    public LeagueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.league_list_item, parent, false);

        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeagueViewHolder holder, int position) {
        holder.bind(leagues.get(position));
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    void sortData() {
        Collections.sort(leagues, comparator);
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

    class LeagueViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView leagueBetweenTextView;
        TextView firstTeam;
        TextView secondTeam;

        /**
         * Initializes the Views contained by this ViewHolder
         *
         * @param itemView The parent view (the entire cell this ViewHolder populates).
         */
        LeagueViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            leagueBetweenTextView = (TextView) itemView.findViewById(R.id.league_item_text_view);
            firstTeam = (TextView) itemView.findViewById(R.id.first_team_name_text_view);
            secondTeam = (TextView) itemView.findViewById(R.id.second_team_name_text_view);
        }

        /**
         * Binds a League to the ViewHolder
         *
         * @param league The String to bind
         */
        void bind(final League league) {
            //Todo 4: Possibly improve DB structure to make prettier look
            firstTeam.setText(league.firstTeam().getName());
            secondTeam.setText(league.secondTeam().getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LeagueViewActvity.class);
                    intent.putExtra(LeagueViewActvity.LEAGUE, league);
                    context.startActivity(intent);
                }
            });

        }
    }
}
