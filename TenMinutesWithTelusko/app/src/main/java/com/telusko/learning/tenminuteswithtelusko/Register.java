package com.telusko.learning.tenminuteswithtelusko;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    private TextView b1;
    TextInputLayout et1,et2,et3;

    //firebase auth.
  //  private FirebaseAuth mAuth;



    private ProgressDialog prog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b1 = (TextView) findViewById(R.id.b1);
        et1 = (TextInputLayout) findViewById(R.id.et1);
        et2 = (TextInputLayout) findViewById(R.id.et2);
        et3 = (TextInputLayout) findViewById(R.id.et3);


      //  mAuth = FirebaseAuth.getInstance();


        prog = new ProgressDialog(this);






        b1.setOnClickListener(v->{


            String uname = et1.getEditText().getText().toString();
            String email = et2.getEditText().getText().toString();
            String pass = et3.getEditText().getText().toString();


            if(!TextUtils.isEmpty(uname) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)) {


                prog.setTitle("Registering User");
                prog.setMessage("Please wait while we create your account !");
                prog.setCanceledOnTouchOutside(false);
                prog.show();

             //   register(uname, email, pass);

            }




        });

    }

/*
    public void register(String name, String email, String pass){

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            prog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent i = new Intent(Register.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();


                        } else {
                            // If sign in fails, display a message to the user.
                            prog.hide();

                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    } */

}
