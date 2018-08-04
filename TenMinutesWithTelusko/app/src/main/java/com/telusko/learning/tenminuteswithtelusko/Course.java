package com.telusko.learning.tenminuteswithtelusko;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Locale;

public class Course extends AppCompatActivity {


    private static final long TIME_IN_MILLIS = 10000;

    private TextView tv1,tv2,tv3;
    private CountDownTimer mCount;
    private boolean timeRunning;
    private long timeLeftInMilis = TIME_IN_MILLIS;

    private WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);


        tv3.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        tv1.setVisibility(View.INVISIBLE);


        webView1 = (WebView)findViewById(R.id.webView1);

        webView1.setWebViewClient(new WebViewClient());



        WebSettings webset = webView1.getSettings();
        webset.setJavaScriptEnabled(true);


        tv3.setOnClickListener(v->{

            tv1.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.INVISIBLE);
            webView1.loadUrl("http://www.telusko.com/");



            startTimer();


        });


        tv2.setOnClickListener(v->{

            Intent i = new Intent(Course.this, Quiz.class);
            startActivity(i);

        });


    }

    private void startTimer() {

        mCount = new CountDownTimer(timeLeftInMilis, 1000) {
            @Override
            public void onTick(long l) {

                timeLeftInMilis=l;
                updateTimer();

            }

            @Override
            public void onFinish() {

                timeRunning = false;
                tv1.setVisibility(View.INVISIBLE);
                tv2.setVisibility(View.VISIBLE);


            }
        }.start();

        timeRunning = true;


    }

    private void updateTimer() {

        int minutes = (int) (timeLeftInMilis/1000)/60;
        int seconds = (int) (timeLeftInMilis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        tv1.setText(timeLeftFormatted);
    }

    @Override
    public void onBackPressed() {
        if(webView1.canGoBack()){
            webView1.goBack();
        }
        else {
            super.onBackPressed();
        }

    }

}
