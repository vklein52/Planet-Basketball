package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.illinois.finalproject.Activities.GameDetailActivity;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Game;

/**
 * Created by vijay on 12/6/2017.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<Game> games;
    private Context context;

    /**
     * @param games   The games for this adapter
     * @param context The context this adapter exists within
     */
    public GameAdapter(List<Game> games, Context context) {
        this.games = games;
        this.context = context;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.game_list_item, parent, false);

        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        holder.bind(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView awayTeamNameView;
        TextView awayTeamScoreView;
        TextView homeTeamNameView;
        TextView homeTeamScoreView;

        /**
         * Initializes the Views contained by this ViewHolder
         *
         * @param itemView The parent view (the entire cell this ViewHolder populates).
         */
        GameViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            awayTeamNameView = (TextView) itemView.findViewById(R.id.game_list_item_away_team_name);
            awayTeamScoreView = (TextView) itemView.findViewById(R.id.game_list_item_away_team_score);
            homeTeamNameView = (TextView) itemView.findViewById(R.id.game_list_item_home_team_name);
            homeTeamScoreView = (TextView) itemView.findViewById(R.id.game_list_item_home_team_score);
        }

        /**
         * Binds a Game to the ViewHolder
         *
         * @param game The Game to bind
         */
        void bind(final Game game) {
            awayTeamNameView.setText(game.getAwayName());
            String awayTeamScore = game.getAwayScore() + "";
            awayTeamScoreView.setText(awayTeamScore);
            homeTeamNameView.setText(game.getHomeName());
            String homeTeamScore = game.getHomeScore() + "";
            homeTeamScoreView.setText(homeTeamScore);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, GameDetailActivity.class);
                    intent.putExtra(GameDetailActivity.GAME, game);
                    context.startActivity(intent);
                }
            });
        }
    }
}
