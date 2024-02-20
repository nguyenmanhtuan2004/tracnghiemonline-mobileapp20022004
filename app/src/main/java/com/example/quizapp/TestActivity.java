package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private RecyclerView testView;
    //hiển thị danh sách các phần tử có thể cuộn được
    private Toolbar toolbar;

    private List<TestModel> testList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //tiêu đề của Activity có được hiển thị trên ActionBar
        int cat_index=getIntent().getIntExtra("CAT_INDEX",0);
        //nhận dữ liệu được truyền từ activity có khóa là CAT_INDEX
        getSupportActionBar().setTitle(CatergoryFragment.catList.get(cat_index).getName());
        //lấy tên ra để gán vào toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testView=findViewById(R.id.test_recycler_view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);
                //đặt hướng thành chế độ xem

        loadTestData();

        TestAdapter adapter=new TestAdapter(testList);
        //testList là danh sách object TestModel
        testView.setAdapter(adapter);

    }

    private void loadTestData() {

        testList= new ArrayList<>();

        testList.add(new TestModel("1",50,20));
        testList.add(new TestModel("2",80,20));
        testList.add(new TestModel("3",0,25));
        testList.add(new TestModel("4",10,40));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}