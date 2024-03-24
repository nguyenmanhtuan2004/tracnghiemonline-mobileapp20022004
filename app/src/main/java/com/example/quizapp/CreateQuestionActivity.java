package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class CreateQuestionActivity extends AppCompatActivity {

    private EditText edt_optionA,edt_optionB,edt_optionC,edt_optionD,
            edt_answer,edt_answer2,edt_question;
    private Button btnSave,btnCancel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edt_optionA=findViewById(R.id.edt_optionA);
        edt_optionB=findViewById(R.id.edt_optionB);
        edt_optionC=findViewById(R.id.edt_optionC);
        edt_optionD=findViewById(R.id.edt_optionD);
        edt_answer=findViewById(R.id.edt_answer);
        edt_answer2=findViewById(R.id.edt_answer2);
        edt_question=findViewById(R.id.edt_question);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(CreateQuestionActivity.this, DeleteQuestionActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validate())
                {
                    DbQuery.createQuestionData(edt_optionA.getText().toString(),
                            edt_optionB.getText().toString(),
                            edt_optionC.getText().toString(),
                            edt_optionD.getText().toString(),
                            edt_question.getText().toString(),
                            Integer.valueOf(edt_answer.getText().toString()),
                            Integer.valueOf(edt_answer2.getText().toString()),
                            new MyCompleteListener() {
                                @Override
                                public void onSuccess() {
                                    Intent intent=new Intent(CreateQuestionActivity.this, DeleteQuestionActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure() {

                                    Toast.makeText(CreateQuestionActivity.this,"Load dữ liệu thất bại",Toast.LENGTH_SHORT).show();

                                }
                            }
                    );
                }
            }
        });

    }
    private boolean validate()
    {
        if (edt_optionA.getText().toString().isEmpty())
        {
            edt_optionA.setError("OptionA can not be empty!");
            return false;
        }

        if (edt_optionB.getText().toString().isEmpty())
        {
            edt_optionB.setError("OptionB can not be empty!");
            return false;
        }
        if (edt_optionC.getText().toString().isEmpty())
        {
            edt_optionC.setError("OptionC can not be empty!");
            return false;
        }
        if (edt_optionD.getText().toString().isEmpty())
        {
            edt_optionD.setError("OptionD can not be empty!");
            return false;
        }
        if (edt_answer2.getText().toString().isEmpty())
        {
            edt_answer2.setError("Answer2 can not be empty!");
            return false;
        }
        if (edt_answer.getText().toString().isEmpty())
        {
            edt_answer.setError("Answer can not be empty!");
            return false;
        }


        return true;
    }
}