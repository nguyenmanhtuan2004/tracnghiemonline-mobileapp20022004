package com.example.demodb5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demodb5.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    public String DATABASE_NAME="mydatabase5";
    public String DB_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database=null;
    ListView lvUser;
    Button btnOpen;
    ArrayAdapter<User> adapterUser;
    //lưu trữ dữ liệu của bảng user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy();
        addControls();
        addEvents();
        xulyCapnhat();
    }

    private void xulyCapnhat() {
        //khi nhấp vào item, lấy dòng thông tin tương ứng gắn vào user
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user=adapterUser.getItem(position);//lấy theo vị trí tương ứng//trả về object User theo position
                Intent intent=new Intent(MainActivity.this, CapnhatuserActivity.class);
                intent.putExtra("u",user);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void addEvents() {
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ThemuserActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        //mo csdl
        Cursor cursor=database.rawQuery("select * from tbUser",null);//truy vấn
        adapterUser.clear();
        while (cursor.moveToNext())//moveToNext dùng để chuyển từng dòng

        {
            String ma= cursor.getString(0);
            String ten= cursor.getString(1);
            String phone= cursor.getString(2);
            //số trong getString dùng để chỉ số cột
            //truy vấn dữ liệu từ csdl
            User u=new User(ma,ten,phone);//tạo ra đối tượng user
            adapterUser.add(u);//add vào adapter
            //adapter đổ dữ liệu lên listview
        }
        cursor.close();

    }

    private void addControls() {
        btnOpen=findViewById(R.id.btnOpenNew);
        lvUser=findViewById(R.id.lvUser);
        //load cái database mà bên trong arraydapter
        adapterUser=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvUser.setAdapter(adapterUser);//đổ dữ liệu vào listview

    }

    public String getDatabasePath()
    {
        return getApplicationInfo().dataDir+DB_SUFFIX_PATH+DATABASE_NAME;

    }

    private void processCopy() {

        try {
            File file=getDatabasePath(DATABASE_NAME);
            if(!file.exists()) copyDatabaseFromAssest();
            Toast.makeText(this,"Copy Database Successful",Toast.LENGTH_SHORT).show();


        }catch (Exception e)
        {
            Toast.makeText(this,"Copy Database Fail",Toast.LENGTH_SHORT).show();
        }
    }

    private void copyDatabaseFromAssest()
            //sao chép dữ liệu SQLite vào app Android
    {
        try {
            InputStream inputFile=getAssets().open(DATABASE_NAME);
            String outputFileName=getDatabasePath();
            File file=new File(getApplication().getDataDir()+DB_SUFFIX_PATH);
            if(!file.exists())
                file.mkdir();
            OutputStream outfile=new FileOutputStream(outputFileName);
            byte []buffer=new byte[1024];
            int length;
            while ((length=inputFile.read(buffer))>0)
                outfile.write(buffer,0,length);
            outfile.flush();
            outfile.close();
            inputFile.close();
        }catch (Exception ex)
        {
            Log.e( "Error ",ex.toString());
        }
    }
}