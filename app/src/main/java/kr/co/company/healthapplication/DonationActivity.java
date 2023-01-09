package kr.co.company.healthapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 기부캠페인 액티비티 (2023-01-09 우진 수정)
public class DonationActivity extends Fragment {

    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private DonationAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationData> arrayList;

    private Button peopleBtn, environmentBtn, animalBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_donation,container,false);

        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDonationList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        adapter = new DonationAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        // list 값 세팅
        for(int i=1; i<=10; i++) {
            DonationData donationData = new DonationData("'따뜻한 플레이'"+i, "Google Play"+i, i+"", R.drawable.bear2);
            arrayList.add(donationData);
        }
        adapter.notifyDataSetChanged();

        peopleBtn = rootView.findViewById(R.id.peopleBtn);
        environmentBtn = rootView.findViewById(R.id.environmentBtn);
        animalBtn = rootView.findViewById(R.id.animalBtn);

        peopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               peopleBtn.setBackgroundResource(R.drawable.btnclickback);
               environmentBtn.setBackgroundResource(R.drawable.btnback);
               animalBtn.setBackgroundResource(R.drawable.btnback);

               peopleBtn.setTextColor(Color.WHITE);
               environmentBtn.setTextColor(Color.BLACK);
               animalBtn.setTextColor(Color.BLACK);
            }
        });

        environmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleBtn.setBackgroundResource(R.drawable.btnback);
                environmentBtn.setBackgroundResource(R.drawable.btnclickback);
                animalBtn.setBackgroundResource(R.drawable.btnback);

                peopleBtn.setTextColor(Color.BLACK);
                environmentBtn.setTextColor(Color.WHITE);
                animalBtn.setTextColor(Color.BLACK);
            }
        });

        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleBtn.setBackgroundResource(R.drawable.btnback);
                environmentBtn.setBackgroundResource(R.drawable.btnback);
                animalBtn.setBackgroundResource(R.drawable.btnclickback);

                peopleBtn.setTextColor(Color.BLACK);
                environmentBtn.setTextColor(Color.BLACK);
                animalBtn.setTextColor(Color.WHITE);
            }
        });

        return rootView;
    }


}