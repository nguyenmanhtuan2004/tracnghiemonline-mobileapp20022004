package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_selectted_test_index;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizapp.model.DbQuery;

public class SetTimeForTestActivity extends AppCompatActivity {

    EditText edt_day,edt_month,edt_year,edt_hour,edt_minute,edt_time;

    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_for_test);

        addControls();




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day, month, year,setTimeForStart,hour,minute;
                day=edt_day.getText().toString();
                month=edt_month.getText().toString();
                year=edt_year.getText().toString();
                hour=edt_hour.getText().toString();
                minute=edt_minute.getText().toString();
                setTimeForStart=month+"/"+day+"/"+year+" "+hour+":"+minute+":00";
                DbQuery.g_testList.get(g_selectted_test_index).setTime(Integer.valueOf(edt_time.getText().toString()));

                DbQuery.g_testList.get(g_selectted_test_index).setStart(setTimeForStart);
            }
        });
    }

    private void addControls() {
        edt_day=findViewById(R.id.edt_day);
        edt_month=findViewById(R.id.edt_month);
        edt_year=findViewById(R.id.edt_year);
        edt_time=findViewById(R.id.edt_time);
        edt_hour=findViewById(R.id.edt_hour);
        edt_minute=findViewById(R.id.edt_minute);
        btnSave=findViewById(R.id.btnSave);
    }
}