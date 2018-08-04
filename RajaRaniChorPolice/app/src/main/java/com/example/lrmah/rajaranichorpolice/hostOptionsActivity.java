package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;

public class hostOptionsActivity extends Activity {

    public static int numberOfPlayers=2,numberOfGames=1;
    EditText numOfPlayersET,numOfGamesET,playerNickNameET,roomNickNameET;
    Button nextButton;

    //Store room name
    public static String randomRoomName="anonymous";
    public static String roomNickName;
    public static String playerNickName;

    static final String AB = "123456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mRootReference;
    public static DatabaseReference mRootRoomsReference;
    public static DatabaseReference mRootRoomsMyRoomReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_options);

        playerNickNameET=(EditText) findViewById(R.id.displayNameID);
        //roomNickNameET=(EditText) findViewById(R.id.roomNameID);
        numOfPlayersET =(EditText) findViewById(R.id.numberOfPlayersId);
       // numOfGamesET=(EditText) findViewById(R.id.numberOfGamesId);
        nextButton=(Button) findViewById(R.id.nextButtonId);

        //vvvvvvvvvvvvvvvvvv imptnt
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRootReference=mFirebaseDatabase.getReference();
        mRootRoomsReference=mRootReference.child("Rooms");


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    numberOfPlayers = Integer.parseInt(numOfPlayersET.getText().toString());
                    numberOfGames = Integer.parseInt(numOfGamesET.getText().toString());
                    playerNickName=playerNickNameET.getText().toString();
                    roomNickName=roomNickNameET.getText().toString();
                }
                catch(NumberFormatException e)
                {
                    Toast.makeText(getBaseContext(),"Number of games set 1",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){}

                if((numberOfPlayers>=2)&&(numberOfPlayers<=7))
                {
                    //generate a random name of length 6
                    randomRoomName=generateRandomString(5);
                    mRootRoomsMyRoomReference=mRootRoomsReference.child(randomRoomName);
                    roomCreate newroom=new roomCreate(null,roomNickName,numberOfPlayers,numberOfGames);
                 //vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv IMP
                    mRootRoomsMyRoomReference.setValue(newroom);


                    Intent i=new Intent(getApplicationContext(),hostShareCodeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getBaseContext(),"give proper value",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public static int getNumberOfGames()
    {
        return numberOfGames;
    }




    public String generateRandomString( int len )
    {
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


}
