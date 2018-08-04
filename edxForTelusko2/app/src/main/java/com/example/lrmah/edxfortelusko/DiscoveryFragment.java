package com.example.lrmah.edxfortelusko;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public  class DiscoveryFragment extends Fragment implements recyclerAdapter.ListItemClickListener {


    private static final String ARG_SECTION_NUMBER = "section_number";
    android.support.v7.widget.RecyclerView rv;

    FirebaseDatabase database ;
    DatabaseReference myRef ;
    recyclerAdapter adapter;
    ArrayList<data> courses;
    View rootView;
    boolean isFragmentDisplayed=false;
    Button filterResultsBt;
    FrameLayout containerFrag;
    filterFragment  frag;

    public DiscoveryFragment() {
    }


    public static DiscoveryFragment newInstance(int sectionNumber) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("MainCourseList");

     courses=new ArrayList<>();
        createData();


         rootView = inflater.inflate(R.layout.fragment_main_2, container, false);
         rv= rootView.findViewById(R.id.rv1);
         filterResultsBt=rootView.findViewById(R.id.filterCoursesButtonId);
         containerFrag=(FrameLayout) rootView.findViewById(R.id.fragment_container);
         adapter=new recyclerAdapter(courses,getActivity(),this);
        rv.setAdapter(adapter);

        LinearLayoutManager LLM=new LinearLayoutManager(getActivity());
        LLM.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(LLM);

        //adding filter fragment
        frag=new filterFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();

        filterResultsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFragmentDisplayed)
                closeFragment();
                else
                    displayFragment();

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void createData()
    {

      myRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              for(DataSnapshot tempSnapShot: dataSnapshot.getChildren())
              {
                  data temp=tempSnapShot.getValue(data.class);
                  courses.add(temp);
              }
              adapter.notifyDataSetChanged();
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });

    }

    @Override
    public void onListItemClick(int clickItemIndex) {
        View view = rv.findViewHolderForAdapterPosition(clickItemIndex).itemView;
        TextView tempTv = view.findViewById(R.id.courseTitleId);
        String link= courses.get(clickItemIndex).getLink();
        String desc=courses.get(clickItemIndex).getDescription();

       // Toast.makeText(getActivity(), tempString, Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getActivity(),videoActivity.class);
        i.putExtra("Link",link);
        i.putExtra("Description",desc);
        startActivity(i);


    }

    public void displayFragment()
    {
        containerFrag.setVisibility(View.VISIBLE);
        isFragmentDisplayed=true;
    }

    public void closeFragment()
    {
        containerFrag.setVisibility(View.INVISIBLE);
        isFragmentDisplayed=false;
    }


}
