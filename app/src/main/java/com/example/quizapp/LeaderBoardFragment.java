package com.example.quizapp;

import static com.example.quizapp.model.DbQuery.g_usersCount;
import static com.example.quizapp.model.DbQuery.g_usersList;
import static com.example.quizapp.model.DbQuery.myPerformance;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.Adapter.RankAdapter;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderBoardFragment extends Fragment {

    private TextView txtTotalUsers , txtImg , txtScore , txtRank;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private Dialog progress_Dialog;
    private TextView dialogText;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("LeaderBoard");

        //initViews(View);


        progress_Dialog = new Dialog(getContext());
        progress_Dialog.setContentView(R.layout.dialog_layout);
        progress_Dialog.setCancelable(false);
        progress_Dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progress_Dialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progress_Dialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);


        //Part 32
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