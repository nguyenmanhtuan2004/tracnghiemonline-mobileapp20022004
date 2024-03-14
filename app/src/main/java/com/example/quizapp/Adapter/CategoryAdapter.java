package com.example.quizapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quizapp.R;
import com.example.quizapp.TestActivity;
import com.example.quizapp.model.CategoryModel;
import com.example.quizapp.model.DbQuery;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
//Class CategoryAdapter dùng để hiện thực CategoryModel vào cat_item_layout(cardView)
    private List<CategoryModel> cat_list;

    public CategoryAdapter(List<CategoryModel> cat_list) {
        this.cat_list = cat_list;
    }

    @Override
    public int getCount() {
        return cat_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView( int position, View view, ViewGroup viewGroup) {
        View myView;

        if(view==null)
        {
            myView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item_layout,viewGroup,false);
        }
        else
        {
            myView=view;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DbQuery.g_selected_cat_index=position;

                Intent intent=new Intent(view.getContext(), TestActivity.class);
                //khởi động Activity TestActivity từ Activity hoặc Fragment chứa View (TextView) mà bạn đang thao tác.

                view.getContext().startActivity(intent);
                //view.getContext() là phương thức được sử dụng để lấy ra Context của View. Context là một đối tượng cung cấp quyền truy cập vào các tài nguyên và dịch vụ của hệ thống Android
            }
        });
        TextView catName = myView.findViewById(R.id.catName);
        TextView noOfTests = myView.findViewById(R.id.no_of_tests);

        catName.setText(cat_list.get(position).getName());
        noOfTests.setText(String.valueOf(cat_list.get(position).getNoOfTests()+ " Tests"));
        return myView;
    }
}
