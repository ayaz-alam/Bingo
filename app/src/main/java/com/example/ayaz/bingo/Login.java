package com.example.ayaz.bingo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference reference;
    EditText Email,Password;
    Button Login;
    TextView Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        reference=FirebaseDatabase.getInstance().getReference();
        Email=findViewById(R.id.email_login);
        Password=findViewById(R.id.password_login);
        Login=findViewById(R.id.login_button);
        Register=findViewById(R.id.login_signup);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=Email.getText().toString();
                String pass=Password.getText().toString();

                    if (isOkay(email,pass))
                        authenticate(email,pass);

            }
        });
Register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(Login.this,Signup.class));
        finish();
    }
});





    }

    private boolean isOkay(String email, String pass) {

         
            Email.setError(null);
            Password.setError(null);
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
            else if(pass.equals("123456"))
            {
                Password.setError("Password must be complicated");
                Password.requestFocus();

                isOkay=false;
            }

            return isOkay;
        }
    

    private void authenticate(String email,String pass) {


     mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful())
        {
            DatabaseReference mRef=FirebaseDatabase.getInstance().getReference();
            mRef.child("USERS").push().setValue(mAuth.getCurrentUser().getEmail());
            startActivity(new Intent(Login.this,MainActivity.class));
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),""+task.isSuccessful(),Toast.LENGTH_SHORT).show();
         }
     });


    }


}
