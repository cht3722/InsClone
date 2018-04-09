package com.haotianchen.instgramclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    EditText userEditText;
    EditText passwordEditText;
    Button signButton;
    TextView changeTextView;
    // 1: sign up; 2: sign in
    int ID;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.changeTextView) {

            changeUporIn(view);

        } else if (view.getId() == R.id.backgroundLayout || view.getId() == R.id.logoImageView){

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == keyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

            signUpOrSignIn(view);

        }
        return false;
    }

    public void changeUporIn(View view){

        if (ID == 1) {

            ID = 2;
            changeTextView.setText("Or Sign up");
            signButton.setText("SIGN IN");

        } else if (ID ==2) {

            ID = 1;
            changeTextView.setText("Or Sign in");
            signButton.setText("SIGN UP");
        }

    }

    public void signUpOrSignIn(View view) {

        String username = userEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Log.i("user", username);
        Log.i("password", password);
        if (ID == 1) {

            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(password);

            if (username == null || password == null || username.length() == 0 || password.length() == 0) {

                Toast.makeText(LoginActivity.this, "The username/password cannot be empty", Toast.LENGTH_SHORT).show();

                return;
            }

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        Toast.makeText(LoginActivity.this, "Successful sign up!", Toast.LENGTH_SHORT).show();

                        Log.i("Login", "success");

                        toUserListActivity();

                    } else {

                        Toast.makeText(LoginActivity.this, "Invalid username/password", Toast.LENGTH_SHORT).show();

                        Log.i("Invalid reasson:", e.toString());

                    }
                }
            });


        } else if (ID == 2) {

            ParseUser.logInInBackground(username, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {

                        Toast.makeText(LoginActivity.this, "Successful sign in!", Toast.LENGTH_SHORT).show();

                        toUserListActivity();

                    } else {

                        Toast.makeText(LoginActivity.this, "Wrong username/password!", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());


    }

    public void toUserListActivity() {

        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        

        userEditText = (EditText) findViewById(R.id.userEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        signButton = (Button) findViewById(R.id.signButton);
        changeTextView = (TextView) findViewById(R.id.changeTextView);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.backgroundLayout);
        ImageView imageView = (ImageView) findViewById(R.id.logoImageView);


        changeTextView.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        imageView.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);
        ID = 1;




    }


}
