package com.example.lrmah.hospitalmanagement.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.security.PublicKey;

public final class HospitalContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public static final String CONTENT_AUTHORITY1 = "com.example.lrmah.hospitalmanagement1";
    public static final String CONTENT_AUTHORITY2 = "com.example.lrmah.hospitalmanagement2";
    public static final Uri BASE_CONTENT_URI1 = Uri.parse("content://" + CONTENT_AUTHORITY1);
    public static final Uri BASE_CONTENT_URI2 = Uri.parse("content://" + CONTENT_AUTHORITY2);
    public static final String PATH_DOCTORS = "allDoctors";
    public static final String PATH_PATIENTS = "allPatients";

    private HospitalContract() {
    }

    public static final class doctorsEntry implements BaseColumns {
        //CONTENT_URI= "content://com.example.lrmah.hospitalmanagement1/allDoctors"
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI1, PATH_DOCTORS);


        public static final String table_name = "allDoctors";
        public static final String column_id = BaseColumns._ID;
        public static final String column_name = "DoctorName";
        public static final String column_field = "Field";
        public static final String column_available = "Available";
        public static final String column_patient = "Patients";
        public static final String column_username = "Username";
        public static final String column_password = "Password";
        public static final String CREATE_ENTRIES = ("CREATE TABLE " + doctorsEntry.table_name + "("
                + doctorsEntry.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + doctorsEntry.column_username + " TEXT NOT NULL DEFAULT 0, "
                + doctorsEntry.column_name + " TEXT NOT NULL DEFAULT 0, "
                + doctorsEntry.column_field + " TEXT NOT NULL DEFAULT 0, "
                + doctorsEntry.column_available + " INTEGER NOT NULL DEFAULT 0, "
                + doctorsEntry.column_patient + " TEXT NOT NULL DEFAULT 0, "
                + doctorsEntry.column_password + " TEXT NOT NULL DEFAULT 0 );"

        );
    }


    public static final class patientsEntry implements BaseColumns {
        //CONTENT_URI= "content://com.example.lrmah.hospitalmanagement2/allPatients"

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI2, PATH_PATIENTS);

        public static final String column_id = BaseColumns._ID;
        public static final String table_name = "allPatients";
        public static final String column_username = "Username";
        public static final String column_name = "NameOfPatient";
        public static final String column_in_out_patient = "TypeOfPatient";
        public static final String column_doctorWho = "AssignedDoctor";
        public static final String column_phoneNo = "PhoneNumber";
        public static final String column_password = "Password";
        public static final String column_prescription = "Prescription";
        public static final String column_problem = "Problem";

        public static final String CREATE_ENTRIES = ("CREATE TABLE " + patientsEntry.table_name + "("
                + patientsEntry.column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + patientsEntry.column_username + " TEXT NOT NULL DEFAULT 0, "
                + patientsEntry.column_name + " TEXT NOT NULL DEFAULT 0, "
                + patientsEntry.column_in_out_patient + " INTEGER NOT NULL DEFAULT 0, "
                + patientsEntry.column_doctorWho + " TEXT NOT NULL DEFAULT 0, "
                + patientsEntry.column_phoneNo + " INTEGER NOT NULL DEFAULT 0, "
                + patientsEntry.column_password + " TEXT NOT NULL DEFAULT 0, "
                + patientsEntry.column_problem + " TEXT NOT NULL DEFAULT 0, "
                + patientsEntry.column_prescription + " TEXT NOT NULL DEFAULT 0 );"
        );
    }
}
