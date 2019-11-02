package com.salavation.carmall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private static final String MY_USERNAME = "test@aigen.tech";
    private static final String MY_PASSWORD = "Aigen Tech";
    private EditText username;
    private EditText password;
    private ProgressBar progress;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.input_email);
        password = findViewById(R.id.input_password);
        progress = findViewById(R.id.progress);
        login = findViewById(R.id.btn_login);

        progress.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setEnabled(false);
                progress.setVisibility(View.VISIBLE);
                if (validateInput(username.getText().toString().trim(), password.getText().toString().trim())) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, FeedsActivity.class));
                            finish();
                        }
                    }, 2000);

                } else {
                    login.setEnabled(true);
                    progress.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Wrong Credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(String user, String pass) {
        return user.equalsIgnoreCase(MY_USERNAME) && pass.equalsIgnoreCase(MY_PASSWORD);
    }
}
