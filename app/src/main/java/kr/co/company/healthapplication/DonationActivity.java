package kr.co.company.healthapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;

public class DonationActivity extends Fragment {
    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private RecyclerView.Adapter adapter;                               // 리사이클러뷰 어댑터
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DonationInfo> arrayList;

    private ImageButton homeBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_donation,container,false);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_donation);
//
//        recyclerView = findViewById(R.id.recyclerView);     // 아이디 연결
//        recyclerView.setHasFixedSize(true);                 // 리사이클러뷰 기존성능 강화
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);   // DonationInfo 객체를 담을 어레이 리스트 (어댑터쪽으로 데이터 전송)
//        arrayList = new ArrayList<>();
//
//        // 데이터 가져와야 함
//
//        adapter = new DonationAdapter(arrayList, this);
//        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결
//
//
//        homeBtn = findViewById(R.id.homeBtn);
//        // homeBtn 클릭 시 HomeActivity로 이동
//        homeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DonationActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}