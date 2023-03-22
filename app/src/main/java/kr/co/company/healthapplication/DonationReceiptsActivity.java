package kr.co.company.healthapplication;
// 기부내역 액티비티 (2023-03-22 우진 수정)
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.company.healthapplication.request.DonationRecordRequest;
import kr.co.company.healthapplication.request.DonationRequest;

public class DonationReceiptsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private DonationReceiptsAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationReceiptsData> arrayList;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_receipts);

        // List 설정
        recyclerView = (RecyclerView) findViewById(R.id.rvDonationReceiptsList);
        linearLayoutManager = new LinearLayoutManager(new DonationReceiptsActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new DonationReceiptsAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        setDonationRecord(userId);
    }

    private void setDonationRecord(String userId) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 불러오기 전에 데이터(아이템) 초기화 해줘야 중첩 안됨.
                adapter.notifyDataSetChanged();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = (JSONObject)jsonArray.opt(i);

                        String titleName = jsonObject.getString("titleName");
                        String groupName = jsonObject.getString("groupName");
                        String donationDate = jsonObject.getString("donationDate");
                        String donationStep = jsonObject.getString("donationStep");
                        //String donationImg = jsonObject.getString("DonationImg");
                        //String donationImg = "img";

                        DonationReceiptsData donationData = new DonationReceiptsData(titleName, groupName, donationDate , Integer.parseInt(donationStep), R.drawable.bear2);
                        arrayList.add(donationData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DonationRecordRequest donationRequest = new DonationRecordRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DonationReceiptsActivity.this);
        queue.add(donationRequest);
    }
}