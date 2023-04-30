package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.OrganizInfoSelectRequest;
import kr.co.company.healthapplication.request.OrganizRemoveRequest;

// 기업 정보 확인  (04.29 인범)
public class OrganizMypageActivity extends AppCompatActivity {

    private Button btnLogout, btnRemove;
    private String id, name, proposer, joinDate;
    private TextView tvId, tvName, tvProposer, tvJoinDate;

    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organiz_mypage);

        tvId = findViewById(R.id.tvId);
        tvName = findViewById(R.id.tvName);
        tvProposer = findViewById(R.id.tvProposer);
        tvJoinDate = findViewById(R.id.tvJoinDate);

        // select해서 내정보 띄워주기
        selectOrganizInfo(); // 아이디 받아와서 하는걸로 수정필요.

        btnLogout = findViewById(R.id.btnLogOut);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.remove("organizId").apply();

                Intent intent = new Intent(OrganizMypageActivity.this, OrganizLoginActivity.class);
                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        btnRemove = findViewById(R.id.btnRemove);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원 탈퇴 시 정보 delete해주기
                organizRemove(); // 아이디 받아와서 하는걸로 수정 필요.
            }
        });

    }

    private void organizRemove() {
        String organizId = "asdf";

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    // 회원탈퇴 성공인 경우.
                    if (success) {
                        Toast.makeText(getApplicationContext(), "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrganizMypageActivity.this, OrganizLoginActivity.class);
                        startActivity(intent);
                    }
                    // 회원탈퇴 실패인 경우.
                    else {
                        Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        OrganizRemoveRequest organizRemoveRequest = new OrganizRemoveRequest(organizId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrganizMypageActivity.this);
        queue.add(organizRemoveRequest);
    }


    private void selectOrganizInfo() {
        // 기업 정보 가져오기.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean check = jsonObject.getBoolean("success");

                    if (check == true) {
                        // 값 받아서 저장
                        id = jsonObject.getString("id");
                        name = jsonObject.getString("name");
                        proposer = jsonObject.getString("proposer");
                        joinDate = jsonObject.getString("joinDate");

                        // 값 세팅
                        tvId.setText(id);
                        tvName.setText(name);
                        tvProposer.setText(proposer);
                        tvJoinDate.setText(joinDate);


                    } else {
                        Toast.makeText(OrganizMypageActivity.this, "이용자 정보를 확인하지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(OrganizMypageActivity.this, "에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        OrganizInfoSelectRequest organizInfoSelectRequest = new OrganizInfoSelectRequest("asdf", responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrganizMypageActivity.this);
        queue.add(organizInfoSelectRequest);
    }
}