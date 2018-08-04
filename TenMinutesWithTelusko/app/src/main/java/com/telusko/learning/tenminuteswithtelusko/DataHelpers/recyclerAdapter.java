package com.telusko.learning.tenminuteswithtelusko.DataHelpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.telusko.learning.tenminuteswithtelusko.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by sac on 21-03-2018.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {

    final private ListItemClickListener myOnClickListener;
    ArrayList<QuizData> list;
    Context context;
    LinkedHashMap<String,String> resultsHM;

   public recyclerAdapter(ArrayList<QuizData> list, LinkedHashMap<String,String> resultsHM,Context context, ListItemClickListener myOnClickListener)
    {
        this.myOnClickListener=myOnClickListener;
        this.list=list;
        this.context=context;
        this.resultsHM=resultsHM;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_cards, parent, false);
        myViewHolder holder=new myViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        QuizData currentItem = list.get(position);

        holder.tv1.setText(currentItem.getQuestion());
        holder.rb1.setText(currentItem.getOption1());
        holder.rb2.setText(currentItem.getOption2());
        holder.rb3.setText(currentItem.getOption3());
        holder.rb4.setText(currentItem.getOption4());

        holder.setIsRecyclable(false);
        if(currentItem.getDisplay()=="Red")
        {
            holder.tv1.setBackgroundResource(R.color.colorPrimary);
        }
        if(currentItem.getDisplay()=="Green")
        {
            holder.tv1.setBackgroundResource(R.color.green);
        }
        if(currentItem.getDisplay()=="NoColor")
        {
            holder.tv1.setBackgroundResource(R.color.transparent);
        }

        switch (currentItem.getUserOption()) {
               case "1":
                       holder.rb1.setChecked(true);
                   break;
               case "2":
                       holder.rb2.setChecked(true);
                   break;
               case "3":
                       holder.rb3.setChecked(true);
                   break;
               case "4":
                       holder.rb4.setChecked(true);
                   break;
               default:
                   holder.radioGroup.clearCheck();
           }



   }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder class this helps to initialise views

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public  TextView tv1;
        public RadioGroup radioGroup;
        public RadioButton rb1,rb2,rb3,rb4;

        public myViewHolder(View itemView) {
            super(itemView);

            tv1 = (TextView)itemView.findViewById(R.id.tv1);
            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroupId);
            rb1 = (RadioButton)itemView.findViewById(R.id.rb1);
            rb2 = (RadioButton)itemView.findViewById(R.id.rb2);
            rb3 = (RadioButton)itemView.findViewById(R.id.rb3);
            rb4 = (RadioButton)itemView.findViewById(R.id.rb4);

           // radioGroup.setOnCheckedChangeListener(this);
            rb1.setOnClickListener(this);
            rb2.setOnClickListener(this);
            rb3.setOnClickListener(this);
            rb4.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int clickedCardPosition = getAdapterPosition();

            if(rb1.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition, "1");
            if(rb2.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,"2");
            if(rb3.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,"3");
            if(rb4.isPressed()) myOnClickListener.onListItemClick(clickedCardPosition,"4");

        }

    }

    //Interface to register clicks
    public interface ListItemClickListener
    {
        void onListItemClick(int clickItemIndex,String optionSelected);
    }
}
