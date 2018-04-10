package com.nejat.pasterytrail2.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mLoginUsername;
    private EditText mLoginPassword;
    private Button mLoginButton;
    String username;
    String password;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginUsername = (EditText) findViewById(R.id.login_email);
        firebaseAuth = FirebaseAuth.getInstance();

        mLoginButton.setOnClickListener(this);
    }
    public void login(){
        username = mLoginUsername.getText().toString().trim();
        password = mLoginPassword.getText().toString().trim();

        if(TextUtils.isEmpty(username)){
            mLoginUsername.setError("Email Empty");
        }
        else if (TextUtils.isEmpty(password)){
            mLoginUsername.setError("Password Empty");

        }

        else{
            Log.i("username",username);
            Log.i("pass",password);
            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "signInWithEmail:success");
                                 startActivity(new Intent(LoginActivity.this,Main_Menu.class));
                                FirebaseUser user = firebaseAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("failure", "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }
                        }
                    });
        }

    }

    @Override
    public void onClick(View v) {
        if(v == mLoginButton){
            login();
        }
    }
}
