package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.RunSelectRequest;
import kr.co.company.healthapplication.request.RunUpdateRequest;
import kr.co.company.healthapplication.request.UserInfoSelectRequest;
import kr.co.company.healthapplication.request.UserSettingSelectRequest;
import kr.co.company.healthapplication.request.UserSettingUpdateRequest;

public class UserInfoSettingActivity extends AppCompatActivity {
    private ImageView ivUser;
    private EditText etId, etName, etPhoneNumber, etHeight, etWeight;
    private double height, weight;
    private Button btnSetting;

    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId, returnUserID, returnUserName, returnUserPhone, returnUserImg;

    private Boolean stateUserTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);

        ivUser = findViewById(R.id.ivUser);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        // 이용자 정보 가져오기.
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        selectUserInfo(userId);

        btnSetting = findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String ivUser = ivUser.getText().toString(); 이미지
                String settingId = etId.getText().toString();
                String settingName = etName.getText().toString();
                String settingPhoneNumber = etPhoneNumber.getText().toString();
                String settingHeight = etHeight.getText().toString();
                String settingWeight = etWeight.getText().toString();

                updateUserInfo(returnUserImg, settingId, settingName, settingPhoneNumber, settingHeight, settingWeight, returnUserID);
            }
        });
    }

    private void updateUserInfo(String settingUserPhoto, String settingId, String settingName, String settingPhoneNumber, String settingHeight, String settingWeight, String returnUserID) {
        //3. 기록 DB에 저장
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    if (success) {
                        Toast.makeText(getApplicationContext(), "이용자의 정보가 변경되었습니다.", Toast.LENGTH_SHORT).show();
                        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        editor = pref.edit();
                        editor.putString("UserID", settingId);
                        editor.apply();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "이용자 정보 변경이 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserInfoSettingActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP); //액티비티 스택제거
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        UserSettingUpdateRequest userSettingUpdateRequest = new UserSettingUpdateRequest(returnUserID, settingUserPhoto, settingId, settingName, settingPhoneNumber, settingHeight, settingWeight ,responseListener );
        RequestQueue queue = Volley.newRequestQueue(UserInfoSettingActivity.this);
        queue.add(userSettingUpdateRequest);
    }

    private void selectUserInfo(String userId) {
        // 유저의 정보 가져오기. (2023-01-10 이수)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    stateUserTable = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    if (stateUserTable == true) {
                        returnUserID = jsonObject.getString("UserID");
                        returnUserName = jsonObject.getString("UserName");
                        returnUserPhone = jsonObject.getString("UserPhone");
                        returnUserImg = jsonObject.getString("UserImg");

                        height = jsonObject.optDouble("UserHeight", 0.0);
                        weight = jsonObject.optDouble("UserWeight", 0.0);

                        //ivUser
                        etId.setText(returnUserID);
                        etName.setText(returnUserName);
                        etPhoneNumber.setText(returnUserPhone);
                        etHeight.setText(String.valueOf(height));
                        etWeight.setText(String.valueOf(weight));

                    } else {
                        Toast.makeText(new UserInfoSettingActivity(), "이용자 정보를 확인하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(new UserInfoSettingActivity(), "이용자 정보에 에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        UserSettingSelectRequest userSettingSelectRequest = new UserSettingSelectRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(UserInfoSettingActivity.this);
        queue.add(userSettingSelectRequest);
    }
}