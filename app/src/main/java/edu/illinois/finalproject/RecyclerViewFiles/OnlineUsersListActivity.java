package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.User;

public class OnlineUsersListActivity extends AppCompatActivity {

    public static final String USER = "USER";
    private static final String IS_ONLINE = "isOnline";
    private static final String INVITATIONS = "Invitations";

    private User user;
    private List<String> onlineUsers = new ArrayList<>();
    private OnlineUserAdapter onlineUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_users_list);
        setTitle("Online Users");

        initializeRecyclerView();
        updateOnlineUsers();

        user = getIntent().getParcelableExtra(USER);
        handleInvitations();
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.online_user_list_recycler_view);

        onlineUserAdapter = new OnlineUserAdapter(onlineUsers, this);
        recyclerView.setAdapter(onlineUserAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void updateOnlineUsers() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OnlineUsers");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.getKey().equals(user.getUid())) {
                    onlineUsers.add(dataSnapshot.getValue(String.class));
                    onlineUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                onlineUsers.remove(dataSnapshot.getValue(String.class));
                onlineUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //onlineUsers.clear();
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    onlineUsers.add(userSnapshot.getValue(String.class));
//                }
//                onlineUserAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void handleInvitations() {
        DatabaseReference invRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child(INVITATIONS);
        invRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String otherEmail = dataSnapshot.getValue(String.class);
                buildAlertDialog(otherEmail);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OnlineUsers").child(user.getUid());
            myRef.onDisconnect().removeValue();
            myRef.setValue(user.getEmail());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OnlineUsers").child(user.getUid());
            myRef.onDisconnect().removeValue();
            myRef.removeValue();
        }
    }

    private void buildAlertDialog(String otherEmail) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(otherEmail + " has invited you to start a league!")
                .setTitle("Invitation");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: START THE LEAGUE
            }
        });
        builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // TODO: GET RID OF THE INVITATION
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
