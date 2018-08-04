package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.support.v4.app.FragmentContainer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lrmah.rajaranichorpolice.Chat.MessageAdapter;
import com.example.lrmah.rajaranichorpolice.Chat.chatFragmentActivity;
import com.example.lrmah.rajaranichorpolice.score.scoreAdapter;
import com.firebase.ui.auth.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.lrmah.rajaranichorpolice.R.id.CharTv;
import static com.example.lrmah.rajaranichorpolice.R.id.currentPlayerId;
import static com.example.lrmah.rajaranichorpolice.hostOptionsActivity.mRootReference;
import static com.example.lrmah.rajaranichorpolice.hostOptionsActivity.mRootRoomsMyRoomReference;
import static com.example.lrmah.rajaranichorpolice.hostOptionsActivity.numberOfPlayers;
//DONT USE THIS NOT USEFUL
public class mainGameActivity extends Activity implements View.OnClickListener{


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
    ListView scoreLV;
    ChildEventListener mChildEventListener;
    DatabaseReference mScoreRoomReference;
    List<playerClass> scoreArrayList = new ArrayList<>();
    scoreAdapter mscoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        priorityOrderList=new ArrayList<String>();
        players=new ArrayList<String>();
        bt1=(Button)findViewById(R.id.button1);
        bt2= (Button)findViewById(R.id.button2);
        bt3=(Button)findViewById(R.id.button3);
        bt4=(Button)findViewById(R.id.button4);
        bt5=(Button)findViewById(R.id.button5);
        bt6=(Button)findViewById(R.id.button6);
        yourBt=(Button)findViewById(R.id.currentPlayerId);
        currentStatusTV=(TextView) findViewById(R.id.tvWhoseTurn);
        scoreLV=(ListView) findViewById(R.id.scoreListView);//not copying

        urTV=(TextView) findViewById(CharTv);
        yourBt.setText(MainActivity.getUsername());//not copying




        {
           makeButtonsUnClickable();
            yourBt.setEnabled(false);
            bt1.setOnClickListener(this);
            bt2.setOnClickListener(this);
            bt3.setOnClickListener(this);
            bt4.setOnClickListener(this);
            bt5.setOnClickListener(this);
            bt6.setOnClickListener(this);


        }

        //this is to send roomCode to fragment
        Bundle bundle1 = getIntent().getExtras();
        thisRoomCode = bundle1.getString("roomCode");

        Bundle bundle=new Bundle();
        bundle.putString("roomCode",thisRoomCode);

        mScoreRoomReference=FirebaseDatabase.getInstance().getReference().child("Scores").child(thisRoomCode);
        mscoreAdapter = new scoreAdapter(this,R.layout.item_message, scoreArrayList);
        scoreLV.setAdapter(mscoreAdapter);
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


    }


    public void setButtons(int numbPlayers)
    {
        players.remove(MainActivity.getUsername());
        switch(numbPlayers)
        {
            case 2:
                //bt2.s
               // bt3.setText(players.get(1));
                bt4.setText(players.get(0));
                bt5.setVisibility(View.INVISIBLE);
                bt6.setVisibility(View.INVISIBLE);
                bt1.setVisibility(View.INVISIBLE);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.INVISIBLE);

                break;


            case 3:

                bt3.setText(players.get(0));
                bt4.setText(players.get(1));

               bt2.setVisibility(View.INVISIBLE);
                bt5.setVisibility(View.INVISIBLE);
                bt6.setVisibility(View.INVISIBLE);
                bt1.setVisibility(View.INVISIBLE);
                break;
            case 4:
                bt2.setText(players.get(0));
                bt3.setText(players.get(1));
                bt4.setText(players.get(2));
                bt5.setVisibility(View.INVISIBLE);
                bt6.setVisibility(View.INVISIBLE);
                bt1.setVisibility(View.INVISIBLE);

                break;

            case 5:
                bt2.setText(players.get(0));
                bt3.setText(players.get(1));
                bt4.setText(players.get(2));
                bt5.setText(players.get(3));
                bt6.setVisibility(View.INVISIBLE);
                bt1.setVisibility(View.INVISIBLE);

                break;


            case 6:
                bt1.setText(players.get(0));
                bt2.setText(players.get(1));
                bt3.setText(players.get(2));
                bt4.setText(players.get(3));
                bt5.setText(players.get(4));
                bt6.setVisibility(View.INVISIBLE);

                break;



            case 7:

                bt1.setText(players.get(0));
                bt2.setText(players.get(1));
                bt3.setText(players.get(2));
                bt4.setText(players.get(3));
                bt5.setText(players.get(4));
                bt6.setText(players.get(5));
break;

        }

    }

    public void onDataChangeMethod()
    {
        mRootRoomsMyRoomReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(done!=1){
                    initButtonNames(dataSnapshot);}

                int currentPriority=dataSnapshot.child("currentPriority").getValue(Integer.class);
                int numberOfPlayers=dataSnapshot.child("numberOfPlayers").getValue(Integer.class);
                String yourCharacter=dataSnapshot.child("players").child(MainActivity.getUsername()).child("character").getValue(String.class);
                urTV.setText(yourCharacter);
                currentStatusTV.setText(dataSnapshot.child("status").getValue(String.class));
                yourPriority=getPriority(yourCharacter);


               //if your priority equals current priority
                if((currentPriority==numberOfPlayers)&&(yourPriority==numberOfPlayers))
                {
                    String temp=MainActivity.getUsername() +"is CHOR!LOL";
                    mRootRoomsMyRoomReference.child("status").setValue(temp);
                }
                else if(yourPriority==currentPriority&&currentPriority!=numberOfPlayers)
                {
                    makeButtonsClickable();
                    String temp=MainActivity.getUsername()+" is "+priorityOrderList.get(yourPriority-1)+" and has to find " + priorityOrderList.get(yourPriority);
                    mRootRoomsMyRoomReference.child("status").setValue(temp);
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

    public void makeButtonsClickable()
    {
        switch (numbPlayers)
        {
            case 2:
                //bt2.s
                // bt3.setText(players.get(1));
                bt4.setEnabled(true);

                break;


            case 3:

                bt3.setEnabled(true);
                bt4.setEnabled(true);


            case 4:
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);

                break;

            case 5:
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(true);

                break;


            case 6:

                bt1.setEnabled(true);
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(true);
                break;



            case 7:
                bt1.setEnabled(true);
                bt2.setEnabled(true);
                bt3.setEnabled(true);
                bt4.setEnabled(true);
                bt5.setEnabled(true);
                bt6.setEnabled(true);


        }
    }

    @Override
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
    }


    public void answer(final String username)
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

                    mScoreRoomReference.push().setValue(tempScoreObj);
                    mRootRoomsMyRoomReference.child("currentPriority").setValue(priorityOfClicked);

                }
                else {
                    mRootReference2.child(MainActivity.getUsername()).child("character").setValue(usernameCharacter);
                    mRootReference2.child(username).child("character").setValue(urTV.getText().toString());
                }
                makeButtonsUnClickable();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

public void makeButtonsUnClickable()
{
    bt1.setEnabled(false);
    bt2.setEnabled(false);
    bt3.setEnabled(false);
    bt4.setEnabled(false);
    bt5.setEnabled(false);
    bt6.setEnabled(false);
}

public void initButtonNames(DataSnapshot dataSnapshot)
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
    setButtons(numbPlayers);
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

