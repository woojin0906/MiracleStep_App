package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class TOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tos);

        // 체크박스 findViewById() 메소드
        final CheckBox cbAllAgree = findViewById(R.id.allAgree);
        final CheckBox cbAgree1 = findViewById(R.id.agreeone);
        final CheckBox cbAgree2 = findViewById(R.id.agreetwo);
        final CheckBox cbAgree3 = findViewById(R.id.agreethree);

        // AgreeButton -> 동의하기 버튼 클릭리스너
        Button AgreeButton = findViewById(R.id.AgreeButton);
        AgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전체 동의 또는 필수항목이 true인 경우 JoinActivity로 이동
                if (cbAllAgree.isChecked() == true || (cbAgree1.isChecked() == true && cbAgree2.isChecked() == true)) {
                    Intent intent = new Intent(TOSActivity.this, JoinActivity.class);
                    Toast.makeText(getApplicationContext(), "동의 되었습니다.", Toast.LENGTH_SHORT).show();
                    intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);    // 액티비티 이동 시 애니메이션 제거.
                    startActivity(intent);
                    // 항목별 경우의 수에 따라 필수 항목 동의하라는 Toast 메시지 기능 제공
                } else if (cbAgree1.isChecked() == true || cbAgree2.isChecked() == true || cbAgree3.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "필수 항목 동의 해주십시오.", Toast.LENGTH_SHORT).show();
                } else if ((cbAgree1.isChecked() == true && cbAgree3.isChecked() == true) || (cbAgree2.isChecked() == true && cbAgree3.isChecked() == true)) {
                    Toast.makeText(getApplicationContext(), "필수 항목 동의 해주십시오.", Toast.LENGTH_SHORT).show();
                } else if (cbAgree1.isChecked() == false || cbAgree2.isChecked() == false) {
                    Toast.makeText(getApplicationContext(), "필수 항목 동의 해주십시오.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 체크박스 전체 동의 -> true인 경우 모든 항목 true로 변경, false인 경우 모든 항목 false로 변경
        CheckBox allAgree = findViewById(R.id.allAgree);
        allAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAllAgree.isChecked() == true) {
                    cbAgree1.setChecked(true);
                    cbAgree2.setChecked(true);
                    cbAgree3.setChecked(true);
                } else {
                    cbAgree1.setChecked(false);
                    cbAgree2.setChecked(false);
                    cbAgree3.setChecked(false);
                }
            }
        });

        // 첫 번째 체크박스 필수 항목
        CheckBox agreeone = findViewById(R.id.agreeone);
        agreeone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAgree1.isChecked() == false) {
                    cbAllAgree.setChecked(false);
                } else {
                    if(cbAgree2.isChecked() == true && cbAgree3.isChecked() == true){
                        cbAllAgree.setChecked(true);
                    }
                }
            }
        });

        // 두 번째 체크박스 필수 항목
        CheckBox agreetwo = findViewById(R.id.agreetwo);
        agreetwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAgree2.isChecked() == false) {
                    cbAllAgree.setChecked(false);
                } else {
                    if(cbAgree1.isChecked() == true && cbAgree3.isChecked() == true){
                        cbAllAgree.setChecked(true);
                    }
                }
            }
        });

        // 세 번째 체크박스 필수 항목
        CheckBox agreethree = findViewById(R.id.agreethree);
        agreethree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cbAgree3.isChecked() == false) {
                    cbAllAgree.setChecked(false);
                } else {
                    if(cbAgree1.isChecked() == true && cbAgree2.isChecked() == true){
                        cbAllAgree.setChecked(true);
                    }
                }
            }
        });
    }
}