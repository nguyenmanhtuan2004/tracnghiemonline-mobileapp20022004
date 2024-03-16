package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.quizapp.model.DbQuery.loadData;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

import java.util.concurrent.TimeUnit;

import org.w3c.dom.Text;

//public class ScoreActivity extends AppCompatActivity {
//
//    private TextView scoreTV , timeTV , totalQTV , correctQTV, wrongQTV , unattemptedQTV;
//    private Button leaderB , reAttempB , viewAnsB;
//    private long timeTaken;
//    private Dialog progressDialog;
//    private TextView dialogText;
//    private int finalScore;



public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV, timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
    Button leaderB, reAttemptB, viewAnsB;
    private long timeTaken;
    private Dialog progress_Dialog;
    private TextView dialogText;
    private int finalscore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Part 33
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
            //Part 33
            // viewAnsB.setOnClickListener ( new View.OnClickListener(){

           // public void onClick (View view){
            //    Intent intent = new Intent(ScoreActivity.this , AnswersActivity.class);
             //   startActivity(Intent);
           // }


          //}

        });
        progress_Dialog=new Dialog(ScoreActivity.this);//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progress_Dialog.show();

        addControls();

        loadData();

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
            }
        });

        saveResult();
    }

    private void saveResult() {

        DbQuery.saveResult(finalscore, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progress_Dialog.dismiss();
            }

            @Override
            public void onFailure() {

                Toast.makeText(ScoreActivity.this,"Something went wrong! Please try again later!ScoreActivity",Toast.LENGTH_SHORT).show();
                progress_Dialog.dismiss();
            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            ScoreActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addControls() {
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.correctQ);
        wrongQTV = findViewById(R.id.wrongQ);
        unattemptedQTV = findViewById(R.id.un_attemptedQ);
        leaderB = findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answerB);
    }

    private void loadData(){
        int correctQ = 0, wrongQ = 0, unattemptedQ = 0;

        for (int i = 0; i < DbQuery.g_quesList.size(); i++)
        {
            if (DbQuery.g_quesList.get(i).getSelectedAns() == -1)
            {
                unattemptedQ++;
            }
            else
            {
                if (DbQuery.g_quesList.get(i).getSelectedAns() == DbQuery.g_quesList.get(i).getCorrectAns())
                {
                    correctQ++;
                }
                else
                {
                    wrongQ++;
                }
            }
        }

        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptedQ));

        totalQTV.setText(String.valueOf(DbQuery.g_quesList.size()));

        finalscore = (int)((correctQ*100)/DbQuery.g_quesList.size());
        scoreTV.setText(String.valueOf(finalscore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);
        String time = String.format("%02d:%02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken),
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken)));

        timeTV.setText(time);
        progress_Dialog.dismiss();
    }

    private void reAttempt()
    {
        for (int i = 0; i < DbQuery.g_quesList.size(); i++)
        {
            DbQuery.g_quesList.get(i).setSelectedAns(-1);
            DbQuery.g_quesList.get(i).setStatus(DbQuery.NOT_VISITED);
            Intent intent = new Intent(ScoreActivity.this, StartTestActivity.class);
            startActivity(intent);
            finish();
        }
    }
}






