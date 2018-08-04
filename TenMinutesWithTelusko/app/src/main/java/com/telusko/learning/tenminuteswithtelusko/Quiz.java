package com.telusko.learning.tenminuteswithtelusko;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.telusko.learning.tenminuteswithtelusko.DataHelpers.QuizData;
import com.telusko.learning.tenminuteswithtelusko.DataHelpers.recyclerAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Quiz extends AppCompatActivity implements recyclerAdapter.ListItemClickListener {


     RecyclerView recyclerView;
     recyclerAdapter adap,resultAdapter;
    FirebaseDatabase database ;
    DatabaseReference myRef ;
    Button endQuizBt;
    LinkedHashMap<String, String> results;

    private ArrayList<QuizData> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        results= new LinkedHashMap<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Quiz");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
      //  recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        endQuizBt =(Button) findViewById(R.id.endQuizId);

        list = new ArrayList<>();

        createData();

        adap = new recyclerAdapter(list,results,getApplicationContext(), this);
        recyclerView.setAdapter(adap);


        endQuizBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countCorrect=0,countNotAnswered=0,countWrong=0;

                 ArrayList<QuizData> resultList;
                resultList=new ArrayList<>();

                 for(QuizData temp : list)
                 {
                     switch (results.get(temp.getQuestion()))
                     {
                         case "Correct":
                             countCorrect++;
                             temp.setDisplay("Green");
                             resultList.add(temp);
                             break;
                         case "Wrong": temp.setDisplay("Red");
                             resultList.add(temp);
                             break;

                         case "NotAnswered":
                             temp.setDisplay("No");
                             resultList.add(temp);
                             countNotAnswered++;
                         break;
                         default:
                             Toast.makeText(getApplicationContext(),"Number of correct = "+ Integer.toString(countCorrect) +" Number of unanswered = " + Integer.toString(countNotAnswered),Toast.LENGTH_LONG).show();
                     }
                 }

                resultAdapter=new recyclerAdapter(resultList,results,Quiz.this, Quiz.this);
                recyclerView.swapAdapter(resultAdapter,true);
                String btText="YOU SCORED - "+Integer.toString(countCorrect)+"/"+Integer.toString(list.size());
                endQuizBt.setBackgroundColor(getResources().getColor(R.color.green));
                endQuizBt.setText(btText);
                endQuizBt.setClickable(false);

            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        adap.notifyDataSetChanged();
    }


    public void createData()
    {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for(DataSnapshot tempSnapShot: dataSnapshot.getChildren())
            {
                QuizData temp=tempSnapShot.getValue(QuizData.class);
                try {
                    results.put(temp.getQuestion(), "NotAnswered");
                }
                catch (NullPointerException ex){ex.printStackTrace();}
                list.add(temp);
            }
            adap.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }



    @Override
    public void onListItemClick(int clickItemIndex, String optionSelected) {
        //View view = recyclerView.findViewHolderForAdapterPosition(clickItemIndex).itemView;
        if(list.get(clickItemIndex).getAnswer().equalsIgnoreCase(optionSelected))
        {
            results.replace(list.get(clickItemIndex).getQuestion(),"Correct");
            list.get(clickItemIndex).setUserOption(optionSelected);
        }
        else
        {
            results.replace(list.get(clickItemIndex).getQuestion(),"Wrong");
            list.get(clickItemIndex).setUserOption(optionSelected);
        }
    }
}
