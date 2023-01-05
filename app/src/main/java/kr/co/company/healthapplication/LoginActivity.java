package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.LoginRequest;

// 로그인 액티비티 클래스 (2023-01-02 이수)
public class LoginActivity extends AppCompatActivity {
    private EditText etId, etPwd;
    private Button btnLogin, btnJoin;
    private TextView tvFindPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);
        tvFindPwd = findViewById(R.id.tvFindPwd);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = etId.getText().toString();
                String userPassword = etPwd.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                            boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                            //String jsonString = jsonObject.toString();
                            //Log.d("전송여부", jsonString);

                            // 로그인에 성공한 경우.
                            if(success) {
                                String userID = jsonObject.getString("userID");
                                String userPassword = jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, TestActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                startActivity(intent);
                            }
                            // 로그인에 실패한 경우.
                            else {
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userId, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, JoinActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);    // 액티비티 이동 시 애니메이션 제거.
                startActivity(intent);
            }
        });

        tvFindPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, ResetPasswordActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);    // 액티비티 이동 시 애니메이션 제거.
                startActivity(intent);
            }
        });
    }
}