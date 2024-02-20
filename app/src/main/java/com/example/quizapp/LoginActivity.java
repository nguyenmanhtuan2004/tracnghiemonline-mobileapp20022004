package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText email, pass;
    private Button loginB;
    private TextView forgotPassb, signupB;
    private Dialog progress_Dialog;
    private TextView dialogText;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        addEvents();
    }

    private void addEvents() {
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData())
                {
                    login();
                }
            }
        });

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean validateData()
    {

        if(email.getText().toString().isEmpty())
        {
            email.setError("Enter E-Mail ID");
            return false;
        }
        if(pass.getText().toString().isEmpty())
        {
            pass.setError("Enter Password");
            return false;
        }

        return true;
    }
    private void login()
    {
        progress_Dialog.show();
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            progress_Dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_SHORT).show();


                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();//hoạt động đăng nhập kết thúc
                        } else {
                            // If sign in fails, display a message to the user.
                            progress_Dialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void addControl() {
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        loginB=findViewById(R.id.loginB);
        forgotPassb=findViewById(R.id.forgetpassword);
        signupB=findViewById(R.id.signupB);

        mAuth=FirebaseAuth.getInstance();
        //giúp bạn truy cập và thao tác với các chức năng xác thực của Firebase trong ứng dụng Android.
        progress_Dialog=new Dialog(LoginActivity.this);//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Signing user...");
    }
}