package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.illinois.finalproject.AlertDialogFactory;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.OnlineUser;

/**
 * Created by vijay on 12/12/2017.
 */

public class OnlineUserAdapter extends RecyclerView.Adapter<OnlineUserAdapter.OnlineUserViewHolder> {

    private FirebaseUser fbUser;
    private List<OnlineUser> onlineUsers;
    private Context context;

    public OnlineUserAdapter(List<OnlineUser> onlineUsers, Context context) {
        fbUser = FirebaseAuth.getInstance().getCurrentUser();
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

        public void bind(final OnlineUser onlineUser) {
            String email = onlineUser.getEmail();
            emailText.setText(email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener positive = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference invRef = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(onlineUser.getUid()).child(OnlineUsersListActivity.INVITATIONS);
                            invRef.child(fbUser.getUid()).setValue(fbUser.getEmail());
                        }
                    };

                    DialogInterface.OnClickListener negative = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Did not send invite.", Toast.LENGTH_LONG).show();
                        }
                    };

                    AlertDialogFactory.buildAlertDialog("Would you like to send an invitation to " +
                                    onlineUser.getEmail() + "?", "Confirmation", positive, negative,
                            context).show();
                }
            });
        }
    }
}
