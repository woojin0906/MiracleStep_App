package kr.co.company.healthapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

// 홈 액티비티 (2023-01-04 인범)
public class HomeActivity extends Fragment {

    private AdView mAdView; // 구글 광고
    private ImageView ivRun;

    // 리사이클러뷰
    private ArrayList<CheckListData> arrayList;
    private CheckListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);

        // 구글 광고API
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


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