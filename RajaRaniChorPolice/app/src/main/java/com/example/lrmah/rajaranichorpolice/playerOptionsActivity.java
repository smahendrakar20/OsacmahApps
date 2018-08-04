package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Intent;
import android.sax.TextElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class playerOptionsActivity extends Activity implements TextWatcher {

    String temp;

    public static String roomCode;
    Button next;
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference dr;
    TextWatcher generalTextWatcher;
    int prevLength=0;
    boolean textChanged;
    TextView et1, et2, et3, et4, et5,etCommon;
    StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_options);
        roomCode = "00000";

        mFireBaseDatabase = FirebaseDatabase.getInstance();
        sb=new StringBuilder("");

        next = (Button) findViewById(R.id.nextButtonId);
        next.setEnabled(false);
        et1 = (TextView) findViewById(R.id.editText1);
        et2 = (TextView) findViewById(R.id.editText2);
        et3 = (TextView) findViewById(R.id.editText3);
        et4 = (TextView) findViewById(R.id.editText4);
        et5 = (TextView) findViewById(R.id.editText5);
        etCommon = (EditText) findViewById(R.id.editTextCommon);

        etCommon.addTextChangedListener(this);

        et1.setText("");
        et2.setText("");
        et3.setText("");
        et4.setText("");
        et5.setText("");

        next.getBackground().setAlpha(128);
        next.setFocusable(false);


        //***************************************************************************************

//******************************************************************************


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                sb.append(String.valueOf(temp.charAt(0)));
                sb.append(String.valueOf(temp.charAt(1)));
                sb.append(String.valueOf(temp.charAt(2)));
                sb.append(String.valueOf(temp.charAt(3)));
                sb.append(String.valueOf(temp.charAt(4)));

                    roomCode=sb.toString();
                    dr = mFireBaseDatabase.getReference().child("Rooms");
                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if (snapshot.hasChild(roomCode)) {

                                long count = snapshot.child(roomCode).child("players").getChildrenCount();
                                long numbOfplayers = snapshot.child(roomCode).child("numberOfPlayers").getValue(Long.class);
                                if (count < numbOfplayers) {
                                    Intent i = new Intent(getApplicationContext(), playerWaitingForGameActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "number of players exceeded in room", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter valid code", Toast.LENGTH_LONG).show();
                                roomCode="";
                                sb=null;
                                temp=null;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError de) {

                        }
                    });

            }
        });

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        textChanged = prevLength > s.length();
        if (textChanged) {
            switch (prevLength) {
                case 1:
                    et1.setText("");
                   //sb.deleteCharAt(0);
                   break;

                case 2:
                    et2.setText("");
                    //sb.deleteCharAt(1);
                    break;
                case 3:
                    et3.setText("");
                   // sb.deleteCharAt(2);
                    break;
                case 4:
                    et4.setText("");
                 //   sb.deleteCharAt(3);
                    break;
                case 5:
                    et5.setText("");
                //    sb.deleteCharAt(4);
                    next.getBackground().setAlpha(
                            128);
                    next.setFocusable(false);
                    next.setEnabled(false);
                    break;

            }
        }
        else
            {
           temp = s.toString();

            switch (temp.length())
            {
                case 1:
                    et1.setText(String.valueOf(temp.charAt(0)));

                    break;
                case 2:
                    et2.setText(String.valueOf(temp.charAt(1)));
                    break;
                case 3:
                    et3.setText(String.valueOf(temp.charAt(2)));

                    break;
                case 4:
                    et4.setText(String.valueOf(temp.charAt(3)));
                    break;
                case 5:
                    next.setFocusable(true);
                    next.getBackground().setAlpha(255);
                    next.setEnabled(true);
                    et5.setText(String.valueOf(temp.charAt(4)));


                    Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
                    break;

            }
        }}

        @Override
        public void beforeTextChanged (CharSequence s,int start, int count,
        int after){
            prevLength = s.length();

        }

        @Override
        public void afterTextChanged (Editable editable){


        }


    }



