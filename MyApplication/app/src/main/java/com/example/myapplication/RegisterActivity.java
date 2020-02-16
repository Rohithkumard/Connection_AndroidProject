package com.example.myapplication;

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

public class RegisterActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText confpassword;
    EditText email;
    EditText phnumber;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    Boolean checking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }

    public void signUpProcess(View view) {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.regpassword);
        confpassword = (EditText) findViewById(R.id.regconfpassword);
        email = (EditText) findViewById(R.id.emailId);
        phnumber = (EditText) findViewById(R.id.phnumber);

        final String sUsername = username.getText().toString();
        final String sPassword = password.getText().toString();
        String sConfPassword = confpassword.getText().toString();
        final String sEmail = email.getText().toString();
        final String sPhNumber = phnumber.getText().toString();


        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(RegisterActivity.this, "Before getKey",
                Toast.LENGTH_SHORT).show();

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Register");
        final String Uid = databaseRef.push().getKey();

        mAuth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "usercreated");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            Users users = new Users(sUsername, sPassword, sPhNumber, sEmail);
                            databaseRef.child(Uid).setValue(users);

                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "createUserWithEmail:failure");
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
//                        if(checking){
//                            Intent intent = new Intent(RegisterActivity.this, ChatActivity.class);
//                            finish();
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "False reccieved", Toast.LENGTH_SHORT).show();
//                        }
                    }
                });
    }
}