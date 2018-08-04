package com.example.lrmah.rajaranichorpolice;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sac on 21-03-2018.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {

    final private ListItemClickListener myOnClickListener;
    ArrayList<String> list;
    Context context;

    recyclerAdapter(ArrayList<String> list, Context context, ListItemClickListener myOnClickListener)
    {
        this.myOnClickListener=myOnClickListener;
        this.list=list;
        this.context=context;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        myViewHolder holder=new myViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        holder.tv1.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //viewholder class this helps to initialise views

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView tv1;
        public myViewHolder(View itemView) {
            super(itemView);
            cv =(CardView) itemView.findViewById(R.id.cardView20);
            tv1=(TextView) itemView.findViewById(R.id.titleId);
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
