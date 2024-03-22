package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class CreateQuestionActivity extends AppCompatActivity {

    EditText edt_optionA,edt_optionB,edt_optionC,edt_optionD,
            edt_answer,edt_answer2,edt_question,edt_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
    }
}