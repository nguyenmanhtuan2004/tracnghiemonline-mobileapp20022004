package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_catList;
import static com.example.quizapp.model.DbQuery.g_quesList;
import static com.example.quizapp.model.DbQuery.g_selected_cat_index;
import static com.example.quizapp.model.DbQuery.g_selectted_test_index;
import static com.example.quizapp.model.DbQuery.g_testList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quizapp.model.DbQuery;

import java.util.concurrent.TimeUnit;

public class QuestionActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private TextView txtQuesID, txtTimer, txtCatName;
    private Button btnSubmit, btnMarkForReview, btnClear;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        addControls();

        QuestionAdapter quesAdapter = new QuestionAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        setSnapHelper();

        setClickListeners();

        startTimer();

    }

    private void startTimer() {

        long totalTimer = g_testList.get(g_selectted_test_index).getTime()*60*1000;

        CountDownTimer timer = new CountDownTimer(totalTimer +1000, 1000) {
            @Override
            public void onTick(long remainingTime) {
                String time = String.format("%02d:%02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime),
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));

                txtTimer.setText(time);
            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();
    }

    private void setClickListeners() {

        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quesID > 0)
                    questionsView.smoothScrollToPosition(quesID - 1);
            }
        });

        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quesID < g_quesList.size() - 1)
                    questionsView.smoothScrollToPosition(quesID + 1);
            }
        });
    }

    private void setSnapHelper() {
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);
                txtQuesID.setText(String.valueOf(quesID + 1) + "/" + String.valueOf(g_quesList.size()));

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void addControls() {
        questionsView = findViewById(R.id.questions_view);
        txtQuesID = findViewById(R.id.txtQuesID);
        txtTimer = findViewById(R.id.txtTimer);
        txtCatName = findViewById(R.id.txtCatName);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnMarkForReview = findViewById(R.id.btnMarkForView);
        btnClear = findViewById(R.id.btnClear);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        quesListB = findViewById(R.id.ques_list_gridB);
        quesID = 0;
        txtQuesID.setText("1/" + String.valueOf(g_quesList.size()));
        txtCatName.setText(g_catList.get(g_selected_cat_index).getName());
    }
}