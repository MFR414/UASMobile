package com.mfr414.uasproject.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mfr414.uasproject.MainActivity;
import com.mfr414.uasproject.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textRegister;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            Intent mainMenu = new Intent(this, MainActivity.class);
            startActivity(mainMenu);
        }

        btnLogin= findViewById(R.id.buttonLogin);
        editTextEmail = findViewById(R.id.editEmailLogin);
        editTextPassword = findViewById(R.id.editPasswordLogin);
        textRegister = findViewById(R.id.textViewRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            Toast.makeText(this,"Please insert your email",Toast.LENGTH_SHORT).show();
            return;
        }if(pass.isEmpty()){
            Toast.makeText(this,"Please insert your password",Toast.LENGTH_SHORT).show();
            return;
        }

        fAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent mainMenu = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(mainMenu);
                            Toast.makeText(LoginActivity.this,"Login Succesfull",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this,"Login Unsuccesfull",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
