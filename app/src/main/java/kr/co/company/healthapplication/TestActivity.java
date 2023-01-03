package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

// 로그인 테스트 용도 액티비티 - 추후 삭제 예정 (2023-01-02 이수)
public class TestActivity extends AppCompatActivity {
    private TextView tv_id, tv_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv_id = findViewById(R.id.tv_id);
        tv_pass = findViewById(R.id.tv_pass);

        // 로그인을 성공한 경우 여기로 넘어옴.
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String userPass = intent.getStringExtra("userPassword");

        tv_id.setText(userID);
        tv_pass.setText(userPass);
    }
}
