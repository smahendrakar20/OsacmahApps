package com.osm00apps.lrmah.synonymclusterforgre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.client.AuthUiInitProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import data.DatabaseSchemas;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{


    int flag=0;
    public static final int RC_SIGN_IN=1;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAUthStateListener;
    String userName;

    Button searchComplete,searchMarked,searchLearned,searchSeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth=FirebaseAuth.getInstance();


        searchComplete=(Button) findViewById(R.id.searchCompleteList);
        searchMarked =(Button) findViewById(R.id.searchMarkedList);
                searchLearned= (Button) findViewById(R.id.searchLearnedList);
        searchSeen=(Button) findViewById(R.id.searchSeenList);


       searchComplete.setOnClickListener(this);
       searchSeen.setOnClickListener(this);
       searchMarked.setOnClickListener(this);
       searchLearned.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        Button sendMail=(Button) headerview.findViewById(R.id.gmailTextId);

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","osm00apps@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback for SYNONYMS CLUSTER FOR GRE App");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    //startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });

        mAUthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();

                if(user!=null&&flag==0){
                    userName=user.getDisplayName();
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myRelativeLayout),
                            "Welcome back "+ user.getDisplayName(), Snackbar.LENGTH_LONG);
                    mySnackbar.show();
                    flag=1;
                }
                else if(user==null){
                    userName="ANONYMOUS";
                    if(isOnline()){
                    startActivityForResult(
                            AuthUI.getInstance().createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                    }
                            else {
                        Intent i=new Intent(getApplicationContext(),ErrorDisplayActivity.class);
                        i.putExtra("displayMessage","Connect to Internet and Try Again");
                        startActivity(i);
                    }
                    }
                }

        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
   //     getMenuInflater().inflate(R.menu.main, menu);
   //     return true;
   // }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_signOut) {
            if(mAuth.getCurrentUser()!=null)
                AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navSearchedListId) {
            Intent i=new Intent(this,ListOfSearchedWordsActivity.class);
            startActivity(i);

        } else if (id == R.id.navCompleteListId) {
            Intent i=new Intent(this,CompleteListActivity.class);
             startActivity(i);
        } else if (id == R.id.navNotesListId) {
            Intent i=new Intent(this,ListOfNotesActivity.class);
             startActivity(i);
        } else if (id == R.id.navLearnedListId) {
            Intent i=new Intent(this,ListOfLearnedWordsActivity.class);
             startActivity(i);
        } else if (id == R.id.navMarkedListId) {
            Intent i=new Intent(this,ListOfMarkedWordsActivity.class);
             startActivity(i);
        }
        else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out Synonym Clusters for GRE app, for all your GRE vocabulary at one place, at:\n https://play.google.com/store/apps/details?id=com.osm00apps.lrmah.synonymclusterforgre");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId())
        {
            case R.id.searchLearnedList:
                i=new Intent(this,SearchActivity.class);
                i.putExtra("List", DatabaseSchemas.SearchedTableSchema.LEARNED);
                break;
            case R.id.searchMarkedList:
                 i=new Intent(this,SearchActivity.class);
                i.putExtra("List", DatabaseSchemas.SearchedTableSchema.Marked);
                break;
            case R.id.searchSeenList:
                i=new Intent(this,SearchActivity.class);
                i.putExtra("List","Searched");
                break;
            case R.id.searchCompleteList:
                 i=new Intent(this,SearchActivity.class);
                i.putExtra("List","Complete");
                break;
                default:
                    i=new Intent(this,SearchActivity.class);
                    i.putExtra("List","Complete");
                    break;
        }
    startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAUthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAUthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK)
            {
                FirebaseUser user=mAuth.getCurrentUser();
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.myRelativeLayout),
                        "Welcome "+ user.getDisplayName(), Snackbar.LENGTH_LONG);
                mySnackbar.show();
                Toast.makeText(this,"Signed in",Toast.LENGTH_LONG).show();
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Intent i=new Intent(getApplicationContext(),ErrorDisplayActivity.class);
                i.putExtra("displayMessage","Please Sign In to Continue!");
                startActivity(i);
                finish();
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public Intent createEmailIntent(final String toEmail,
                                    final String subject,
                                    final String message)
    {
        Intent sendTo = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(toEmail) +
                "?subject=" + Uri.encode(subject);
        Uri uri = Uri.parse(uriText);
        sendTo.setData(uri);

        List<ResolveInfo> resolveInfos =
                getPackageManager().queryIntentActivities(sendTo, 0);

        // Emulators may not like this check...
        if (!resolveInfos.isEmpty())
        {
            return sendTo;
        }

        // Nothing resolves send to, so fallback to send...
        Intent send = new Intent(Intent.ACTION_SEND);

        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_EMAIL,
                new String[] { toEmail });
        send.putExtra(Intent.EXTRA_SUBJECT, subject);

        return Intent.createChooser(send, "ABC");
    }
}
