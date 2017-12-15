package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.illinois.finalproject.Activities.LeagueViewActivity;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.League;

/**
 * Created by vijay on 12/5/2017.
 */

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private List<League> leagues;
    private Context context;

    /**
     * @param leagues The leagues this adapter will display
     * @param context The context this adapter exists within
     */
    public LeagueAdapter(List<League> leagues, Context context) {
        this.leagues = leagues;
        this.context = context;
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
         * @param league The League to bind
         */
        void bind(final League league) {
            //Todo 4: Possibly improve DB structure to make prettier look
            firstTeam.setText(league.firstTeam().getName());
            secondTeam.setText(league.secondTeam().getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LeagueViewActivity.class);
                    intent.putExtra(LeagueViewActivity.LEAGUE, league);
                    context.startActivity(intent);
                }
            });

        }
    }
}
