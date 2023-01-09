package kr.co.company.healthapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.company.healthapplication.request.RankRequest;

// 랭크 액티비티 (2023-01-10 인범 수정)
public class RankActivity extends Fragment {

    // 구글 광고
    private AdView mAdView;

    // 리사이클러뷰
    private ArrayList<RankListData> arrayList;
    private RankListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 랭킹 버튼
    private Button btnDonaRank, btnStepRank;
    private TextView tvRankTitle, tvRankStep;


    private String category = "TotalUserDonation";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_rank, container, false);

        // 구글 광고API
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvRankList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new RankListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        // 랭크 메서드 호출
        rank(category);


        // 랭킹 버튼 클릭 이벤트
        tvRankTitle = rootView.findViewById(R.id.tvRankTitle);
        tvRankStep = rootView.findViewById(R.id.tvRankStep);

        btnDonaRank = rootView.findViewById(R.id.btnDonaRank);
        btnDonaRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 기부버튼 클릭 시
                tvRankTitle.setText("기부 랭킹");
                tvRankStep.setText("코인");

                category = "TotalUserDonation";
                rank(category);
            }
        });

        btnStepRank = rootView.findViewById(R.id.btnStepRank);
        btnStepRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 걸음버튼 클릭 시
                tvRankTitle.setText("걷기 랭킹");
                tvRankStep.setText("걸음");

                category = "TotalUserStep";
                rank(category);
            }
        });

        return rootView;
    }

    private void rank(String category) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 결과 값을 리턴받음.
                    JSONObject jsonObject;

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        String id = jsonObject.getString("UserId");
                        int point = jsonObject.getInt("point");

                        RankListData mainData = new RankListData((i+1)+"", R.drawable.user, id, point+"");
                        arrayList.add(mainData);
                    }

                    // 불러오기 전에 데이터(아이템) 초기화 해줘야 중첩 안됨.

                    mainAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RankRequest rankRequest = new RankRequest(category, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(rankRequest);
    }

}