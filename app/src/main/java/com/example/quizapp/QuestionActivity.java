package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizapp.model.DbQuery;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private TextView txtQuesID, txtTimer, txtOK;
    private Button btnSubmit, btnMarkForReview, btnClear;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        addControls();

        QuestionAdapter quesAdapter = new QuestionAdapter(DbQuery.g_quesList);
        questionsView.setAdapter(quesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

    }

    private void addControls() {
        questionsView = findViewById(R.id.questions_view);
        txtQuesID = findViewById(R.id.txtQuesID);
        txtTimer = findViewById(R.id.txtTimer);
        txtOK = findViewById(R.id.txtOK);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnMarkForReview = findViewById(R.id.btnMarkForView);
        btnClear = findViewById(R.id.btnClear);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        quesListB = findViewById(R.id.ques_list_gridB);
    }
}