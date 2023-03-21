package kr.co.company.healthapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import kr.co.company.healthapplication.request.UserInfoSelectRequest;
import kr.co.company.healthapplication.request.UserSettingUpdateRequest;

/* 신체정보 변경(키, 체중 등등), 회원정보 변경, 기부 신청내역 */

public class MypageActivity extends Fragment {
    private TextView tvUserSetting, tvUserID, tvUserName, tvUserAvailableStep;
    private TextView tvUserInfoSetting, tvUserHeight, tvUserWeight, tvUserAge, tvUserBMI;
    private TextView tvUserDonation;
    private Button btnDonationReceipts ,btnRank ,btnLogOut;

    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId;
    private Boolean stateUserTable;

    private String rId, rName, rBirth;
    private int rHeight, rWeight, rAvailableStep, totalDonationStep;

    private LinearLayout layoutBodyinfoUpdate;
    private Button btnUpdateInfo, btnCancelInfo;
    private EditText etHeight, etWeight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.activity_mypage,container,false);
        tvUserID = view.findViewById(R.id.tvUserID);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserAvailableStep = view.findViewById(R.id.tvUserDStep);
        tvUserHeight = view.findViewById(R.id.tvUserHeight);
        tvUserWeight = view.findViewById(R.id.tvUserWeight);
        tvUserSetting = view.findViewById(R.id.tvUserSetting);
        tvUserAge = view.findViewById(R.id.tvUserAge);
        tvUserBMI = view.findViewById(R.id.tvUserBMI);
        tvUserDonation = view.findViewById(R.id.tvUserDonation);
        btnDonationReceipts = view.findViewById(R.id.btnDonationReceipts);
        btnRank = view.findViewById(R.id.btnRank);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);
        btnCancelInfo = view.findViewById(R.id.btnCancelInfo);
        layoutBodyinfoUpdate = view.findViewById(R.id.layoutBodyinfoUpdate);
        etHeight = view.findViewById(R.id.etHeight);
        etWeight = view.findViewById(R.id.etWeight);

        // 이용자 정보 가져오기.
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        selectUserInfo();   // DB에서 정보를 가져오는 메소드.

        // 신체정보 수정 [변경버튼]
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String h = String.valueOf(etHeight.getText());
                String w = String.valueOf(etWeight.getText());

                // DB에서 업데이트 시킴
                updateUserInfo(userId, h, w);
                layoutBodyinfoUpdate.setVisibility(View.INVISIBLE);
            }
        });
        // 신체정보 수정 [취소버튼]
        btnCancelInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBodyinfoUpdate.setVisibility(View.INVISIBLE);
            }
        });


        tvUserSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 신체정보 수정
                layoutBodyinfoUpdate.setVisibility(View.VISIBLE);

                etHeight.setText(tvUserHeight.getText());
                etWeight.setText(tvUserWeight.getText());
            }
        });

        btnDonationReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DonationReceiptsActivity.class);
                startActivity(intent);
            }
        });

        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RankActivity2.class);
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
        // 유저 정보 가져오기.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    stateUserTable = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    if (stateUserTable == true) {
                        rId = jsonObject.getString("id");
                        rName = jsonObject.getString("name");
                        rBirth = jsonObject.getString("birth");

                        rHeight = jsonObject.getInt("height");
                        rWeight = jsonObject.getInt("weight");
                        rAvailableStep = jsonObject.getInt("availableStep");

                        totalDonationStep = jsonObject.getInt("totalDonationStep");

                        tvUserID.setText(rId);
                        tvUserName.setText(rName);
                        tvUserAvailableStep.setText(Integer.toString(rAvailableStep));
                        tvUserHeight.setText(rHeight);
                        tvUserWeight.setText(rWeight);

                        tvUserDonation.setText(String.valueOf(totalDonationStep));

                        // 생일 (birth) 계산식 => 현재년도 - 출생년도
                        LocalDate now = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
                        String formatedNow = now.format(formatter);

                        String formatedBirth = rBirth.substring(0, 4);

                        String age = String.valueOf(Integer.parseInt(formatedNow) - Integer.parseInt(formatedBirth));
                        tvUserAge.setText(age + "세");

                        // BMI 계산
                        double bmi = rWeight / ((rHeight*0.01)*(rHeight*0.01));

                        // BMI가 18.5 이하면 저체중 ／ 18.5 ~ 22.9 사이면 정상 ／ 23.0 ~ 24.9 사이면 과체중 ／ 25.0 이상부터는 비만으로 판정.
                        String verdict;
                        if(bmi <= 18.5) verdict = "저체중";
                        else if(bmi <= 22.9) verdict = "정상";
                        else if(bmi <= 24.9) verdict = "과체중";
                        else verdict = "비만";

                        tvUserBMI.setText(String.valueOf(bmi).substring(0, 4)+"("+verdict+")");



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

    // 신체정보 업데이트
    private void updateUserInfo(String userId, String settingHeight, String settingWeight) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);
                    Toast.makeText(getActivity(), "변경 완료", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "변경 실패", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        UserSettingUpdateRequest userSettingUpdateRequest = new UserSettingUpdateRequest(userId, settingHeight, settingWeight, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(userSettingUpdateRequest);
    }

}