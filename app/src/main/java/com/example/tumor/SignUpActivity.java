package com.example.tumor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailbox,passwordbox,namebox;
    Button login,signup;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();




        emailbox=findViewById(R.id.emailBox);
        passwordbox=findViewById(R.id.passwordBox);
        namebox=findViewById(R.id.namebox);
        login=findViewById(R.id.Login);
        signup=findViewById(R.id.Signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,email,pass;
                email=emailbox.getText().toString();
                name=namebox.getText().toString();
                pass=passwordbox.getText().toString();
                User user=new User();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(pass);
                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful())
                         {
                             //success
                             database.collection("Users")
                                     .document().set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                 }
                             });

                             Toast.makeText(SignUpActivity.this, "Account is Created", Toast.LENGTH_SHORT).show();
                         }
                         else{
                             Toast.makeText(SignUpActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                         }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });


    }
}