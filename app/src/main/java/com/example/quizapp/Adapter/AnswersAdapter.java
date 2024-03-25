package com.example.quizapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.R;
import com.example.quizapp.Adapter.TestAdapter;
import com.example.quizapp.model.QuestionsModel;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<QuestionsModel> questionsModelList;

    public AnswersAdapter(List<QuestionsModel> questionsModelList)
    {
        this.questionsModelList = questionsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout,parent,false);

        return new com.example.quizapp.Adapter.AnswersAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String ques = questionsModelList.get(position).getQuestion();
        String a = questionsModelList.get(position).getOptionA();
        String b = questionsModelList.get(position).getOptionB();
        String c = questionsModelList.get(position).getOptionC();
        String d = questionsModelList.get(position).getOptionD();

        int selected = questionsModelList.get(position).getSelectedAns();
        int ans = questionsModelList.get(position).getCorrectAns();


        holder.setData(position,ques,a,b,c,d,selected,ans);
    }

    @Override
    public int getItemCount() {
        return questionsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView quesNo,question,optionA , optionB, optionC , optionD,result;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quesNo=itemView.findViewById(R.id.quesNo);
            question=itemView.findViewById(R.id.question);
            optionA=itemView.findViewById(R.id.btnOptionA);
            optionB=itemView.findViewById(R.id.btnOptionB);
            optionC=itemView.findViewById(R.id.btnOptionC);
            optionD=itemView.findViewById(R.id.btnOptionD);
            result=itemView.findViewById(R.id.result);



        }
        private void setData(int pos,String ques , String a, String b , String c , String d,int selected , int correctAns)
        {
            quesNo.setText("Question No. "+String.valueOf(pos+1));
            question.setText(ques);
            optionA.setText("A. "+a);
            optionB.setText("B. "+b);
            optionC.setText("C. "+c);
            optionD.setText("D. "+d);

            if(selected==-1)
            {
                result.setText("UN-ANSWERED");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));

                setOptionColor(selected, R.color.text_normal);
            }

            else
            {
                if( selected == correctAns)
                {
                    result.setText("CORRECT");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                    setOptionColor(selected,R.color.green);

                }
                else
                {
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                    setOptionColor(selected,R.color.green);

                }
            }


        }
        private void setOptionColor(int selected , int color)
        {
           if(selected == 1)
           {
               optionA.setTextColor(itemView.getContext().getResources().getColor(color));
           }
           else
           {
               optionA.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
           }

            if(selected == 2)
            {
                optionB.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionB.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if(selected == 3)
            {
                optionC.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionC.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }

            if(selected == 4)
            {
                optionD.setTextColor(itemView.getContext().getResources().getColor(color));
            }
            else
            {
                optionD.setTextColor(itemView.getContext().getResources().getColor(R.color.text_normal));
            }
        }
    }
}
