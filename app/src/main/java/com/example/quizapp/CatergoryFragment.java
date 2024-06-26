package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.quizapp.Adapter.CategoryAdapter;
import com.example.quizapp.model.DbQuery;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CatergoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CatergoryFragment extends Fragment {
    //class này dùng để gán từng cardView vào từng gridView hoặc gắn vào CategoryFragment

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GridView catView;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CatergoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CatergoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CatergoryFragment newInstance(String param1, String param2) {
        CatergoryFragment fragment = new CatergoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_catergory, container, false);

        catView=view.findViewById(R.id.cat_Grid);//tham chiếu cat_Grid với catView
        //loadCategories();//tạo mới CategoryModel

        CategoryAdapter adapter=new CategoryAdapter(DbQuery.g_catList);//khởi tạo một CategoryAdapter(cardView) mới với tham số đầu vào là catList
        catView.setAdapter(adapter);//thiết lập adapter vào catView

        return view;
    }


}