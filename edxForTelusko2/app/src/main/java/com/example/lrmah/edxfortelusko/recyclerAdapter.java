package com.example.lrmah.edxfortelusko;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sac on 21-03-2018.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {

    final private ListItemClickListener myOnClickListener;
    ArrayList<data> list;
    Context context;

    recyclerAdapter(ArrayList<data> list, Context context, ListItemClickListener myOnClickListener)
    {
        this.myOnClickListener=myOnClickListener;
        this.list=list;
        this.context=context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_cards_format, parent, false);
        myViewHolder holder=new myViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.company.setText(list.get(position).getCompany());
        holder.coursetitle.setText(list.get(position).getName());
        holder.descriptionTv.setText(list.get(position).getDescription());
        holder.durationTv.setText(list.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder class this helps to initialise views

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView company,coursetitle,descriptionTv,durationTv;
        public myViewHolder(View itemView) {
            super(itemView);
            cv=(CardView) itemView.findViewById(R.id.cardView20);
            company =(TextView) itemView.findViewById(R.id.companyTitleId);
            coursetitle=(TextView) itemView.findViewById(R.id.courseTitleId);
            descriptionTv=(TextView) itemView.findViewById(R.id.descriptionId);
            durationTv=(TextView) itemView.findViewById(R.id.durationId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition=getAdapterPosition();
            myOnClickListener.onListItemClick(clickedPosition);

        }
    }

    //Interface to register clicks
    public interface ListItemClickListener
    {
        void onListItemClick(int clickItemIndex);
    }
}
