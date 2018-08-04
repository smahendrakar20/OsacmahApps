package com.telusko.learning.tenminuteswithtelusko;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyCwVtRuAaf1OhJlbq2vFPT6pHiAo1gKd7o";

    //https://www.youtube.com/watch?v=<VIDEO_ID>
    public static String VIDEO_ID = "";
    String description;
    TextView courseDescriptionTextView;
    Button continueToQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent i=getIntent();
       VIDEO_ID=i.getStringExtra("Link");
        description=i.getStringExtra("Description");
        continueToQuiz=(Button) findViewById(R.id.continueToQuizTv);
        courseDescriptionTextView=(TextView) findViewById(R.id.courseDescriptionId);
        courseDescriptionTextView.setText(description);
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_id);
        youTubePlayerView.initialize(API_KEY, this);

        continueToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Quiz.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(null== youTubePlayer) return;

        // Start buffering
        if (!b) {
            youTubePlayer.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failed to initialize.", Toast.LENGTH_LONG).show();

    }
}

