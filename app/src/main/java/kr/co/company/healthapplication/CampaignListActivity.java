package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.company.healthapplication.dbAll.CampaignList;
import kr.co.company.healthapplication.request.DonationRequest;

// 캠페인 리스트 액티비티 (2023-04-06 우진)
public class CampaignListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private CampaignAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationData> arrayList;

    private Button peopleBtn, environmentBtn, animalBtn, campaignAddBtn;

    private Button btnMyinfo;

    private String category = "people";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_list);

        Intent receiveIntent = getIntent();
        final String userId = receiveIntent.getStringExtra("userID");

        // List 설정
        recyclerView = findViewById(R.id.rvDonationList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        adapter = new CampaignAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        btnMyinfo = findViewById(R.id.btnMyinfo);
        btnMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CampaignListActivity.this, OrganizMypageActivity.class);
                startActivity(intent);
            }
        });

        // 캠페인 메서드 호출
        donation(category);

        // 캠페인 버튼 클릭 이벤트
        peopleBtn = findViewById(R.id.peopleBtn);
        environmentBtn = findViewById(R.id.environmentBtn);
        animalBtn = findViewById(R.id.animalBtn);
        campaignAddBtn = findViewById(R.id.campaignAddBtn);

        peopleBtn.setBackgroundResource(R.drawable.btnclickback);
        environmentBtn.setBackgroundResource(R.drawable.btnback);
        animalBtn.setBackgroundResource(R.drawable.btnback);
        peopleBtn.setTextColor(Color.rgb(55, 85, 190));
        environmentBtn.setTextColor(Color.rgb(184, 184, 184));
        animalBtn.setTextColor(Color.rgb(184, 184, 184));

        peopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleBtn.setBackgroundResource(R.drawable.btnclickback);
                environmentBtn.setBackgroundResource(R.drawable.btnback);
                animalBtn.setBackgroundResource(R.drawable.btnback);

                peopleBtn.setTextColor(Color.rgb(55, 85, 190));
                environmentBtn.setTextColor(Color.rgb(184, 184, 184));
                animalBtn.setTextColor(Color.rgb(184, 184, 184));

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new CampaignAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                category = "people";
                donation(category);
            }
        });

        environmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleBtn.setBackgroundResource(R.drawable.btnback);
                environmentBtn.setBackgroundResource(R.drawable.btnclickback);
                animalBtn.setBackgroundResource(R.drawable.btnback);

                peopleBtn.setTextColor(Color.rgb(184, 184, 184));
                environmentBtn.setTextColor(Color.rgb(55, 85, 190));
                animalBtn.setTextColor(Color.rgb(184, 184, 184));

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new CampaignAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                category = "environment";
                donation(category);

            }
        });

        animalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleBtn.setBackgroundResource(R.drawable.btnback);
                environmentBtn.setBackgroundResource(R.drawable.btnback);
                animalBtn.setBackgroundResource(R.drawable.btnclickback);

                peopleBtn.setTextColor(Color.rgb(184, 184, 184));
                environmentBtn.setTextColor(Color.rgb(55, 85, 190));
                animalBtn.setTextColor(Color.rgb(132, 167, 228));

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new CampaignAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                category = "animal";
                donation(category);

            }
        });

        campaignAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(CampaignListActivity.this, CampaignWriterActivity.class);
                intent.putExtra("userID", userId);
                startActivity(intent);
            }
        });

    }

    private void donation(String category) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 불러오기 전에 데이터(아이템) 초기화 해줘야 중첩 안됨.
                adapter.notifyDataSetChanged();

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = (JSONObject) jsonArray.getJSONObject(i);
                        String titleName = jsonObject.getString("titleName");
                        String name = jsonObject.getString("name");
                        String nowStep = jsonObject.getString("nowStep");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        String startDate = jsonObject.getString("startDate");
                        String maxStep = jsonObject.getString("maxStep");
                        String dNum = jsonObject.getString("dNum");
                        String contentImage = jsonObject.getString("contentImage");

                        DonationData mainData = new DonationData(dNum, titleName, name, nowStep, contentImage, content, date, startDate, maxStep);
                        arrayList.add(mainData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DonationRequest donationRequest = new DonationRequest(category, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(donationRequest);
    }
}