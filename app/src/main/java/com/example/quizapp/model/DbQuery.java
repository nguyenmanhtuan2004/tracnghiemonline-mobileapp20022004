package com.example.quizapp.model;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Field;
import java.util.Map;

public class DbQuery {
    public static FirebaseFirestore g_firestore;
    //fireStore cung cấp các phương thức để tương tác các dữ liệu được lưu trữ trong FireStore:đọc, ghi, xóa


    public static void createUserData(String email, String name)
    {
        Map<String, Object> userData= new ArrayMap<>();
        userData.put("EMAIL_ID", email);//(key,value)
        userData.put("NAME",name);
        userData.put("TOTAL_SCORE",0);

        DocumentReference userDoc=g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //.getInstance quản lý việc xác thực người dùng trong ứng dụng
        //.getCurrentUser lấy thông tin người dùng hiện đang được xác thực (đăng nhập)
        //FirebaseAuth quản lý việc xác thực người dùng
        //DocumentReference cung cấp các phương thức để truy cập, cập nhật và xóa tài liệu đó
        WriteBatch batch=g_firestore.batch();
        batch.set(userDoc, userData);
        DocumentReference countDoc=g_firestore.collection("USERS").document("TOTAL_USERS");
        //.collection truy cập một bộ sưu tập (collection) trong Cloud Firestore
        //.collection("USERS") tham chiếu đến collection "USERS" trong FireStore
        batch.update(countDoc, "COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
