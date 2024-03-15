package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

public class MyProfileActivity extends AppCompatActivity {


    private EditText edt_name, edt_email, edt_phone;
    private LinearLayout editB;
    private Button btnCancel, btnSave;
    private TextView txtProfile;
    private LinearLayout btn_layout;
    private String nameStr, phoneStr;
    private Dialog progress_Dialog;
    private TextView dialogText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();

        progress_Dialog=new Dialog(MyProfileActivity.this);
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Updating Data...");

        disableEditing();

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditting();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableEditing();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate())
                    saveData();
            }
        });
    }

    private void disableEditing() {
        edt_name.setEnabled(false);
        edt_email.setEnabled(false);
        edt_phone.setEnabled(false);

        btn_layout.setVisibility(View.GONE);

        edt_name.setText(DbQuery.myProfile.getName());
        edt_email.setText(DbQuery.myProfile.getEmail());

        if(DbQuery.myProfile.getPhone() != null)
            edt_phone.setText(DbQuery.myProfile.getPhone());

        String profileName = DbQuery.myProfile.getName();
        txtProfile.setText(profileName.toUpperCase().substring(0,1));
    }

    private void enableEditting()
    {
        edt_name.setEnabled(true);
        //edt_email.setEnabled(true);
        edt_phone.setEnabled(true);

        btn_layout.setVisibility(View.VISIBLE);
    }
    private void addControls() {
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        txtProfile = findViewById(R.id.txtProfile);
        editB = findViewById(R.id.editB);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        btn_layout = findViewById(R.id.btn_layout);
    }

    private boolean validate()
    {
        nameStr = edt_name.getText().toString();
        phoneStr = edt_phone.getText().toString();

        if (nameStr.isEmpty())
        {
            edt_name.setError("Name can not be empty!");
            return false;
        }

        if (!phoneStr.isEmpty())
        {
            if(!((phoneStr.length() == 10) && (TextUtils.isDigitsOnly(phoneStr))))
            {
                edt_phone.setError("Enter Valid Phone Number");
                return false;
            }
        }
        return true;
    }

    private void saveData()
    {
        progress_Dialog.show();
        if(phoneStr.isEmpty())
            phoneStr = null;

        DbQuery.saveProfileData(nameStr, phoneStr, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MyProfileActivity.this, "Profile Updated Successfully.", Toast.LENGTH_SHORT).show();
                disableEditing();
                progress_Dialog.dismiss();
            }

            @Override
            public void onFailure() {
                Toast.makeText(MyProfileActivity.this, "Something went wrong! Please try again later.", Toast.LENGTH_SHORT).show();
                progress_Dialog.dismiss();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            MyProfileActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}