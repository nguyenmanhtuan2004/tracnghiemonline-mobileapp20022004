package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_catList;
import static com.example.quizapp.model.DbQuery.g_selectted_test_index;
import static com.example.quizapp.model.DbQuery.loadquestions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;
import com.google.protobuf.StringValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StartTestActivity extends AppCompatActivity {
    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;
    private Dialog progress_Dialog;
    private TextView dialogText;

    private Date time1;
    private Date time2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        init();

        progress_Dialog=new Dialog(StartTestActivity.this);//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");

        progress_Dialog.show();

        loadquestions(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                setData();
                progress_Dialog.dismiss();
            }

            @Override
            public void onFailure() {
                progress_Dialog.dismiss();
                Toast.makeText(StartTestActivity.this, "Có gì đó sai! Vui lòng thử lại",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        catName=findViewById(R.id.st_cat_name);
        testNo=findViewById(R.id.st_test_no);
        totalQ=findViewById(R.id.st_total_ques);
        bestScore=findViewById(R.id.st_best_score);
        time=findViewById(R.id.st_time);
        startTestB=findViewById(R.id.start_testB);
        backB=findViewById(R.id.st_backB);




        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTestActivity.this.finish();
            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRange();
            }
        });




    }
//    private void setTimeForTest()
//    {
//        DbQuery.g_testList.get(g_selectted_test_index).setTime();
//    }
    private void checkRange(){
        Calendar now = Calendar.getInstance();
        now.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String mystr1 = DbQuery.g_testList.get(g_selectted_test_index).getStart();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            time1 = format.parse(mystr1);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time1);
            calendar.add(Calendar.MINUTE,
                    DbQuery.g_testList.get(g_selectted_test_index).getTime());
            time2=calendar.getTime();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if ( now.getTime().after(time1) && now.getTime().before(time2)) {
            Intent intent = new Intent(StartTestActivity.this, QuestionActivity.class);
            intent.putExtra("TIME_ENTRANCE",(long) now.getTime().getTime());
            intent.putExtra("TIME_START",(long) time1.getTime());
            startActivity(intent);
            finish();
        }
        else
        {
            if(now.getTime().before(time1))
            {
                String log1=format.format(time1);
                String log=String.valueOf(log1+" mới tới giờ làm bài");
                Toast.makeText(StartTestActivity.this,log,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(StartTestActivity.this, StartTestActivity.class);
                startActivity(intent);
                finish();
            }
            else if(now.getTime().after(time2))
            {
                String log2=format.format(time2);
                String log=String.valueOf("đã hết thời gian làm bài lúc "+log2);
                Toast.makeText(StartTestActivity.this,log,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(StartTestActivity.this, StartTestActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }
    private void setData()
    {
        catName.setText(g_catList.get(DbQuery.g_selected_cat_index).getName());
        testNo.setText("Test No. " + String.valueOf(DbQuery.g_selectted_test_index+1));
        totalQ.setText(String.valueOf(DbQuery.g_quesList.size()));
        bestScore.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selectted_test_index).getTopScore()));
        time.setText(String.valueOf(DbQuery.g_testList.get(DbQuery.g_selectted_test_index).getTime()));
    }
}