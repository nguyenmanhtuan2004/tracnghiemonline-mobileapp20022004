package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_selectted_test_index;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class SetTimeForTestActivity extends AppCompatActivity {

    EditText edt_day,edt_month,edt_year,edt_hour,edt_minute,edt_time;

    String day, month, year,setTimeForStart,hour,minute;
    private Toolbar toolbar;
    int time=-1;
    Button btnSave,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_for_test);

        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addControls();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SetTimeForTestActivity.this, CreateQuestionActivity.class);
                startActivity(intent);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=String.valueOf(edt_day.getText().toString());
                month=edt_month.getText().toString();
                year=edt_year.getText().toString();
                hour=edt_hour.getText().toString();
                minute=edt_minute.getText().toString();
                setTimeForStart=String.valueOf(month+"/"+day+"/"+year+" "+hour+":"+minute+":00");
                if(validate())
                {
                    DbQuery.saveSetTime(setTimeForStart,Integer.valueOf(edt_time.getText().toString()), new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            Intent intent=new Intent(SetTimeForTestActivity.this, CreateQuestionActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(SetTimeForTestActivity.this,"Nhập đúng form đi",Toast.LENGTH_SHORT).show();
                        }
                    });

                }


            }
        });
    }

    private boolean validate()
    {
        if (day.isEmpty())
        {
            edt_day.setError("Day can not be empty!");
            return false;
        }
        if(!day.isEmpty())
        {
            if(day.length()!=2)
            {
                edt_day.setError("Enter Valid Day");
                return false;
            }

        }

        if (month.isEmpty())
        {
            edt_month.setError("Month can not be empty!");
            return false;
        }
        if(!month.isEmpty())
        {
            if(month.length()!=2)
            {
                edt_month.setError("Enter Valid month");
                return false;
            }

        }
        if (year.isEmpty())
        {
            edt_year.setError("Year can not be empty!");
            return false;
        }
        if(!year.isEmpty())
        {
            if(year.length()!=4)
            {
                edt_day.setError("Enter Valid Year");
                return false;
            }

        }
        if (hour.isEmpty())
        {
            edt_hour.setError("Hour can not be empty!");
            return false;
        }
        if(!hour.isEmpty())
        {
            if(hour.length()!=2)
            {
                edt_hour.setError("Enter Valid hour");
                return false;
            }

        }
        if (edt_minute.getText().toString().isEmpty())
        {
            edt_minute.setError("Minute can not be empty!");
            return false;
        }

        if (edt_time.getText().toString().isEmpty())
        {
            edt_time.setError("Time can not empty");
            return false;
        }

        return true;
    }
    private void addControls() {
        edt_day=findViewById(R.id.edt_day);
        edt_month=findViewById(R.id.edt_month);
        edt_year=findViewById(R.id.edt_year);
        edt_time=findViewById(R.id.edt_time);
        edt_hour=findViewById(R.id.edt_hour);
        edt_minute=findViewById(R.id.edt_minute);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);
    }
}