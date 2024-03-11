package com.example.quizapp.model;

import android.util.ArrayMap;

import androidx.annotation.NonNull;

import com.example.quizapp.ProfileModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DbQuery {
    public static int g_selected_cat_index = 0;
    public static FirebaseFirestore g_firestore;
    //fireStore cung cấp các phương thức để tương tác các dữ liệu được lưu trữ trong FireStore:đọc, ghi, xóa
    public static List<CategoryModel> g_catList =new ArrayList<CategoryModel>();
    //Part 17
    public static int g_selectted_test_index = 0;
    public static List<QuestionsModel> g_quesList = new ArrayList<>();
    //end part 17
    public static ProfileModel myProfile = new ProfileModel("NA",null);//(name,email)
    //public static RankModel myPerformance = new RankModel(0,-1);

    public static List<TestModel> g_testList = new ArrayList<>();

    //PART 17
    public static void loadquestions(MyCompleteListener completeListener)
    {
        g_quesList.clear();
        g_firestore.collection("Questions")
                .whereEqualTo("CATEGORY", g_catList.get(g_selected_cat_index).getDocID())
                .whereEqualTo("TEST", g_testList.get(g_selectted_test_index).getTestID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            g_quesList.add(new QuestionsModel(
                                    doc.getString("QUESTION"),
                                    doc.getString("A"),
                                    doc.getString("B"),
                                    doc.getString("C"),
                                    doc.getString("D"),
                                    doc.getLong("ANSWER").intValue(),-1
                            ));
                        }
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    //Tạo dữ liệu người dùng mới trong Firestore.
    //Cập nhật thông tin người dùng gồm email, tên và tổng điểm (khởi tạo là 0).
    //Cập nhật tổng số người dùng trong bộ sưu tập "USERS".
    //Thông báo cho người gọi hàm về kết quả thành công hay thất bại.
    public static void createUserData(String email, String name,MyCompleteListener completeListener)
    {
        Map<String, Object> userData= new ArrayMap<>();
        userData.put("EMAIL_ID", email);//(key,value)
        userData.put("NAME",name);
        userData.put("TOTAL_SCORE",0);

        DocumentReference userDoc=g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        //tham chiếu đến tài liệu người dùng
        //.getInstance quản lý việc xác thực người dùng trong ứng dụng
        //.getCurrentUser lấy thông tin người dùng hiện đang được xác thực (đăng nhập)
        //FirebaseAuth quản lý việc xác thực người dùng
        //DocumentReference cung cấp các phương thức để truy cập, cập nhật và xóa tài liệu đó
        WriteBatch batch=g_firestore.batch();//cập nhật dữ liệu theo nhóm (batch)
        batch.set(userDoc, userData);//đặt dữ liệu userData vào userDoc
        DocumentReference countDoc=g_firestore.collection("USERS").document("TOTAL_USERS");
        //.collection truy cập một bộ sưu tập (collection) trong Cloud Firestore
        //.collection("USERS") tham chiếu đến collection "USERS" trong FireStore
        batch.update(countDoc, "COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();

                    }
                });
    }
    public static void getUserData(final MyCompleteListener completeListener)
    {
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                //document có ID tương ứng với UID của người dùng hiện tại
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot){
                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));

                        completeListener.onSuccess();
                   }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }
    //connect Category to Database
    public static void loadCategories(final MyCompleteListener completeListener)
    {
        g_catList.clear();

        g_firestore.collection("QUIZ").get()//truy vấn dữ liệu từ collection QUIZ
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        //QuerySnapshot tập hợp kết quả của một truy vấn
                        //QueryDocumentSnapshot một tài liệu riêng lẻ trong tập hợp kết quả của một truy vấn
                        Map<String, QueryDocumentSnapshot> docList=new ArrayMap<>();
                        for(QueryDocumentSnapshot doc:queryDocumentSnapshots)//queryDocumentSnapshots đại diện cho một tài liệu riêng lẻ được truy xuất từ cơ sở dữ liệu.
                        {
                            docList.put(doc.getId(), doc);
                        }
//                        lưu trữ các tài liệu (documents) từ một tập hợp kết quả truy vấn vào một
//                        cấu trúc dữ liệu dạng bảng băm (hash map) để truy cập chúng dễ dàng hơn dựa trên ID của chúng.


                        QueryDocumentSnapshot catListDoc=docList.get("Catergories");

                        long catCount=catListDoc.getLong("COUNT");

                        for(int i=1;i<=catCount;i++)
                        {
                            String catID= catListDoc.getString("CAT"+String.valueOf(i)+"_ID");
                            QueryDocumentSnapshot catDoc=docList.get(catID);
                            int noOfTest=catDoc.getLong("NO_OF_TESTS").intValue();
                            String catName=catDoc.getString("NAME");

                            g_catList.add(new CategoryModel(catID,catName,noOfTest));
                        }
                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    //tải dữ liệu testID và testTime cho từng cái catergory (môn học)
    public static void loadTestData(final MyCompleteListener completeListener)
    {
        g_testList.clear();
        g_firestore.collection("QUIZ").document(g_catList.get(g_selected_cat_index).getDocID())
                .collection("TESTS_LIST").document("TESTS_INFO")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        int noOfTests= g_catList.get(g_selected_cat_index).getNoOfTests();
                        for(int i=1;i<=noOfTests;i++)
                        {
                            g_testList.add(new TestModel(
                                    documentSnapshot.getString("TEST"+String.valueOf(i)+"_ID"),0,documentSnapshot.getLong("TEST"+String.valueOf(i)+"_TIME").intValue()
                            ));
                        }
                        completeListener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        completeListener.onFailure();
                    }
                });
    }

    public static void loadData(final MyCompleteListener completeListener)
    {
        loadCategories(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                getUserData(completeListener);
            }

            @Override
            public void onFailure() {
                completeListener.onFailure();
            }
        });
    }




}
