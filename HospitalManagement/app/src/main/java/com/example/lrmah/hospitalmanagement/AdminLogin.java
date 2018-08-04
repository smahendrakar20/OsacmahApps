
package com.example.lrmah.hospitalmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static android.R.attr.password;


public class AdminLogin extends AppCompatActivity {

    static String pass;
    String temp;
    EditText adminUserName;
    EditText password;
    Button login;

    public static String getAdminPassword() {
        return pass;
    }

    public static void setAdminPassword(String s) {
        pass=s;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUserName = (EditText) findViewById(R.id.adminUserNameTV);
        password = (EditText) findViewById(R.id.passwordFieldOfAdmin);

        login = (Button) findViewById(R.id.adminPageLoginButton);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                temp = adminUserName.getText().toString();
                pass = password.getText().toString();
                if ((temp.equals("q") && pass.equals("q"))) {
                    Intent i = new Intent(AdminLogin.this, adminPage.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "You are now logged in :-)", Toast.LENGTH_LONG).show();
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "  :-(", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}