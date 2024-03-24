package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class UpdateQuestionActivity extends AppCompatActivity {

    private EditText txtQuestion,txtOptionA,txtOptionB,txtOptionC,txtOptionD,txtAnswer,txtAnswer2;
    private Button btnDelete, btnUpdate, btnCancel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnDelete=findViewById(R.id.btnDelete);
        btnUpdate=findViewById(R.id.btnUpdate);
        txtQuestion=findViewById(R.id.txtQuestion);
        txtOptionA=findViewById(R.id.txtOptionA);
        txtOptionB=findViewById(R.id.txtOptionB);
        txtOptionC=findViewById(R.id.txtOptionC);
        txtOptionD=findViewById(R.id.txtOptionD);
        btnCancel=findViewById(R.id.btnCancel);
        txtAnswer=findViewById(R.id.txtAnswer);
        txtAnswer2=findViewById(R.id.txtAnswer2);
        DbQuery.loadquestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                txtQuestion.setText(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getQuestion());
                txtOptionA.setText(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getOptionA());
                txtOptionB.setText(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getOptionB());
                txtOptionC.setText(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getOptionC());
                txtOptionD.setText(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getOptionD());
                txtAnswer.setText(String.valueOf(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getCorrectAns()));
                txtAnswer2.setText(String.valueOf(DbQuery.g_quesList.get(DbQuery.g_selectted_question_index).getCorrectAns2()));
                Toast.makeText(UpdateQuestionActivity.this,"Load dữ liệu thành công",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure() {
                Toast.makeText(UpdateQuestionActivity.this,"Load dữ liệu thất bại",Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UpdateQuestionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbQuery.updateQuestionData(txtOptionA.getText().toString(),
                        txtOptionB.getText().toString(),
                        txtOptionC.getText().toString(),
                        txtOptionD.getText().toString(),
                        txtQuestion.getText().toString(),
                        Integer.valueOf(txtAnswer.getText().toString()),
                        Integer.valueOf(txtAnswer2.getText().toString()),
                        new MyCompleteListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(UpdateQuestionActivity.this,"Update success",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(UpdateQuestionActivity.this, DeleteQuestionActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbQuery.deleteQuestionData(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdateQuestionActivity.this,"Xóa dữ liệu thành công",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(UpdateQuestionActivity.this, DeleteQuestionActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(UpdateQuestionActivity.this,"Xóa dữ liệu thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}