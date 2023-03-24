package kr.co.company.healthapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class RankActivity extends AppCompatActivity {

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

    private String category = "totalDonation";

    // SharedPreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String rememberID;

    // 내랭킹
    private TextView tvMyPoint;
    private TextView tvMyRank;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        // 구글 광고API
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // List 설정
        recyclerView = findViewById(R.id.rvRankList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new RankListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        // 랭크 메서드 호출
        rank(category);


        // 랭킹 버튼 클릭 이벤트
        tvRankTitle = findViewById(R.id.tvRankTitle);
        tvRankStep = findViewById(R.id.tvRankStep);

        btnDonaRank = findViewById(R.id.btnDonaRank);
        btnDonaRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 기부버튼 클릭 시
                tvRankTitle.setText("전체 기부 랭킹");
                tvRankStep.setText("코인");

                category = "totalDonation";
                rank(category);
            }
        });

        btnStepRank = findViewById(R.id.btnStepRank);
        btnStepRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 걸음버튼 클릭 시
                tvRankTitle.setText("전체 걷기 랭킹");
                tvRankStep.setText("걸음");

                category = "totalStep";
                rank(category);
            }
        });
    }

    private void rank(String category) {

        // 내 랭킹을 나타낼 TextView정의
        tvMyPoint = findViewById(R.id.tvMyPoint);
        tvMyRank = findViewById(R.id.tvMyRank);

        // 현재 로그인된 아이디
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        rememberID = pref.getString("UserID", "_");

        // 어댑터 초기화
        arrayList = new ArrayList<>();
        mainAdapter = new RankListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 결과 값을 리턴받음.
                    JSONObject jsonObject;

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("UserId");
                        String point = jsonObject.getString("point");

                        if(rememberID.equals(id)){
                            tvMyPoint.setText(point+"");
                            tvMyRank.setText((i+1)+"");
                        }
Log.d(">>>>>>>>>>>", rememberID + " " + id);
                        RankListData mainData = new RankListData((i+1)+"", R.drawable.user, id, point);
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
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(rankRequest);
    }
}