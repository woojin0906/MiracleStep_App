package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.CampaignUpdateRequest;
import kr.co.company.healthapplication.request.UserStepUpdateRequest;

// 캠페인 게시글 수정 액티비티 (2023-04-27 우진)
public class CampaignUpdateActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private Button campaign_update_btn;
    private EditText edtitleName, edstartdate, eddate, edmaxStep, edcontent;
    private TextView tname;
    private String titleName, startdate, date, content, smaxStep, rNum, rtitleName, rdate, rmaxStep, rcontent, rstartdate, rname;
    private int maxStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_update);

        // 글 번호 받아오기
        Intent receiveIntent = getIntent();
        rNum = receiveIntent.getStringExtra("dNum");
        rtitleName = receiveIntent.getStringExtra("titleName");
        rname = receiveIntent.getStringExtra("name");
        //final String ivDonationProfile = receiveIntent.getStringExtra("ivDonationProfile");
        rdate = receiveIntent.getStringExtra("date");
        rstartdate = receiveIntent.getStringExtra("startDate");
        rmaxStep = receiveIntent.getStringExtra("maxStep").replace(",", "");
        rcontent = receiveIntent.getStringExtra("content");

        edtitleName = findViewById(R.id.titleName);
        tname = findViewById(R.id.name);
        edstartdate = findViewById(R.id.startdate);
        eddate = findViewById(R.id.date);
        edmaxStep = findViewById(R.id.maxStep);
        edcontent = findViewById(R.id.content);

        edtitleName.setText(rtitleName);
        tname.setText(rname);
        edstartdate.setText(rstartdate);
        eddate.setText(rdate);
        edmaxStep.setText(rmaxStep);
        edcontent.setText(rcontent);


        // campaign_update_btn 클릭 시 DonationActivity 이동
        campaign_update_btn = findViewById(R.id.campaign_update_btn);
        campaign_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleName = edtitleName.getText().toString();
                startdate = edstartdate.getText().toString();
                date = eddate.getText().toString();
                smaxStep = edmaxStep.getText().toString();
                content = edcontent.getText().toString();
                maxStep = Integer.parseInt(smaxStep);

                // 모든 정보 입력 확인
                if(titleName.equals("") || startdate.equals("")
                        || date.equals("") || smaxStep.equals("") || content.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 목표 걸음수 길이제한
                if(smaxStep.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "목표 걸음수를 0이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                updateCampaign();

            }
        });

        // backBtn 클릭 시 DonationActivity 이동
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 사용자의 기부 가능 걸음 수 업데이트
    private void updateCampaign() {
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
                        finish();

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
        CampaignUpdateRequest campaignUpdateRequest = new CampaignUpdateRequest(Integer.parseInt(rNum), titleName, startdate, date, maxStep, content, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CampaignUpdateActivity.this);
        queue.add(campaignUpdateRequest);

    }
}