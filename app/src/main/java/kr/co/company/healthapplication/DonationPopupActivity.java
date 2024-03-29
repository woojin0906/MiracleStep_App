package kr.co.company.healthapplication;

import android.app.Activity;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import kr.co.company.healthapplication.request.DonationInsertRequest;
import kr.co.company.healthapplication.request.DonationUpdateRequest;
import kr.co.company.healthapplication.request.UserStepSelectRequest;
import kr.co.company.healthapplication.request.UserStepUpdateRequest;

// 기부캠페인 팝업 액티비티 (2023-03-21 우진 수정)
public class DonationPopupActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView stepCount, DuserStep;
    private Button maxBtn, minBtn, donationBtn;
    private ImageButton closeBtn;

    // Preferences Shared
    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId, dNum, nowStep, userStep, returnID;
    private int updateStep, DBUserStep, updateUserStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_popup);

        // 글 번호 받아오기
        Intent receiveIntent = getIntent();
        dNum = receiveIntent.getStringExtra("dNum");
        nowStep = receiveIntent.getStringExtra("nowStep").replace(",", "");

        // userID 값 받아오기
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");   // 유저 아이디 값 불러오기 (저장해둔 값 없으면 초기값인 _으로 불러옴)

        selectUserStep();

        seekBar = findViewById(R.id.seekBar);
        stepCount = findViewById(R.id.stepCount);
        DuserStep = findViewById(R.id.dUserStep);
        maxBtn = findViewById(R.id.maxBtn);
        minBtn = findViewById(R.id.minBtn);
        donationBtn = findViewById(R.id.donationBtn);
        closeBtn = findViewById(R.id.closeBtn);


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
                userStep = stepCount.getText().toString().replace(",", "");

                if(Integer.parseInt(DuserStep.getText().toString().replace(",", ""))>=Integer.parseInt(userStep)) {
                    Log.d("유저의 기부가능걸음수", Integer.parseInt(DuserStep.getText().toString().replace(",", ""))+"");
                    Log.d("기부하려는 걸음 수", Integer.parseInt(userStep)+"");
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
                                    // 메시지가 안뜸
                                    updateStep = Integer.parseInt(userStep) + Integer.parseInt(nowStep);
                                    Toast.makeText(DonationPopupActivity.this, "기부되셨습니다.", Toast.LENGTH_SHORT).show();

                                    // 현재 기부된 걸음 수 업데이트하기
                                    updateNowStep();

                                    // 사용자의 기부 가능 걸음 수 - 기부할 걸음 수
                                    updateUserStep = (Integer.parseInt(DuserStep.getText().toString().replace(",", ""))-Integer.parseInt(userStep));

                                    // 사용자의 기부 가능 걸음 수 업데이트하기
                                    updateUserStep();
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
                    Toast.makeText(DonationPopupActivity.this, "기부 가능 걸음 수가 초과되었습니다.", Toast.LENGTH_LONG).show();
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

    // 사용자의 기부 가능 걸음 수 업데이트
    private void updateUserStep() {
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
                        Toast.makeText(getApplicationContext(), "업데이트 완료.", Toast.LENGTH_SHORT).show();
                    }
                    // 저장 실패인 경우.
                    else {
                        Toast.makeText(getApplicationContext(), "업데이트 실패.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        UserStepUpdateRequest userStepUpdateRequest = new UserStepUpdateRequest(userId, updateUserStep, responseListener);
        RequestQueue queue = Volley.newRequestQueue(DonationPopupActivity.this);
        queue.add(userStepUpdateRequest);

    }

    // 사용자의 기부 가능 걸음 수 가져오기
    private void selectUserStep() {
       Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값 리턴 받음
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    String jsonString = jsonObject.getString("DBUserStep");
                    Log.d("전송여부", jsonString);

                    if (success) {
                        DBUserStep = jsonObject.optInt("DBUserStep", 0);

                        DecimalFormat myFormatter = new DecimalFormat("###,###");
                        String doStep = myFormatter.format(DBUserStep);
                        DuserStep.setText(doStep);
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

    // 해당 캠페인의 현재 기부된 걸음 수 업데이트
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
                        Toast.makeText(getApplicationContext(), "업데이트 완료.", Toast.LENGTH_SHORT).show();
                    }
                    // 저장 실패인 경우.
                    else {
                        Toast.makeText(getApplicationContext(), "업데이트 실패.", Toast.LENGTH_SHORT).show();
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