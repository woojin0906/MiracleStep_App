package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

// 기부캠페인 게시글 액티비티 (2023-01-10 우진 수정)
public class DonationPostActivity extends AppCompatActivity {

    private Button donationBtn;
    private ImageButton backBtn;
    private TextView tvTitleName, tvName, tvNowStep, tvDate, tvMaxStep, tvContent;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_post);

        tvTitleName = (TextView) findViewById(R.id.titleName);
        tvName = (TextView) findViewById(R.id.name);
        tvNowStep = (TextView) findViewById(R.id.nowStep);
        img = findViewById(R.id.ivDonationProfile);
        tvDate = (TextView) findViewById(R.id.date);
        tvMaxStep = (TextView) findViewById(R.id.maxStep);
        tvContent = (TextView) findViewById(R.id.content);

        Intent receiveIntent = getIntent();
        final String titleName = receiveIntent.getStringExtra("titleName");
        final String name = receiveIntent.getStringExtra("name");
        final String nowStep = receiveIntent.getStringExtra("nowStep");
        final String ivDonationProfile = receiveIntent.getStringExtra("ivDonationProfile");
        final String date = receiveIntent.getStringExtra("date");
        final String maxStep = receiveIntent.getStringExtra("maxStep");
        final String content = receiveIntent.getStringExtra("content");
        final String category = receiveIntent.getStringExtra("category");
        final String dNum = receiveIntent.getStringExtra("dNum");

        tvTitleName.setText(titleName);
        tvName.setText(name);
        tvNowStep.setText(nowStep);
        Glide.with(img).load(ivDonationProfile).into(img);
        tvDate.setText(date);
        tvMaxStep.setText(maxStep);
        tvContent.setText(content);

        donationBtn = findViewById(R.id.donationBtn);
        backBtn = findViewById(R.id.backBtn);

        // donationBtn 클릭 시 DonationPopupActivity으로 이동
        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationPostActivity.this, DonationPopupActivity.class);
                intent.putExtra("dNum", dNum);
                intent.putExtra("nowStep", nowStep);

                startActivity(intent);

            }
        });

        // backBtn 클릭 시 DonationActivity 이동
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}