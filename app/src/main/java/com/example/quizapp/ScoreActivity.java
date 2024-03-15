package com.example.quizapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV , timeTV , totalQTV , correctQTV, wrongQTV , unattemptedQTV;
    private Button leaderB , reAttempB , viewAnsB;
    private long timeTaken;
    private Dialog progressDialog;
    private TextView dialogText;
    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Part 33
        // setSupportActionBar(toolbar);
        // setSupportActionBar().setDisplayShowTitleEnabled(true);
        // setSupportActionBar().SetTitle("Result");
        // setSupportActionBar().setDisplayHomeAsUpEnabled(true);


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


    }



}