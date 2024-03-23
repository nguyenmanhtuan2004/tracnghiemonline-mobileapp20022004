package com.example.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.QuestionsModel;

import java.util.List;

public class DeleteQuestionAdapter extends RecyclerView.Adapter<DeleteQuestionAdapter.ViewHolder> {
    private List<QuestionsModel> questionList;
    private DeleteQuestionAdapter(List<QuestionsModel> questionList){this.questionList=questionList;}
    @NonNull
    @Override
    public DeleteQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout,parent,false);
        return new DeleteQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteQuestionAdapter.ViewHolder holder, int position) {
//        DbQuery.g_selectted_question_index
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
