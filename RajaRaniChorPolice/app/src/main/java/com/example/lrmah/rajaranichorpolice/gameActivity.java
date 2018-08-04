package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrmah.rajaranichorpolice.Chat.chatFragmentActivity;
import com.example.lrmah.rajaranichorpolice.R;
import com.example.lrmah.rajaranichorpolice.data;
import com.example.lrmah.rajaranichorpolice.recyclerAdapter;
import com.example.lrmah.rajaranichorpolice.score.scoreAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static com.example.lrmah.rajaranichorpolice.R.id.CharTv;

public class gameActivity extends Activity implements recyclerAdapter.ListItemClickListener{
    int swapT=0;
    TextView tv1;
    ImageView swap;
    LinearLayoutManager LLM;
    ImageView chat;
    RecyclerView rv;
    recyclerAdapter adapter;
    int rvClickable=0;
    ImageView exclamationImage;

    //copied from old
    public static DatabaseReference mRootRoomsMyRoomReference,mRootReference2;
    FrameLayout container;
    int currentPriority,yourPriority,done;
    int numbPlayers;
    Button bt1,bt2,bt3,bt4,bt5,bt6,yourBt;
    TextView urTV,currentStatusTV;
    ArrayList<String> players;
    ArrayList<String> priorityOrderList;
    String character;
    String thisRoomCode;
    int KINGp,QUEENp,MANTRIp,KNIGHTp,SPYp,POLICEp,CHORp;
    ChildEventListener mChildEventListener;
    DatabaseReference mScoreRoomReference;
    List<playerClass> scoreArrayList = new ArrayList<>();
    scoreAdapter mscoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_game);

        swap=(ImageView) findViewById(R.id.bt20);
        //old

        priorityOrderList=new ArrayList<String>();
        players=new ArrayList<String>();
        currentStatusTV=(TextView) findViewById(R.id.tvWhoseTurn);
        urTV=(TextView) findViewById(CharTv);

        //to get room name from prev activity
        Bundle bundle1 = getIntent().getExtras();
        thisRoomCode = bundle1.getString("roomCode");

        //to send room name to fragment
        Bundle bundle=new Bundle();
        bundle.putString("roomCode",thisRoomCode);

        //setting up score listview
        mScoreRoomReference= FirebaseDatabase.getInstance().getReference().child("Scores").child(thisRoomCode);
        mscoreAdapter = new scoreAdapter(this, R.layout.item_message, scoreArrayList);
        childEventListenerMethod();

        mRootRoomsMyRoomReference=FirebaseDatabase.getInstance().getReference().child("Rooms").child(thisRoomCode);
        mRootReference2=FirebaseDatabase.getInstance().getReference().child("Rooms").child(thisRoomCode).child("players");


        onDataChangeMethod();

        mRootRoomsMyRoomReference.child("players").child(MainActivity.getUsername()).child("character").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                character=dataSnapshot.getValue(String.class);
                urTV.setText(character);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        container = (FrameLayout) findViewById(R.id.container);
        //set Fragment to activity
        chatFragmentActivity frag = new chatFragmentActivity();
        frag.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.container,frag).commit();

        //swap animation on click of button
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swapT==0)
                {//
                    currentStatusTV.animate().alpha(0).setDuration(2000);
                    container.animate().alpha(1).setDuration(2000);
                    currentStatusTV.setVisibility(View.INVISIBLE);
                    container.setVisibility(View.VISIBLE);
                    swapT=1;}
                else
                {
                    container.animate().alpha(0).setDuration(2000);
                    currentStatusTV.animate().alpha(1).setDuration(2000);
                    container.setVisibility(View.INVISIBLE);
                    currentStatusTV.setVisibility(View.VISIBLE);
                    swapT=0;
                }
            }
        });


        /*data1=new ArrayList<data>();
        data1.add(new data("sachin"));
        data1.add(new data("rishi"));
        data1.add(new data("sachidfasn"));
        data1.add(new data("sachimaha"));
        data1.add(new data("sachinyoyoyo"));*/

        rv=findViewById(R.id.recyclerView1);
        adapter=new recyclerAdapter(players,getApplication(),this);
        rv.setAdapter(adapter);
        LLM=new LinearLayoutManager(this);
        LLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(LLM);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rv.setItemAnimator(itemAnimator);
    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        if(rvClickable==1) {
            View view = rv.findViewHolderForAdapterPosition(clickItemIndex).itemView;
            TextView tempTv = view.findViewById(R.id.titleId);

            String tempString = tempTv.getText().toString();

            Toast.makeText(this, tempString, Toast.LENGTH_SHORT).show();
            answer(tempString, clickItemIndex);
        }
        else{
            Toast.makeText(this,"Wait for your turn!!",Toast.LENGTH_LONG).show();
        }
        }


    public void onDataChangeMethod()
    {
        mRootRoomsMyRoomReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(done!=1){
                    getPlayerNames(dataSnapshot);}

                int currentPriority=dataSnapshot.child("currentPriority").getValue(Integer.class);
                int numberOfPlayers=dataSnapshot.child("numberOfPlayers").getValue(Integer.class);
                String yourCharacter=dataSnapshot.child("players").child(MainActivity.getUsername()).child("character").getValue(String.class);
                urTV.setText(yourCharacter);
                currentStatusTV.setText(dataSnapshot.child("status").getValue(String.class));
                yourPriority=getPriority(yourCharacter);


                //if your priority equals current priority
                if((currentPriority==numberOfPlayers)&&(yourPriority==numberOfPlayers))
                {
                    String temp=MainActivity.getUsername() +" is CHOR!LOL";
                    mRootRoomsMyRoomReference.child("status").setValue(temp);
                }
                else if(yourPriority==currentPriority&&currentPriority!=numberOfPlayers)
                {

                    rvClickable=1;
                    String temp=MainActivity.getUsername()+" is "+priorityOrderList.get(yourPriority-1)+" and has to find " + priorityOrderList.get(yourPriority);
                    mRootRoomsMyRoomReference.child("status").setValue(temp);
                }

                String removePlayerName=dataSnapshot.child("removeThisPlayer").getValue(String.class);
                //remove the player after the turn from recycler view
                if(removePlayerName!=null)
                {
                    players.remove(removePlayerName);
                adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //add number of characters to character priority list based on number of players
    public void giveCharacterPriorities()
    {
        switch(numbPlayers)
        {
            case 2:
                KINGp=1;
                QUEENp=2;
                priorityOrderList.add("KING");
                priorityOrderList.add("QUEEN");

                break;


            case 3:
                KINGp=1;
                QUEENp=2;
                CHORp=3;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("CHOR");
                break;
            case 4:
                KINGp=1;
                QUEENp=2;
                POLICEp=3;
                CHORp=4;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("POLICE");priorityOrderList.add("CHOR");
                break;
            case 5:
                KINGp=1;
                QUEENp=2;
                POLICEp=4;
                MANTRIp=3;
                CHORp=5;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("MANTRI");priorityOrderList.add("POLICE");priorityOrderList.add("CHOR");
                break;
            case 6:
                KINGp=1;
                QUEENp=2;
                MANTRIp=3;
                KNIGHTp=4;
                POLICEp=5;
                CHORp=6;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("MANTRI");priorityOrderList.add("KNIGHT");priorityOrderList.add("POLICE");priorityOrderList.add("CHOR");
                break;
            case 7:
                KINGp=1;
                QUEENp=2;
                MANTRIp=3;
                KNIGHTp=4;
                SPYp=5;
                POLICEp=6;
                CHORp=7;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("MANTRI");priorityOrderList.add("KNIGHT");priorityOrderList.add("SPY");priorityOrderList.add("POLICE");priorityOrderList.add("CHOR");
                break;
            default:
                KINGp=1;
                QUEENp=2;
                MANTRIp=3;
                KNIGHTp=4;
                SPYp=5;
                POLICEp=6;
                CHORp=7;
                priorityOrderList.add("KING");priorityOrderList.add("QUEEN");priorityOrderList.add("MANTRI");priorityOrderList.add("KNIGHT");priorityOrderList.add("SPY");priorityOrderList.add("POLICE");priorityOrderList.add("CHOR");
                break;

        }

    }

    public int getPriority(String ch)
    {
        switch(ch)
        {
            case "KING":
                return(KINGp);
            case "QUEEN":
                return QUEENp;

            case "MANTRI":
                return MANTRIp;

            case "KNIGHT":
                return KNIGHTp;

            case "SPY":
                return SPYp;

            case "POLICE":
                return POLICEp;

            case "CHOR":
                return CHORp;

            default: return 0;
        }
    }

  /*  @Override
    public void onClick(View view){
        switch(view.getId())
        {
            case R.id.button1:
                answer(bt1.getText().toString());
                break;
            case R.id.button2:
                answer(bt2.getText().toString());
                break;
            case R.id.button3:
                answer(bt3.getText().toString());
                break;
            case R.id.button4:
                answer(bt4.getText().toString());
                break;
            case R.id.button5:
                answer(bt5.getText().toString());
                break;
            case R.id.button6:
                answer(bt6.getText().toString());
                break;
        }
    }*/


    public void answer(final String username, final int positionInRv)
    {

        mRootReference2.child(username).child("character").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usernameCharacter=dataSnapshot.getValue(String.class);
                int priorityOfClicked=getPriority(usernameCharacter);
                if(yourPriority+1==priorityOfClicked)
                {
                    int score=getScore(yourPriority);
                    playerClass tempScoreObj=new playerClass(MainActivity.getUsername(),Integer.toString(score));
//here go to score activity.
                    mScoreRoomReference.push().setValue(tempScoreObj);
                    mRootRoomsMyRoomReference.child("currentPriority").setValue(priorityOfClicked);
                    mRootRoomsMyRoomReference.child("removeThisPlayer").setValue(MainActivity.getUsername());
                    players.remove(username);
                    adapter.notifyItemRemoved(positionInRv);

                }
                else {
                    mRootReference2.child(MainActivity.getUsername()).child("character").setValue(usernameCharacter);
                    mRootReference2.child(username).child("character").setValue(urTV.getText().toString());
                }
              rvClickable=0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void getPlayerNames(DataSnapshot dataSnapshot)
    {
        try {
            currentPriority=dataSnapshot.child("currentPriority").getValue(Integer.class);
            numbPlayers = dataSnapshot.child("numberOfPlayers").getValue(Integer.class);
            giveCharacterPriorities();
        }catch(NullPointerException e){
            Log.e("nps","sdaf");
        }
        //getting players names in arraylist "players"
        for(DataSnapshot iterator: dataSnapshot.child("players").getChildren()){
            playerClass temp=iterator.getValue(playerClass.class);
            players.add(temp.getUserName());
        }
        players.remove(MainActivity.getUsername());
        done=1;
    }


    public int getScore(int priority)
    {
        switch (numbPlayers)
        {
            case 2:
                switch (priority)
                {
                    case 1:
                        return(20);
                    case 2:return(5);
                    default: return 0;
                }
            case 3:
                switch (priority)
                {
                    case 1:return(30);
                    case 2:return(15);
                    case 3:return(0);
                    default: return 0;
                }
            case 4:
                switch (priority)
                {
                    case 1:return(40);
                    case 2:return(25);
                    case 3:return(15);
                    case 4:return(0);
                    default: return 0;
                }
            case 5:
                switch (priority)
                {
                    case 1:return(50);
                    case 2:return(30);
                    case 3:return(15);
                    case 4:return(5);
                    case 5:return(0);
                    default: return 0;
                }
            case 6:
                switch (priority)
                {
                    case 1:return(60);
                    case 2:return(45);
                    case 3:return(30);
                    case 4:return(20);
                    case 5:return(10);
                    case 6:return(0);
                    default: return 0;
                }
            case 7:
                switch (priority)
                {
                    case 1:return(70);
                    case 2:return(50);
                    case 3:return(40);
                    case 4:return(25);
                    case 5:return(15);
                    case 6:return(10);
                    case 7:return(0);
                    default: return 0;
                }
            default: return 0;
        }
    }
    public void childEventListenerMethod() {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                playerClass playerClassOBJ = dataSnapshot.getValue(playerClass.class);
                mscoreAdapter.add(playerClassOBJ);
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
        mScoreRoomReference.addChildEventListener(mChildEventListener);
    }

}
