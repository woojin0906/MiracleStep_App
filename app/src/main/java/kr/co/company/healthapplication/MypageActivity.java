package kr.co.company.healthapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import kr.co.company.healthapplication.request.UserInfoSelectRequest;

/* 신체정보 변경(키, 체중 등등), 회원정보 변경, 기부 신청내역 */

public class MypageActivity extends Fragment {
    private TextView tvUserSetting, tvUserID, tvUserName, tvUserDStep;
    private TextView tvUserInfoSetting, tvUserHeight, tvUserWeight, tvUserAge, tvUserBMI;
    private TextView tvUserDonation;
    private Button btnDonationReceipts ,btnLogOut;

    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId;
    private Boolean stateUserTable;

    private String returnID;
    private String returnUserName;
    private int returnUserDStep;
    private String returnUserImg;
    private double height;
    private double weight;
    private String birth;
    private int totalUserDonation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.activity_mypage,container,false);
        tvUserSetting = view.findViewById(R.id.tvUserSetting);
        tvUserID = view.findViewById(R.id.tvUserID);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserDStep = view.findViewById(R.id.tvUserDStep);
        tvUserHeight = view.findViewById(R.id.tvUserHeight);
        tvUserWeight = view.findViewById(R.id.tvUserWeight);
        tvUserSetting = view.findViewById(R.id.tvUserSetting);
        tvUserAge = view.findViewById(R.id.tvUserAge);
        tvUserBMI = view.findViewById(R.id.tvUserBMI);
        tvUserDonation = view.findViewById(R.id.tvUserDonation);
        btnDonationReceipts = view.findViewById(R.id.btnDonationReceipts);
        btnLogOut = view.findViewById(R.id.btnLogOut);

        // 이용자 정보 가져오기.
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        selectUserInfo();   // DB에서 정보를 가져오는 메소드.

        tvUserDonation.setText(Integer.toString(totalUserDonation));

        tvUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserInfoSettingActivity.class);
                startActivity(intent);
                //getActivity().finish();
            }
        });

        btnDonationReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonationReceiptsActivity.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = pref.edit();
                editor.remove("UserID").apply();
                Toast.makeText(getActivity().getApplicationContext(),"로그아웃에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    private void selectUserInfo() {
        // 유저의 정보 가져오기. (2023-01-10 이수)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    stateUserTable = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    if (stateUserTable == true) {
                        returnID = jsonObject.getString("userID");
                        returnUserName = jsonObject.getString("userName");
                        returnUserDStep = jsonObject.optInt("userDStep", 0);
                        returnUserImg = jsonObject.getString("userImg");

                        height = jsonObject.optDouble("height", 0.0);
                        weight = jsonObject.optDouble("weight", 0.0);
                        birth = jsonObject.getString("birth");

                        totalUserDonation = jsonObject.optInt("totalUserDonation", 0);

                        tvUserID.setText(returnID);
                        tvUserName.setText(returnUserName);
                        tvUserDStep.setText(Integer.toString(returnUserDStep));
                        // 1. 이미지... (2023-01-10 이수)

                        tvUserHeight.setText(Double.toString(height));
                        tvUserWeight.setText(Double.toString(weight));

                        // 2. 나이 변환해야 함. (2023-01-10 이수)
                        tvUserAge.setText(birth);

                        // 3. BMI 계산해야 함. (2023-01-10 이수)
                        //tvUserBMI.setText();

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
        UserInfoSelectRequest userSelectRequest = new UserInfoSelectRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(userSelectRequest);
    }
}