package com.example.lrmah.rajaranichorpolice.Chat;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lrmah.rajaranichorpolice.MainActivity;
import com.example.lrmah.rajaranichorpolice.R;
import com.example.lrmah.rajaranichorpolice.playerClass;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.lrmah.rajaranichorpolice.playerClass;

import java.util.ArrayList;
import java.util.List;


public class chatFragmentActivity extends Fragment {

    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mMessagesRoomDatabaseReference;
    List<playerClass> playerClassList = new ArrayList<>();
    String roomCode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

                final View view = inflater.inflate(R.layout.chat_fragment_layout, container, false);
          initViews(view);
        initListView(view);



        return view;




        //accesing database


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getDatabaseReferences();
        textChangedMethod();
        childEventListenerMethod();
        onClickingSend();


    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getDatabaseReferences()
    {
        mFirebaseDatabase= FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        mMessagesRoomDatabaseReference=mMessagesDatabaseReference.child(getRoomCode());
    }

    public void textChangedMethod()
    {
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
    }
    public void childEventListenerMethod()
    {
        mChildEventListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                playerClass playerClassOBJ= dataSnapshot.getValue(playerClass.class);
                mMessageAdapter.add(playerClassOBJ);
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
        mMessagesRoomDatabaseReference.addChildEventListener(mChildEventListener);
    }
    public void initListView(final View view)
    {
        mMessageAdapter = new MessageAdapter(view.getContext(), R.layout.item_message, playerClassList);
        mMessageListView = (ListView) view.findViewById(R.id.messageListView);
        mMessageListView.setAdapter(mMessageAdapter);
    }

    public void onClickingSend()
    {
        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                playerClass playerClassOBJ = new playerClass(null, MainActivity.getUsername(),null,mMessageEditText.getText().toString(),null);
                //push the message to database in the node "messages"
                mMessagesRoomDatabaseReference.push().setValue(playerClassOBJ);
                // Clear input box
                mMessageEditText.setText("");
            }
        });

    }

    public String getRoomCode()
    {
        //passing value from activity
        if (getArguments()!=null) {
            roomCode =getArguments().getString("roomCode");
        }
    return roomCode;
    }


    public void  initViews(final View view)
    {

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }
}
