package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                if(mAuth.getCurrentUser()!=null)//đăng nhập rồi
                {
                    //để load dữ liệu của từ bảng Catergory
                    DbQuery.loadCategories(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(SplashActivity.this, "Có gì đó sai! Vui lòng thử lại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
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

        DbQuery.g_firestore =FirebaseFirestore.getInstance();
    }
}