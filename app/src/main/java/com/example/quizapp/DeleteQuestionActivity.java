package com.example.quizapp;





import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.quizapp.Adapter.DeleteQuestionAdapter;
import com.example.quizapp.Adapter.TestAdapter;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class DeleteQuestionActivity extends AppCompatActivity {

    private RecyclerView questionView;
    private Toolbar toolbar;
    private DeleteQuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_question);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        questionView=findViewById(R.id.test_recycler_view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        questionView.setLayoutManager(layoutManager);




        DbQuery.loadquestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter = new DeleteQuestionAdapter(DbQuery.g_quesList);
                //testList là danh sách object TestModel
                questionView.setAdapter(adapter);
                Toast.makeText(DeleteQuestionActivity.this,"Load dữ liệu thành công",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {

                Toast.makeText(DeleteQuestionActivity.this,"Load dữ liệu thất bại",Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            DeleteQuestionActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}