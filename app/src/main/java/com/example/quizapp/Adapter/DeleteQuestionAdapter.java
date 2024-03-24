package com.example.quizapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.StartTestActivity;
import com.example.quizapp.UpdateQuestionActivity;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.MyCompleteListener;
import com.example.quizapp.model.QuestionsModel;

import java.util.List;

public class DeleteQuestionAdapter extends RecyclerView.Adapter<DeleteQuestionAdapter.ViewHolder> {
    public List<QuestionsModel> questionList;
    public DeleteQuestionAdapter(List<QuestionsModel> questionList){this.questionList=questionList;}
    @NonNull
    @Override
    public DeleteQuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout2,parent,false);
        return new DeleteQuestionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteQuestionAdapter.ViewHolder holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtQuestion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQuestion=itemView.findViewById(R.id.txtQuestion);
        }

        private void setData(final int pos)
        {
            txtQuestion.setText("Question "+String.valueOf(pos)+": "+questionList.get(pos).getQuestion());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbQuery.g_selectted_question_index=pos;
                    Intent intent = new Intent(itemView.getContext(), UpdateQuestionActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
