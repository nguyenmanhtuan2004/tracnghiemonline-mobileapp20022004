package com.example.demodb5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demodb5.model.User;

public class ThemuserActivity extends AppCompatActivity {
    EditText edtMa,edtTen,edtPhone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themuser);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                //tạo một dòng mới để insert dữ liệu
                values.put("maU",edtMa.getText().toString());//(COLUMN_NAME,values)
                values.put("tenU",edtTen.getText().toString());
                values.put("phoneU",edtPhone.getText().toString());
                //gán giá trị vào mỗi cột trong dòng vừa tạo
                long kq=MainActivity.database.insert("tbUser",null,values);
                if(kq>0)
                {
                    finish();
                }else
                {
                    Toast.makeText(ThemuserActivity.this,"Insert new record Fail",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void addControls() {
        edtMa=findViewById(R.id.edtMa);
        edtTen=findViewById(R.id.edtTen);
        edtPhone=findViewById(R.id.edtPhone);
        btnSave=findViewById(R.id.btnSave);

    }


}