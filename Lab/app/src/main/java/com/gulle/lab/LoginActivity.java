package com.gulle.lab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser, etPass;
    Button login;
    String username, password;
    int formsuccess, userid;

    DbHelper db;
    SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DbHelper(this);
        login = (Button) findViewById(R.id.btnLogin);
        TextView reg = (TextView) findViewById(R.id.btnRegister);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        shared = getSharedPreferences("gulle" , Context.MODE_PRIVATE);

        reg.setFocusable(false);
        reg.setClickable(true);
        reg.setInputType(InputType.TYPE_NULL);
        login.setOnClickListener(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        if (shared.contains(db.TBL_USER_ID)){
            this.finish();
            startActivity(new Intent(this, HomeActivity.class));
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.btnLogin:
                username = etUser.getText().toString();
                password = etPass.getText().toString();

                formsuccess = 2;

                // validate username
                if (username.equals("")) {
                    etUser.setError("This field is required");
                    formsuccess--;
                }

                // validate password
                if (password.equals("")) {
                    etPass.setError("This field is required");
                    formsuccess--;
                }

                if (formsuccess == 2) {
                    userid = db.checkUser(password, username);


                    if (userid >= 1) {
                        SharedPreferences.Editor editor = shared.edit();
                        editor.putInt(db.TBL_USER_ID, userid).commit();
                        this.finish();
                        startActivity(new Intent(this, HomeActivity.class));
                    } else {
                        etUser.setError("Invalid Login Credentials  ");
                        etPass.setText("");
                    }

                }
                break;

            case R.id.btnRegister:
                Intent create = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(create);
                break;

        }
    }
}