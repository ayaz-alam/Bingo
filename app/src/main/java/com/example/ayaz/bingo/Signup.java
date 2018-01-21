package com.example.ayaz.bingo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {
    EditText Email,Password,Name;
    Button Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Email=findViewById(R.id.sign_up_email);
        Password=findViewById(R.id.sign_up_pass);
        Name=findViewById(R.id.display_name);
        Signup=findViewById(R.id.signup_button);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String pass=Password.getText().toString();
                String name=Name.getText().toString();

                if(isOkay(email,pass,name))
                    authenticate(email,pass,name);
            }
        });
    }

    private void authenticate(String email, String pass, final String name) {
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    user.updateProfile(profileUpdates);
                    Toast.makeText(getApplicationContext(),"Successfully Created",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this,MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Cannot Created"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean isOkay(String email, String pass,String name) {


        Email.setError(null);
        Password.setError(null);
        Name.setError(null);
        boolean isOkay=true;
        if(email.equals(""))
        {
            Email.setError("Required Field");
            Email.requestFocus();
            isOkay=false;
        }
        else if(email.length()<6||!email.contains("@")||!email.contains(".com"))
        {
            Email.setError("Invalid Email");
            Email.requestFocus();
            isOkay=false;

        }
        else if(pass.equals(""))
        {
            Password.setError("Required Field");
            Password.requestFocus();
            isOkay=false;
        }
        else if(pass.length()<6)
        {
            Password.setError("Password length must be at least 6 digit");
            Password.requestFocus();


            isOkay=false;
        }
        else if(name.equals(""))
        {
            Name.setError("We need your name please");
            Name.requestFocus();

            isOkay=false;
        }

        return isOkay;
    }

}
