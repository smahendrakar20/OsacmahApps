package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static android.R.id.list;

public class hostShareCodeActivity extends Activity {

    private TextView codeTextView;
    private ListView mPlayersJoinedLV;
    private mPlayersAdapter playerAdapter;
    public static DatabaseReference mRootRoomsMyRoomPlayersReference;
    public static DatabaseReference mRootRoomsMyRoomPlayersMySelfReference;
    private Button startGame;
    ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_share_code);

        //new references
        mRootRoomsMyRoomPlayersReference=hostOptionsActivity.mRootRoomsMyRoomReference.child("players");
        mRootRoomsMyRoomPlayersMySelfReference=mRootRoomsMyRoomPlayersReference.child(MainActivity.getUsername());

        //Adding host to player list
        playerClass newPlayer=new playerClass(hostOptionsActivity.playerNickName,MainActivity.getUsername(),null,null,null);
        mRootRoomsMyRoomPlayersMySelfReference.setValue(newPlayer);


        startGame=(Button) findViewById(R.id.startGameButtonId);
        mPlayersJoinedLV = (ListView) findViewById(R.id.shareCodeListViewId);
        codeTextView=(TextView) findViewById(R.id.codeToShareTVId);
        codeTextView.setText(hostOptionsActivity.randomRoomName);



        //initialization of the list
        List<playerClass> friendlyMessages = new ArrayList<>();
        playerAdapter = new mPlayersAdapter(this, R.layout.item_player_joined, friendlyMessages);
        mPlayersJoinedLV.setAdapter(playerAdapter);



        mChildEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                playerClass friendlyMessage= dataSnapshot.getValue(playerClass.class);
                playerAdapter.add(friendlyMessage);
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
        };
        mRootRoomsMyRoomPlayersReference.addChildEventListener(mChildEventListener);

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(hostOptionsActivity.numberOfPlayers==mPlayersJoinedLV.getCount()){

                ArrayList<String> characters=new ArrayList<String>();


                    switch (hostOptionsActivity.getNumberOfPlayers())
                    {
                        case 2:
                            characters.add("KING");characters.add("QUEEN");
                            break;
                        case 3:
                            characters.add("KING");characters.add("QUEEN");characters.add("CHOR");
                            break;
                        case 4:
                            characters.add("KING");characters.add("QUEEN");characters.add("POLICE");characters.add("CHOR");
                            break;

                        case 5:
                            characters.add("KING");characters.add("QUEEN");characters.add("POLICE");characters.add("CHOR");characters.add("MANTRI");
                            break;
                        case 6:
                            characters.add("KING");characters.add("QUEEN");characters.add("POLICE");characters.add("CHOR");characters.add("MANTRI");characters.add("KNIGHT");
                            break;

                        case 7:
                            characters.add("KING");characters.add("QUEEN");characters.add("POLICE");characters.add("CHOR");characters.add("MANTRI");characters.add("KNIGHT");characters.add("SPY");
                            break;
                    }

                    Collections.shuffle(characters);
                    Iterator itr=characters.iterator();

                    for(int i=0;i<hostOptionsActivity.numberOfPlayers&&itr.hasNext();i++)
                    {
                        String tempString=(String)itr.next();
                        View v=mPlayersJoinedLV.getChildAt(i);
                        TextView temp=(TextView) v.findViewById(R.id.usernameTextView);

                        String name=temp.getText().toString();


                        mRootRoomsMyRoomPlayersReference.child(name).child("character").setValue(tempString);

                    }

                Intent i=new Intent(getApplicationContext(),gameActivity.class);
                i.putExtra("roomCode", hostOptionsActivity.randomRoomName);
                hostOptionsActivity.mRootRoomsMyRoomReference.child("removeThisPlayer").setValue("");
                hostOptionsActivity.mRootRoomsMyRoomReference.child("isGameStarted").setValue("1");
            startActivity(i);

                }

                else
                {
                    Toast.makeText(getApplicationContext(),"Players have not joined",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
