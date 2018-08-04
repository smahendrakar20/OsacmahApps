package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class playerWaitingForGameActivity extends Activity {


    private ListView mPlayersJoinedLV;
    private TextView roomNickNameTV;
    private mPlayersAdapter playerAdapter;
    public static DatabaseReference mRootRoomsMyRoomReference;
    public static DatabaseReference mRootRoomsMyRoomPlayersReference;
    public static DatabaseReference mRootRoomsMyRoomPlayersMySelfReference;
    private Button startGame;
    ValueEventListener playersAddedListener;
    ChildEventListener mChildEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_waiting_for_game);


startGame=(Button) findViewById(R.id.startGameButtonId);

        //new references
        mRootRoomsMyRoomReference= FirebaseDatabase.getInstance().getReference()
                .child("Rooms")
                .child(playerOptionsActivity.roomCode);
        mRootRoomsMyRoomPlayersReference= FirebaseDatabase.getInstance().getReference()
                .child("Rooms")
                .child(playerOptionsActivity.roomCode)
                .child("players");
        mRootRoomsMyRoomPlayersMySelfReference=mRootRoomsMyRoomPlayersReference.child(MainActivity.getUsername());

        //Adding host to player list
        playerClass newPlayer=new playerClass("",MainActivity.getUsername(),null,null,null);
        mRootRoomsMyRoomPlayersMySelfReference.setValue(newPlayer);


        mPlayersJoinedLV = (ListView) findViewById(R.id.shareCodeListViewId);


        //initialization of the list
        List<playerClass> friendlyMessages = new ArrayList<>();
        playerAdapter = new mPlayersAdapter(this, R.layout.item_player_joined, friendlyMessages);
        mPlayersJoinedLV.setAdapter(playerAdapter);


        mChildEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                playerClass playerObj= dataSnapshot.getValue(playerClass.class);
                playerAdapter.add(playerObj);
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


        mRootRoomsMyRoomReference.child("isGameStarted").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               String temp=dataSnapshot.getValue(String.class);
                try{
                if(temp.equals("1"))
                {
                    Intent i=new Intent(getApplicationContext(),gameActivity.class);
                    i.putExtra("roomCode", playerOptionsActivity.roomCode);
                   // TimeUnit.SECONDS.sleep(2);
                    startActivity(i);
                }}catch(NullPointerException e){
                    Log.e("null pointer","equals is wrong");}
                catch( Exception e){}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

}
    public String getMyData() {
        return playerOptionsActivity.roomCode;
    }
}
