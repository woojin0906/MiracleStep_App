package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DonationPostActivity extends AppCompatActivity {

    private Button donationBtn;
    private ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_post);

        donationBtn = findViewById(R.id.donationBtn);
        backBtn = findViewById(R.id.backBtn);

        // donationBtn 클릭 시 DonationPopupActivity으로 이동
        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DonationPostActivity.this, DonationPopupActivity.class);
                startActivity(intent);

            }
        });

        // backBtn 클릭 시 DonationActivity 이동
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                Intent intent = new Intent(DonationPostActivity.this, DonationActivity.class);
                startActivity(intent);

            }
        });
    }
}