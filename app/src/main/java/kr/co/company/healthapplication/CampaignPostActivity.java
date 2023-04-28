package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

// 캠페인 게시글 액티비티 (2023-04-06 우진)
public class CampaignPostActivity extends AppCompatActivity {

    private Button testButton;
    private ImageButton backBtn;
    private TextView tvTitleName, tvName, tvNowStep, tvDate, tvMaxStep, tvContent, tvStartDate;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_post);

        tvTitleName = (TextView) findViewById(R.id.titleName);
        tvName = (TextView) findViewById(R.id.name);
        tvNowStep = (TextView) findViewById(R.id.nowStep);
        img = findViewById(R.id.ivDonationProfile);
        tvDate = (TextView) findViewById(R.id.date);
        tvMaxStep = (TextView) findViewById(R.id.maxStep);
        tvContent = (TextView) findViewById(R.id.content);
        tvStartDate = (TextView) findViewById(R.id.startdate);

        Intent receiveIntent = getIntent();
        final String titleName = receiveIntent.getStringExtra("titleName");
        final String name = receiveIntent.getStringExtra("name");
        final String nowStep = receiveIntent.getStringExtra("nowStep");
        //final String ivDonationProfile = receiveIntent.getStringExtra("ivDonationProfile");
        final String date = receiveIntent.getStringExtra("date");
        final String maxStep = receiveIntent.getStringExtra("maxStep");
        final String content = receiveIntent.getStringExtra("content");
        final String startDate = receiveIntent.getStringExtra("startDate");
        final String num = receiveIntent.getStringExtra("dNum");

        tvTitleName.setText(titleName);
        tvName.setText(name);
        tvNowStep.setText(nowStep);
        //Glide.with(img).load(ivDonationProfile).into(img);
        tvDate.setText(date);
        tvStartDate.setText(startDate);
        tvMaxStep.setText(maxStep);
        tvContent.setText(content);


        // backBtn 클릭 시 DonationActivity 이동
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CampaignPostActivity.this, CampaignUpdateActivity.class);
                intent.putExtra("dNum", num);
                intent.putExtra("titleName", titleName);
                intent.putExtra("startDate", startDate);
                intent.putExtra("date", date);
                intent.putExtra("content", content);
                intent.putExtra("maxStep", maxStep);
                intent.putExtra("name", name);

                startActivity(intent);
            }
        });
    }
}