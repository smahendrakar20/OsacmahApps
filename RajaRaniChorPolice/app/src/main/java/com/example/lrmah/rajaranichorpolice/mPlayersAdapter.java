package com.example.lrmah.rajaranichorpolice;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;



public class mPlayersAdapter extends ArrayAdapter<playerClass> {

    public mPlayersAdapter(Context context, int resource, List<playerClass> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_player_joined, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);

        playerClass playerClassObj = getItem(position);


            nameTextView.setVisibility(View.VISIBLE);
            nameTextView.setText(playerClassObj.getname());
            usernameTextView.setText(playerClassObj.getUserName());

        return convertView;
    }
}
