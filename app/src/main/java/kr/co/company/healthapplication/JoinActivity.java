package kr.co.company.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import kr.co.company.healthapplication.request.JoinRequest;

// 회원가입 액티비티 클래스 (2023-01-02 이수)
public class JoinActivity extends AppCompatActivity {
    private EditText etId, etPwd, etCFPwd, etName, etPhoneNumber, etHeight, etWeight;
    private DatePicker dpBirth;
    private Button btnJoin;
    private String birthDate = "2010-01-01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        etCFPwd = findViewById(R.id.etCFPwd);
        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnJoin = findViewById(R.id.btnJoin);
        dpBirth = findViewById(R.id.dpBirth);

        dpBirth.init(2010, 01, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                birthDate = year + "-" + monthOfYear + "-" + dayOfMonth;
            }
        });

        // 회원가입 버튼 클릭 이벤트 메서드
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etId.getText().toString();
                String userPassword = etPwd.getText().toString();
                String userCFPassword = etCFPwd.getText().toString();
                String userName = etName.getText().toString();
                String userPhoneNumber = etPhoneNumber.getText().toString();
                String userHeight = etHeight.getText().toString();
                String userWeight =etWeight.getText().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                // 모든 정보 입력 확인
                if(userId.equals("") || userPassword.equals("") || userName.equals("") || userPhoneNumber.equals("")
                        || etHeight.getText().toString().equals("") || etWeight.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 재입력 확인
                if(!userPassword.equals(userCFPassword)) {
                    Toast.makeText(getApplicationContext(), "비밀번호 재입력을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 아이디 길이제한
                if(userId.length() < 6) {
                    Toast.makeText(getApplicationContext(), "아이디를 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 길이제한
                if(userPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 전송 시 필요한 형변환 실행.
                int dUserHeight = Integer.parseInt(userHeight);
                int dUserWeight = Integer.parseInt(userWeight);
                Log.d("생년월일", String.valueOf(birthDate));

                // JSON 오브젝트를 활용하여 회원가입 요청을 하는 메서드
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                            boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                            // 회원가입 성공인 경우.
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            // 회원가입 실패인 경우.
                            else {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                JoinRequest joinRequest = new JoinRequest(userId, userPassword, userName, userPhoneNumber, dUserHeight, dUserWeight, birthDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                queue.add(joinRequest);
            }
        });
    }
}