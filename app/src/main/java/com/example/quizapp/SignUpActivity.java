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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText username,emailID,password_signup,confirm_pass;
    private Button signUpBtn;
    private ImageView backB;
    private FirebaseAuth mAuthSingnup;
    private String emailStr, passStr,confirmPassStr, nameStr;
    private Dialog progress_Dialog;
    private TextView dialogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        addControls();
        addEvents();
    }

    private void addEvents() {
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    signupNewUser();
                }
            }
        });
    }
    private void addControls() {
        username=findViewById(R.id.username);
        emailID=findViewById(R.id.emailID);
        password_signup=findViewById(R.id.password_signup);
        confirm_pass=findViewById(R.id.confirm_pass);
        signUpBtn=findViewById(R.id.signupBtn);
        backB=findViewById(R.id.backB);
        mAuthSingnup=FirebaseAuth.getInstance();
        progress_Dialog=new Dialog(SignUpActivity.this);//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Registering user...");


    }
    private boolean validate()
    {
        nameStr=username.getText().toString().trim();
        passStr=password_signup.getText().toString().trim();
        emailStr=emailID.getText().toString().trim();
        confirmPassStr=confirm_pass.getText().toString().trim();

        if(nameStr.isEmpty())
        {
            username.setError("Enter your name");
            return false;
        }
        if(emailStr.isEmpty())
        {
            emailID.setError("Enter Email ID");
            return false;
        }
        if(passStr.isEmpty())
        {
            password_signup.setError("Enter Email ID");
            return false;
        }
        if(confirmPassStr.isEmpty())
        {
            confirm_pass.setError("Enter Email ID");
            return false;
        }
        if(passStr.compareTo(confirmPassStr)!=0)
        {
            Toast.makeText(SignUpActivity.this,"Password and confirm Password should be same",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void signupNewUser()
    {
        progress_Dialog.show();
        mAuthSingnup.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this,"Sign Up Successful",Toast.LENGTH_SHORT).show();

                            DbQuery.createUserData(emailStr,nameStr);

                            progress_Dialog.dismiss();//đóng Dialog tiến trình
                            Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
                            startActivity(intent);
                            SignUpActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progress_Dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}