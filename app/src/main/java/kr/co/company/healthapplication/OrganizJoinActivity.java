package kr.co.company.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import kr.co.company.healthapplication.request.OrganizJoinRequest;

public class OrganizJoinActivity extends AppCompatActivity {
    private EditText etId, etPwd, etCFPwd, etName, etProposer;
    private Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organiz_join);

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        etCFPwd = findViewById(R.id.etCFPwd);
        etName = findViewById(R.id.etName);
        etProposer = findViewById(R.id.etProposer);
        btnJoin = findViewById(R.id.btnJoin);

        // 회원가입 버튼 클릭 이벤트 메서드
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oId = etId.getText().toString();
                String oPassword = etPwd.getText().toString();
                String oCFPassword = etCFPwd.getText().toString();
                String oName = etName.getText().toString();
                String oProposer = etProposer.getText().toString();

                // 모든 정보 입력 확인
                if(oId.equals("") || oPassword.equals("") || oCFPassword.equals("") || oName.equals("") || oProposer.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 재입력 확인
                if(!oPassword.equals(oCFPassword)) {
                    Toast.makeText(getApplicationContext(), "비밀번호 재입력을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 아이디 길이제한
                if(oId.length() < 6) {
                    Toast.makeText(getApplicationContext(), "아이디를 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 길이제한
                if(oPassword.length() < 6) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 6글자 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }


                // JSON 오브젝트를 활용하여 회원가입 요청을 하는 메서드
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            // 회원가입 성공인 경우.
                            if (success) {
                                Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrganizJoinActivity.this, OrganizLoginActivity.class);
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

                Log.d("joinAc", "hi");
                // 서버로 Volley를 이용해서 요청을 함.
                OrganizJoinRequest organizJoinRequest = new OrganizJoinRequest(oId, oPassword, oName, oProposer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(OrganizJoinActivity.this);
                queue.add(organizJoinRequest);
            }
        });
    }
}