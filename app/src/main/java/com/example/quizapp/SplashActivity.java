package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    TextView appName;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        addControl();
        addEvents();
    }

    private void addEvents() {
        appName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user=mAuth.getCurrentUser();
                if(user!=null)//đăng nhập rồi//này lỗi rồi
                {
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }
                else
                {
                    Intent intent=new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SplashActivity.this.finish();
                }

            }
        });
    }


    private void addControl() {
        appName=findViewById(R.id.app_name);
        mAuth=FirebaseAuth.getInstance();
    }
}