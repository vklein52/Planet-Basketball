package edu.illinois.finalproject.RecyclerViewFiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.finalproject.AlertDialogFactory;
import edu.illinois.finalproject.R;
import edu.illinois.finalproject.SimulationFiles.Draft;
import edu.illinois.finalproject.SimulationFiles.OnlineUser;
import edu.illinois.finalproject.SimulationFiles.Player;
import edu.illinois.finalproject.SimulationFiles.StringGenerator;
import edu.illinois.finalproject.SimulationFiles.User;

public class OnlineUsersListActivity extends AppCompatActivity {

    public static final String USER = "USER";
    private static final String IS_ONLINE = "isOnline";
    public static final String INVITATIONS = "Invitations";

    private User user;
    private Context context;
    private List<OnlineUser> onlineUsers = new ArrayList<>();
    private OnlineUserAdapter onlineUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_users_list);
        setTitle("Online Users");

        initializeRecyclerView();
        updateOnlineUsers();

        context = this;
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
                    OnlineUser user = dataSnapshot.getValue(OnlineUser.class);
                    if (!onlineUsers.contains(user)) {
                        onlineUsers.add(user);
                        onlineUserAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                onlineUsers.remove(dataSnapshot.getValue(OnlineUser.class));
                onlineUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void handleInvitations() {
        final DatabaseReference invRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child(INVITATIONS);
        invRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final String otherUid = dataSnapshot.getKey();
                final String otherEmail = dataSnapshot.getValue(String.class);
                String message = otherEmail + " has invited you to start a league!";

                DialogInterface.OnClickListener positive = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invRef.removeValue();
                        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

                        final String draftKey = StringGenerator.genRandomString(15);
                        //No null pointer for email because app requires email authentication
                        Player.initializeNames(context);
                        Draft draft = new Draft(fbUser.getEmail(), fbUser.getUid(), otherEmail, otherUid, draftKey);

                        DatabaseReference draftRef = FirebaseDatabase.getInstance().getReference("Drafts").child(draftKey);
                        draftRef.setValue(draft).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference mDraftRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Drafts").child(draftKey);
                                mDraftRef.setValue(draftKey);
                                DatabaseReference oDraftRef = FirebaseDatabase.getInstance().getReference("Users").child(otherUid).child("Drafts").child(draftKey);
                                oDraftRef.setValue(draftKey);
                            }
                        });
                        //Todo: Start the league
                    }
                };
                DialogInterface.OnClickListener negative = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        invRef.removeValue();
                    }
                };
                AlertDialogFactory.buildAlertDialog(message, "Invitation!", positive, negative, context).show();
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
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            OnlineUser onlineUser = new OnlineUser(user.getEmail(), user.getUid());
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OnlineUsers").child(user.getUid());
            myRef.onDisconnect().removeValue();
            myRef.setValue(onlineUser);

            DatabaseReference draftRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Drafts");
            draftRef.onDisconnect().removeValue();
            draftRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Intent intent = new Intent(context, DraftActivity.class);
                    intent.putExtra(DraftActivity.DRAFT_KEY, dataSnapshot.getKey());
                    startActivity(intent);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("OnlineUsers").child(user.getUid());
            myRef.removeValue();
        }
        //Todo: ORIENTATION CHANGES YA BOZO
    }


}
