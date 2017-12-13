package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.illinois.finalproject.R;

/**
 * Created by vijay on 12/12/2017.
 */

public class OnlineUserAdapter extends RecyclerView.Adapter<OnlineUserAdapter.OnlineUserViewHolder> {

    private List<String> onlineUsers;
    private Context context;

    public OnlineUserAdapter(List<String> onlineUsers, Context context) {
        this.onlineUsers = onlineUsers;
        this.context = context;
    }


    @Override
    public OnlineUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.online_user_item, parent, false);

        return new OnlineUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OnlineUserViewHolder holder, int position) {
        holder.bind(onlineUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return onlineUsers.size();
    }

    class OnlineUserViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        TextView emailText;

        public OnlineUserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            this.emailText = (TextView) itemView.findViewById(R.id.user_item_email_text);
        }

        public void bind(String email) {
            emailText.setText(email);
        }
    }
}
