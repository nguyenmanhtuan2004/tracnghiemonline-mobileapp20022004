package com.example.quizapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizapp.QuestionActivity;
import com.example.quizapp.R;
import com.example.quizapp.StartTestActivity;
import com.example.quizapp.model.DbQuery;
import com.example.quizapp.model.TestModel;

import java.util.List;

//ViewHolder là trình bao bọc xung quanh một View chứa bố cục cho từng mục riêng lẻ trong danh sách
//Adapter sẽ tạo các đối tượng ViewHolder nếu cần, đồng thời đặt dữ liệu cho các thành phần hiển thị đó

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    private List<TestModel> testList;

    public TestAdapter(List<TestModel> testList) {
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // tạo ra một ViewHolder mới mỗi khi cần hiển thị một item trong RecyclerView.
        //ViewHolder lưu trữ các view của item (như ImageView, TextView, ...) và giúp hiển thị dữ liệu lên item một cách hiệu quả.
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout,parent,false);
        return new  ViewHolder(view);
    }
    //phương thức này tạo và khởi động ViewHolder cùng với View(test_item_layout) đã liên kết-ViewHoler chưa liên kêt dữ liệu

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {

        int progress=testList.get(position).getTopScore();
        //lấy điểm theo position và gắn vào progress
        holder.setData(position,progress);
    }
    //onBindViewHolder liên kết ViewHolder với dữ liệu

    @Override
    public int getItemCount() {
        return testList.size();
    }
    //trả về danh sách có bao nhiêu item


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView testNo;

        private TextView topScore;

        private ProgressBar progressBar;//thanh tiến trình

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemView: Tham chiếu đến View được truyền vào constructor của ViewHolder.
            testNo =itemView.findViewById(R.id.testNo);
            //giúp ViewHolder có thể được sử dụng với nhiều layout khác nhau.
            //nếu bạn sử dụng testNo = itemView.findViewById(R.id.testNo);, bạn chỉ cần tạo một ViewHolder duy nhất:
            //
            //ViewHolder cho cả layout_1.xml và layout_2.xml
            topScore=itemView.findViewById(R.id.scoretext);
            progressBar=itemView.findViewById(R.id.testProgressbar);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), QuestionActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        private void setData(final int pos, int progress)
        {
            //progress ở đây là điểm lấy theo position
            testNo.setText("Test No :"+ String.valueOf(pos+1));
            topScore.setText(String.valueOf(progress)+ "%");

            progressBar.setProgress(progress);

            itemView.setOnClickListener(view -> {
                DbQuery.g_selectted_test_index = pos;
                Intent intent = new Intent(itemView.getContext(), StartTestActivity.class);
                itemView.getContext().startActivity(intent);
            } );
        }
    }
}
