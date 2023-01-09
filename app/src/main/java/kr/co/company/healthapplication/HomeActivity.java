package kr.co.company.healthapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// 홈 액티비티 (2023-01-04 인범)
public class HomeActivity extends Fragment {

    private ImageView ivRun;

    // 리사이클러뷰
    private ArrayList<CheckListData> arrayList;
    private CheckListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 러닝
    private Button btnRun;


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);


        // 러닝 버튼
        btnRun = rootView.findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RunActivity.class);
                startActivity(intent);
            }
        });


        // 러닝 GIF
        ivRun = rootView.findViewById(R.id.ivRun);
        Glide.with(this).load(R.raw.run2).into(ivRun);


        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new CheckListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        // list값 세팅
        for(int i=1;i<=7;i++){
            CheckListData mainData = new CheckListData(R.drawable.uncheckbox,  "오늘의 체크리스트 "+i);
            arrayList.add(mainData);
        }

        mainAdapter.notifyDataSetChanged();

        return rootView;
    }


}