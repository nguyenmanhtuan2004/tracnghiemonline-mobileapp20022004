package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_usersCount;
import static com.example.quizapp.model.DbQuery.g_usersList;
import static com.example.quizapp.model.DbQuery.myPerformance;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.Adapter.RankAdapter;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderBoardFragment extends Fragment {

    private TextView txtTotalUsers , txtImg , txtScore , txtRank;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private Dialog progress_Dialog;
    private TextView dialogText;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_leader_board, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        initViews(view);

        progress_Dialog=new Dialog(getContext());//khởi tạo hộp thoại
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);//người dùng không thể hủy hộp thoại này
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //thiết lập kích thước của cửa sổ dialog thành kích thước nhỏ nhất có thể để vừa với nội dung bên trong của nó
        dialogText= progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progress_Dialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        usersView.setLayoutManager(layoutManager);
        adapter = new RankAdapter(DbQuery.g_usersList);
        usersView.setAdapter(adapter);

        DbQuery.getTopUsers(new MyCompleteListener() {
            @Override
            public void onSuccess() {
                adapter.notifyDataSetChanged();
                if(myPerformance.getScore() != 0)
                {
                    if(!DbQuery.isMeOnTopList)
                    {
                        calculateRank();
                    }
                    txtScore.setText("Score : " + myPerformance.getScore());
                    txtRank.setText("Rank - " + myPerformance.getRank());
                }

                progress_Dialog.dismiss();
            }

            @Override
            public void onFailure() {

                Toast.makeText(getContext(), "Có gì đó sai! Vui lòng thử lại",
                        Toast.LENGTH_SHORT).show();
                progress_Dialog.dismiss();
            }
        });
        txtTotalUsers.setText("Total Users : " + DbQuery.g_usersCount);
        txtImg.setText(myPerformance.getName().toUpperCase().substring(0,1));

        return view;
    }

    private void initViews(View view) {
        txtTotalUsers = view.findViewById(R.id.txtTotalUsers);
        txtImg = view.findViewById(R.id.txtImg);
        txtScore = view.findViewById(R.id.txtTotalScore);
        txtRank = view.findViewById(R.id.txtRank);
        usersView = view.findViewById(R.id.usersView);
    }

    private void calculateRank()
    {
        int lowTopScore = g_usersList.get(g_usersList.size() - 1).getScore();
        int remaining_slots = g_usersCount - 20;
        int myslot = (myPerformance.getScore()*remaining_slots) / lowTopScore;
        int rank;
        if(lowTopScore != myPerformance.getScore())
        {
            rank = g_usersCount - myslot;
        }
        else
            rank = 21;
        myPerformance.setRank(rank);
    }
}