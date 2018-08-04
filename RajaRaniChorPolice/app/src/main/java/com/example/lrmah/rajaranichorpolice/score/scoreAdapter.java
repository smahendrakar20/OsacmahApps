package com.example.lrmah.rajaranichorpolice.score;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lrmah.rajaranichorpolice.R;
import com.example.lrmah.rajaranichorpolice.playerClass;

import java.lang.reflect.Array;
import java.util.List;

import static com.example.lrmah.rajaranichorpolice.R.id.messageTextView;
import static com.example.lrmah.rajaranichorpolice.R.id.nameTV;
import static com.example.lrmah.rajaranichorpolice.R.id.nameTextView;



public class scoreAdapter extends ArrayAdapter<playerClass> {
    public scoreAdapter(Context context, int resource, List<playerClass> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_score, parent, false);
        }

        TextView nameTextView= (TextView) convertView.findViewById(nameTV);
        TextView scoreTextView = (TextView) convertView.findViewById(R.id.scoreTV);

        playerClass score = getItem(position);


            nameTextView.setVisibility(View.VISIBLE);
            scoreTextView.setVisibility(View.VISIBLE);


            scoreTextView.setText(score.getScore());
        nameTextView.setText(score.getUserName());

        return convertView;
    }

}
