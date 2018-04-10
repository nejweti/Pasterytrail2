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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mRegisterUsername;
    private EditText mRegisterPassword;
    private Button mRegisterButton;
    private LinearLayout mSignin;
    String username;
    String password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterPassword = (EditText) findViewById(R.id.register_password);
        mRegisterUsername = (EditText) findViewById(R.id.register_email);
        mSignin = (LinearLayout) findViewById(R.id.signin);
        firebaseAuth = FirebaseAuth.getInstance();
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("yes","clicked");
                signinUser();
            }
        });
        mRegisterButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == mRegisterButton){
            registerUser();
        }

    }

    private void signinUser() {
       startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
       this.finish();
    }

    public void registerUser(){
        username = mRegisterUsername.getText().toString().trim();
        password = mRegisterPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            mRegisterUsername.setError("Email Empty");
        }
        else if (TextUtils.isEmpty(password)){
            mRegisterPassword.setError("Password Empty");
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("success", "createUserWithEmail:success");
                                startActivity(new Intent(RegisterActivity.this,Main_Menu.class));
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("success", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            }

                        }
                    });
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
    }
}
