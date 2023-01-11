package kr.co.company.healthapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import kr.co.company.healthapplication.request.DonationInsertRequest;
import kr.co.company.healthapplication.request.DonationUpdateRequest;
import kr.co.company.healthapplication.request.JoinRequest;
import kr.co.company.healthapplication.request.RunSelectRequest;
import kr.co.company.healthapplication.request.RunUpdateRequest;
import kr.co.company.healthapplication.request.UserInfoSelectRequest;
import kr.co.company.healthapplication.request.UserStepSelectRequest;

// 기부캠페인 팝업 액티비티 (2023-01-10 우진 수정)
public class DonationPopupActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView stepCount, DuserStep;
    private Button maxBtn, minBtn, donationBtn;
    private ImageButton closeBtn;

    // Preferences Shared
    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId, dNum, nowStep, userStep, returnID;   // 유저 아이디 값 불러오기 (저장해둔 값 없으면 초기값인 _으로 불러옴)
    private int updateStep, DBUserStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_popup);

        // 글 번호 받아오기
        Intent receiveIntent = getIntent();
        dNum = receiveIntent.getStringExtra("dNum");
        nowStep = receiveIntent.getStringExtra("nowStep");

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        selectUserStep();

        seekBar = findViewById(R.id.seekBar);
        stepCount = findViewById(R.id.stepCount);
        DuserStep = findViewById(R.id.dUserStep);
        maxBtn = findViewById(R.id.maxBtn);
        minBtn = findViewById(R.id.minBtn);
        donationBtn = findViewById(R.id.donationBtn);
        closeBtn = findViewById(R.id.closeBtn);

        userStep = stepCount.getText().toString();
        DuserStep.setText(Integer.toString(DBUserStep));

        // seekBar의 기본 값 지정
        seekBar.setProgress(1);

        // maxBtn 클릭 시
        maxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(10000);  // 10000으로 설정
                stepCount.setText(String.valueOf(10000));  // 10000으로 설정
            }
        });

        // minBtn 클릭 시
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(1);  // 1로 설정
                stepCount.setText(String.valueOf(1));  // 1로 설정
            }
        });

        // donationBtn 클릭 시 기부 완료 Toast메시지 뜨면서 화면 종료
        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(DuserStep.getText().toString())>=Integer.parseInt(userStep)) {
                    //3. 기록 DB에 저장
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                String jsonString = jsonObject.toString();
                                Log.d("전송여부", jsonString);

                                if(success) {
                                    // if문으로 기부 가능 금액 초과시 알림 뜨도록 추가해야함
                                    // 메시지가 안뜸
                                    updateStep = Integer.parseInt(userStep) + Integer.parseInt(nowStep);
                                    Toast.makeText(DonationPopupActivity.this, "기부되셨습니다.", Toast.LENGTH_SHORT).show();
                                    updateNowStep();

                                    finish();
                                }
                                else {

                                    Toast.makeText(DonationPopupActivity.this, "기부 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    // 서버로 Volley를 이용해서 요청을 함.
                    DonationInsertRequest donationInsertRequest = new DonationInsertRequest(Integer.parseInt(dNum), userId, userStep, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(DonationPopupActivity.this);
                    queue.add(donationInsertRequest);
                } else {
                    Toast.makeText(DonationPopupActivity.this, "기부 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // closeBtn 클릭 시 화면 종료
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                stepCount.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void selectUserStep() {
        // 유저의 정보 가져오기. (2023-01-10 이수)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    if (success) {
                        DBUserStep = jsonObject.optInt("DBUserStep", 0);
                        DuserStep.setText(Integer.toString(DBUserStep));
                    } else {
                        Toast.makeText(DonationPopupActivity.this, "걸음 수 가져오기 실패", Toast.LENGTH_SHORT).show();

                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        UserStepSelectRequest userStepSelectRequest = new UserStepSelectRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DonationPopupActivity.this);
        queue.add(userStepSelectRequest);
    }

    private void updateNowStep() {
        //3. 기록 DB에 저장
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    // 저장 성공인 경우.
                    if (success) {
                        Toast.makeText(getApplicationContext(), "저장 완료.", Toast.LENGTH_SHORT).show();
                    }
                    // 저장 실패인 경우.
                    else {
                        Toast.makeText(getApplicationContext(), "저장 실패.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        DonationUpdateRequest donationUpdateRequest = new DonationUpdateRequest(Integer.parseInt(dNum), updateStep, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DonationPopupActivity.this);
        queue.add(donationUpdateRequest);
    }
}