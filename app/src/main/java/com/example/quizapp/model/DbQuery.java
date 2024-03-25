package com.example.quizapp.model;

import android.util.ArrayMap;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DbQuery {
    public static int g_selected_cat_index = 0;

    public static String RANDOMID_QUESTION;
    public static FirebaseFirestore g_firestore;
    //fireStore cung cấp các phương thức để tương tác các dữ liệu được lưu trữ trong FireStore:đọc, ghi, xóa
    public static List<CategoryModel> g_catList =new ArrayList<CategoryModel>();
    //Part 17
    public static int g_selectted_test_index = 0;
    public static int g_selectted_question_index = 0;
    public static List<QuestionsModel> g_quesList = new ArrayList<>();
    //end part 17
    public static ProfileModel myProfile = new ProfileModel("NA",null, null);//(name,email)
    public static List<RankModel> g_usersList = new ArrayList<>();
    public static boolean isMeOnTopList = false;
    public  static int g_usersCount = 0;


    public static RankModel myPerformance = new RankModel("NULL", 0,-1);
    public static final int NOT_VISITED = 0;
    public static final int UNANSWERED = 1;
    public static final int ANSWERED = 2;
    public static final int REVIEW = 3;
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
                                    doc.getLong("ANSWER").intValue(),
                                    doc.getLong("ANSWER2").intValue(),
                                    -1,-1,
                                    doc.getString("RANDOMID"),NOT_VISITED

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
    public static void updateQuestionData(String optionA,String optionB,String optionC,String optionD,String question,int answer,int answer2,MyCompleteListener completeListener)
    {
        Map<String, Object> questionDataUpdate= new ArrayMap<>();
        questionDataUpdate.put("A",optionA);
        questionDataUpdate.put("B",optionB);
        questionDataUpdate.put("C",optionC);
        questionDataUpdate.put("D",optionD);
        questionDataUpdate.put("QUESTION",question);
        questionDataUpdate.put("ANSWER",answer);
        questionDataUpdate.put("ANSWER2",answer2);
       g_firestore.collection("Questions")
                .document(String.valueOf(g_quesList.get(g_selectted_question_index).getRANDOMID().toString()))
               .update(questionDataUpdate)
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
    public static void deleteQuestionData(MyCompleteListener completeListener)
    {

        DocumentReference questionsDelete=g_firestore.collection("Questions")
                .document(String.valueOf(g_quesList.get(g_selectted_question_index).getRANDOMID().toString()));
        WriteBatch batch=g_firestore.batch();
        batch.delete(questionsDelete);
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
    public static void createQuestionData(String optionA,
                                          String optionB,
                                          String optionC,
                                          String optionD,
                                          String question,
                                          int answer,
                                          int answer2,
                                          MyCompleteListener completeListener)
    {
        String randomID=UUID.randomUUID().toString();
        Map<String, Object> questionData= new ArrayMap<>();
        questionData.put("A",optionA);
        questionData.put("B",optionB);
        questionData.put("C",optionC);
        questionData.put("D",optionD);
        questionData.put("QUESTION",question);
        questionData.put("ANSWER",answer);
        questionData.put("ANSWER2",answer2);
        questionData.put("CATEGORY",g_catList.get(g_selected_cat_index).getDocID());
        questionData.put("TEST",g_testList.get(g_selectted_test_index).getTestID());
        questionData.put("RANDOMID",randomID);


        DocumentReference questionsDoc=g_firestore.collection("Questions").document(randomID);
        WriteBatch batch=g_firestore.batch();//cập nhật dữ liệu theo nhóm (batch)
        batch.set(questionsDoc, questionData);

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
    public static void saveSetTime(String setDate,int time, MyCompleteListener completeListener)
    {
        Map<String, Object> saveTimeData = new ArrayMap<>();
        saveTimeData.put("TEST"+String.valueOf(g_selectted_test_index+1)+"_START",setDate);
        saveTimeData.put("TEST"+String.valueOf(g_selectted_test_index+1)+"_TIME",time);

        g_firestore.collection("QUIZ").document(g_catList.get(g_selected_cat_index).getDocID())
                .collection("TESTS_LIST").document("TESTS_INFO")
                .update(saveTimeData)
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

    public static void saveProfileData(String name, String phone, MyCompleteListener completeListener)
    {
        Map<String, Object> profileData = new ArrayMap<>();
        profileData.put("NAME", name);
        if (phone != null)
            profileData.put("PHONE", phone);

        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .update(profileData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        myProfile.setName(name);

                        if(phone != null)
                            myProfile.setPhone(phone);
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
        //document có ID tương ứng với UID của người dùng hiện tại
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>(){
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot){
                        myProfile.setName(documentSnapshot.getString("NAME"));
                        myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));

                       if (documentSnapshot.getString("VAITRO") != null)
                           myProfile.setVaitro(String.valueOf(documentSnapshot.getString("VAITRO")));

                        if (documentSnapshot.getString("PHONE") != null)
                            myProfile.setPhone(documentSnapshot.getString("PHONE"));

                        myPerformance.setScore(documentSnapshot.getLong("TOTAL_SCORE").intValue());
                        myPerformance.setName(documentSnapshot.getString("NAME"));

                        myPerformance.setName(documentSnapshot.getString("NAME"));
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


    public static void getTopUsers(MyCompleteListener completeListener)
    {
        g_usersList.clear();
        String myUID = FirebaseAuth.getInstance().getUid();
        g_firestore.collection("USERS")
                .whereGreaterThan("TOTAL_SCORE", 0)
                .orderBy("TOTAL_SCORE", Query.Direction.DESCENDING)
                .limit(20)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        int rank = 1;
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            g_usersList.add(new RankModel(
                                    doc.getString("NAME"),
                                    doc.getLong("TOTAL_SCORE").intValue(),
                                    rank
                            ));

                            if(myUID.compareTo(doc.getId()) == 0)
                            {
                                isMeOnTopList = true;
                                myPerformance.setRank(rank);
                            }

                            rank++;
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


    public static void loadMyProfile(TextView username,TextView profileimage,final MyCompleteListener completeListener)
    {
        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String name=documentSnapshot.getString("NAME");
                        profileimage.setText(name.toUpperCase().substring(0,1));
                        username.setText(name);
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

    public static void getUsersCount(MyCompleteListener completeListener)
    {
        g_firestore.collection("USERS").document("TOTAL_USERS")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        g_usersCount = documentSnapshot.getLong("COUNT").intValue();
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
    public static void loadMyScores(MyCompleteListener completeListener)
    {

        g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).
                collection("USER_DATA").
                document("MY_SCORES").
                get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        for (int i = 0; i < g_testList.size(); i++)
                        {
                            int top = 0;
                            if (documentSnapshot.get(g_testList.get(i).getTestID()) != null)
                            {
                                top = documentSnapshot.getLong(g_testList.get(i).getTestID()).intValue();

                            }

                            g_testList.get(i).setTopScore(top);
                        }

                        completeListener.onSuccess();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        completeListener.onFailure();
                    }
                });
    }

    public static void saveResult(int score, MyCompleteListener completeListener){
        WriteBatch batch = g_firestore.batch();

        DocumentReference userDoc = g_firestore.collection("USERS").document(FirebaseAuth.getInstance().getUid());

        batch.update(userDoc, "TOTAL_SCORE", score);

        if (score > g_testList.get(g_selectted_test_index).getTopScore())
        {
            DocumentReference scoreDoc = userDoc.collection("USER_DATA").document("MY_SCORES");

            Map<String, Object> testData = new ArrayMap<>();
            testData.put(g_testList.get(g_selectted_test_index).getTestID(), score);

            batch.set(scoreDoc,testData, SetOptions.merge());
        }

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                if (score > g_testList.get(g_selectted_test_index).getTopScore())
                    g_testList.get(g_selectted_test_index).setTopScore(score);

                myPerformance.setScore(score);

                completeListener.onSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
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
                                    documentSnapshot.getString("TEST"+String.valueOf(i)+"_ID"),1,
                                    documentSnapshot.getLong("TEST"+String.valueOf(i)+"_TIME").intValue(),
                                    documentSnapshot.getString("TEST"+String.valueOf(i)+"_START")
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
                getUserData(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        getUsersCount(completeListener);
                    }

                    @Override
                    public void onFailure() {
                        completeListener.onFailure();
                    }
                });
            }

            @Override
            public void onFailure() {
                completeListener.onFailure();
            }
        });
    }




}
