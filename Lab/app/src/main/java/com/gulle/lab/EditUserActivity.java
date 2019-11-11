package com.gulle.lab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUserActivity extends AppCompatActivity {

    EditText etUsername, etPassword, etFullname;
    String username, password, fullname;
    int formsuccess, userid;

    DbHelper db;
    ArrayList<HashMap<String,String>> selected_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        db = new DbHelper(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etFullname = findViewById(R.id.etFullname);

        Intent intent =  getIntent();
        userid = intent.getIntExtra(db.TBL_USER_ID,userid);
        selected_user = db.getSelectedUserData(userid);

        etUsername.setText(selected_user.get(0).get(db.TBL_USER_USERNAME));
        etPassword.setText(selected_user.get(0).get(db.TBL_USER_PASSWORD));
        etFullname.setText(selected_user.get(0).get(db.TBL_USER_FULLNAME));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_cancel, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSave:
                formsuccess = 3;
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                fullname = etFullname.getText().toString();

                // validate username
                if(username.equals("")) {
                    etUsername.setError("This field is required");
                    formsuccess--;
                }

                // validate password
                if(password.equals("")) {
                    etPassword.setError("This field is required");
                    formsuccess--;
                }

                // validate fullname
                if(fullname.equals("")) {
                    etFullname.setError("This field is required");
                    formsuccess--;
                }

                // validate success
                if(formsuccess == 3)
                    {HashMap<String, String> map_user = new HashMap();
                    map_user.put(db.TBL_USER_ID, String.valueOf(userid));
                    map_user.put(db.TBL_USER_USERNAME, username);
                    map_user.put(db.TBL_USER_PASSWORD, password);
                    map_user.put(db.TBL_USER_FULLNAME, fullname);
                    db.updateuser(map_user);

                    Toast.makeText(this, "User Successfully Updated", Toast.LENGTH_SHORT).show();
                    this.finish();
                }


                break;
            case R.id.btnCancel:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

