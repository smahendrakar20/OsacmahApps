package com.example.lrmah.edxfortelusko;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class filterFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


   // private OnFragmentInteractionListener mListener;

    public filterFragment() {
        // Required empty public constructor
    }

    public static filterFragment newInstance() {
        filterFragment fragment = new filterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

   // public void onButtonPressed(Uri uri) {
     //   if (mListener != null) {
       //     mListener.onFragmentInteraction(uri);
        //}
    //}

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

  //  @Override
   ////// public void onDetach() {
     //   super.onDetach();
  //      mListener = null;
//    }


    //public interface OnFragmentInteractionListener {
      //  void onFragmentInteraction(Uri uri);
    //}
}
