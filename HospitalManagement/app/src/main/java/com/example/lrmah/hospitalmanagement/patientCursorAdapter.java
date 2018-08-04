package com.example.lrmah.hospitalmanagement;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.lrmah.hospitalmanagement.Data.HospitalContract;


public class patientCursorAdapter extends CursorAdapter {
    public patientCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1= (TextView) view.findViewById(R.id.name);
        TextView tv2 = (TextView) view.findViewById(R.id.summary);

        int patientName = cursor.getColumnIndex(HospitalContract.patientsEntry.column_name);
        int patientType = cursor.getColumnIndex(HospitalContract.patientsEntry.column_in_out_patient);

        String name = cursor.getString(patientName);
        String type = cursor.getString(patientType);

        tv1.setText(name);
        tv2.setText(type);
    }

}
