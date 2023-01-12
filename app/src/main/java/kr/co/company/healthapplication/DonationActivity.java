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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import kr.co.company.healthapplication.request.DonationRequest;
import kr.co.company.healthapplication.request.RankRequest;

// 기부캠페인 액티비티 (2023-01-11 우진 수정)
public class DonationActivity extends Fragment {

    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private DonationAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationData> arrayList;

    private Button peopleBtn, environmentBtn, animalBtn;

    private String category = "people";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_donation, container, false);

        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDonationList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        adapter = new DonationAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        // 캠페인 메서드 호출
        donation(category);

        // 캠페인 버튼 클릭 이벤트
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

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new DonationAdapter(arrayList);
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

                peopleBtn.setTextColor(Color.BLACK);
                environmentBtn.setTextColor(Color.WHITE);
                animalBtn.setTextColor(Color.BLACK);

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new DonationAdapter(arrayList);
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

                peopleBtn.setTextColor(Color.BLACK);
                environmentBtn.setTextColor(Color.BLACK);
                animalBtn.setTextColor(Color.WHITE);

                // 데이터 초기화 시켜주는 과정
                arrayList = new ArrayList<>();
                adapter = new DonationAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                category = "animal";
                donation(category);

            }
        });

        return rootView;
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
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        String titleName = jsonObject.getString("titleName");
                        String name = jsonObject.getString("name");
                        String nowStep = jsonObject.getString("nowStep");
                        String content = jsonObject.getString("content");
                        String date = jsonObject.getString("date");
                        String maxStep = jsonObject.getString("maxStep");
                        String dNum = jsonObject.getString("dNum");

                        DonationData mainData = new DonationData(dNum, titleName, name, nowStep, R.drawable.bear2, content, date, maxStep);
                        arrayList.add(mainData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DonationRequest donationRequest = new DonationRequest(category, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(donationRequest);
    }
}