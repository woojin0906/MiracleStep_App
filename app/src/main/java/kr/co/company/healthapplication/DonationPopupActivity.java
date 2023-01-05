package kr.co.company.healthapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class DonationPopupActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView textview;
    private Button maxBtn, minBtn, donationBtn;
    private ImageButton closeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_popup);

        seekBar = findViewById(R.id.seekBar);
        textview = findViewById(R.id.textView);
        maxBtn = findViewById(R.id.maxBtn);
        minBtn = findViewById(R.id.minBtn);
        donationBtn = findViewById(R.id.donationBtn);
        closeBtn = findViewById(R.id.closeBtn);

        // seekBar의 기본 값 지정
        seekBar.setProgress(1);

        // maxBtn 클릭 시
        maxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(10000);  // 10000으로 설정
                textview.setText(String.valueOf(10000));  // 10000으로 설정
            }
        });

        // minBtn 클릭 시
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(1);  // 1로 설정
                textview.setText(String.valueOf(1));  // 1로 설정
            }
        });

        // donationBtn 클릭 시 기부 완료 Toast메시지 뜨면서 화면 종료
        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if문으로 기부 가능 금액 초과시 알림 뜨도록 추가해야함

                Toast.makeText(DonationPopupActivity.this, "기부되셨습니다.", Toast.LENGTH_SHORT).show();

                finish();
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
                textview.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}