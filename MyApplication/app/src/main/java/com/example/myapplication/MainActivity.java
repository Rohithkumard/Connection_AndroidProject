package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    private FirebaseDatabase database;
    private DatabaseReference db_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Register(View view){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        finish();
        Log.d("reg", "register method");
        startActivity(intent);

    }
    public void signinOperation(View view){
        email = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        String sEmail = email.getText().toString();
        String sPassword = password.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                                    Toast.makeText(MainActivity.this, "Sign In success",
                                            Toast.LENGTH_SHORT).show();
                                    switchActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "signInWithEmail:failure");
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });

    }
    public void switchActivity(){
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        finish();
        startActivity(intent);
    }

}