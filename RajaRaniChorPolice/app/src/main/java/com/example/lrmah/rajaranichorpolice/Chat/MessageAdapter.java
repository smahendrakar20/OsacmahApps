package com.example.lrmah.rajaranichorpolice.Chat;

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

import java.util.List;

public class MessageAdapter extends ArrayAdapter<playerClass> {

    public MessageAdapter(Context context, int resource, List<playerClass> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);

        playerClass message = getItem(position);

            messageTextView.setVisibility(View.VISIBLE);

            messageTextView.setText(message.getMessage());

        authorTextView.setText(message.getUserName());

convertView.animate();
        return convertView;
    }
}
