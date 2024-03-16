package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Adapter.TestAdapter;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class TestActivity extends AppCompatActivity {

    private RecyclerView testView;
    //hiển thị danh sách các phần tử
    private Toolbar toolbar;


    private Dialog progress_Dialog;
    private TextView dialogText;
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //tiêu đề của Activity có được hiển thị trên ActionBar

        //nhận dữ liệu được truyền từ activity có khóa là CAT_INDEX

        getSupportActionBar().setTitle(DbQuery.g_catList.get(DbQuery.g_selected_cat_index).getName());
        //lấy tên ra để gán vào toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testView=findViewById(R.id.test_recycler_view);

        progress_Dialog=new Dialog(TestActivity.this);//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");

        progress_Dialog.show();

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);
                //đặt hướng thành chế độ xem

        DbQuery.loadTestData(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                DbQuery.loadMyScores(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        adapter = new TestAdapter(DbQuery.g_testList);
                        //testList là danh sách object TestModel
                        testView.setAdapter(adapter);

                        progress_Dialog.dismiss();
                    }

                    @Override
                    public void onFailure() {
                        progress_Dialog.dismiss();
                        Toast.makeText(TestActivity.this, "Có gì đó sai! Vui lòng thử lại",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure() {
                progress_Dialog.dismiss();
                Toast.makeText(TestActivity.this, "Có gì đó sai! Vui lòng thử lại",
                        Toast.LENGTH_SHORT).show();
            }
        });
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