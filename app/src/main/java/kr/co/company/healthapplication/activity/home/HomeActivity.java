package kr.co.company.healthapplication.activity.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kr.co.company.healthapplication.ChatbotActivity;
import kr.co.company.healthapplication.R;
import kr.co.company.healthapplication.RunActivity;
import kr.co.company.healthapplication.request.AstepSelectRequest;
import kr.co.company.healthapplication.request.home.HomeSelectCheckListRequest;
import kr.co.company.healthapplication.request.home.UserAvailableStepRequest;
import kr.co.company.healthapplication.request.home.UserDonationStepRequest;
import kr.co.company.healthapplication.request.home.UserRunStepRequest;

// 2023-03-22 이수 작성.
public class HomeActivity extends Fragment {
    private ImageView ivRun;

    // 리사이클러뷰
    private ArrayList<HomeCheckListData> arrayList;
    private HomeCheckListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView noSchedule;
    private ImageButton btnChatbot;

    // 유저 정보
    private TextView tvAvilableStep, tvTodayWalk, tvTotalContributionStep;

    // SharedPreference
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public String userID, nowDate;

    private String aStep;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);

        initWidgets(rootView);
        
        userID = getUserID();
        nowDate = getNowDate();

        selectUserTodayRunStep(userID, nowDate);
        selectUserDonationStep(userID);

        selectAstep(userID);

        selectCheckList(nowDate);

        btnChatbot = rootView.findViewById(R.id.btnChatbot);
        btnChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatbotActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void initWidgets(ViewGroup rootView) {
        recyclerView = rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        mainAdapter = new HomeCheckListAdapter(arrayList, getActivity());
        recyclerView.setAdapter(mainAdapter);
        noSchedule = rootView.findViewById(R.id.noSchedule);
        tvAvilableStep = rootView.findViewById(R.id.tvAvilableStep);
        tvTodayWalk = rootView.findViewById(R.id.tvTodayWalk);
        tvTotalContributionStep = rootView.findViewById(R.id.tvTotalDonationStep);

        // 러닝 GIF
        ivRun = rootView.findViewById(R.id.ivRun);
        Glide.with(this).load(R.raw.run2).into(ivRun);
        ivRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RunActivity.class);
                startActivity(intent);
            }
        });
    }

    // 기부가능 걸음 조희
    private void selectAstep(String userID) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean check = jsonObject.getBoolean("success");

                    if (check == true) {
                        // 값 받아서 저장
                        aStep = jsonObject.getString("aStep");
                        // 값 세팅
                        tvAvilableStep.setText(aStep);

                    } else {
                        Toast.makeText(getActivity(), "이용자 정보를 확인하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        AstepSelectRequest astepSelectRequest = new AstepSelectRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(astepSelectRequest);
    }


    private String getNowDate() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return now.format(formatter);
    }

    private String getUserID() {
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();pref.getString("UserID", "_");
        return pref.getString("UserID", "_");
    }

    private void selectUserTodayRunStep(String userId, String nowDate) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    tvTodayWalk.setText(jsonObject.getString("runStep"));
                } catch (JSONException e) {
                Toast.makeText(getActivity(),"사용자의 정보가 없습니다. 관리자에게 문의 부탁드립니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        UserRunStepRequest userRunStepRequest = new UserRunStepRequest(userId, nowDate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(userRunStepRequest);
    }

    private void selectUserDonationStep(String userId) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    tvTotalContributionStep.setText(jsonObject.getString("totalDonationStep"));
                } catch (JSONException e) {
                    Toast.makeText(getActivity(),"사용자의 정보가 없습니다. 관리자에게 문의 부탁드립니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        UserDonationStepRequest userDonationStepRequest = new UserDonationStepRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(userDonationStepRequest);
    }

    private void selectCheckList(String nowDate) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 결과 값을 리턴받음.
                    JSONObject jsonObject;

                    if(jsonArray.length() == 0)
                        noSchedule.setVisibility(View.VISIBLE);
                    else
                        noSchedule.setVisibility(View.GONE);

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        int ListNum = jsonObject.getInt("ListNum");
                        int Checked = jsonObject.getInt("Checked");
                        String CheckContent = jsonObject.getString("CheckContent");
                        HomeCheckListData mainData;
                        if(Checked==0)
                            mainData = new HomeCheckListData(Checked, userID, ListNum+"", R.drawable.uncheckbox, CheckContent);
                        else
                            mainData = new HomeCheckListData(Checked, userID, ListNum+"", R.drawable.checkbox, CheckContent);
                        arrayList.add(mainData);
                    }
                    mainAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        HomeSelectCheckListRequest homeRequest = new HomeSelectCheckListRequest(userID, nowDate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(homeRequest);
    }
}