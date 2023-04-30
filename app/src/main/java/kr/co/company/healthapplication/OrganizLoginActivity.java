package kr.co.company.healthapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.OrganizLoginRequest;

// 기업 로그인  (04.29 인범)
public class OrganizLoginActivity extends AppCompatActivity {
    private EditText etId, etPwd;
    private Button btnLogin, btnJoin;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organiz_login);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        // GPS 권한 요청 (2023-01-07 인범)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        etId = findViewById(R.id.etId);
        etPwd = findViewById(R.id.etPwd);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OrganizId = etId.getText().toString();
                String OrganizPw = etPwd.getText().toString();
                organizLogin(OrganizId, OrganizPw);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(OrganizLoginActivity.this, OrganizTosActivity.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);    // 액티비티 이동 시 애니메이션 제거.
                startActivity(intent);
            }
        });

        // toggle btn
        LabeledSwitch labeledSwitch = findViewById(R.id.toggle);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                Intent intent= new Intent(OrganizLoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void organizLogin(String OrganizId, String OrganizPw) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success");
                    String oID = jsonObject.getString("id");

                    // 로그인에 성공한 경우.
                    if(success) {
                        editor.putString("organizId", OrganizId);
                        editor.apply();

                        Toast.makeText(getApplicationContext(),"로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrganizLoginActivity.this, CampaignListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    // 로그인에 실패한 경우.
                    else {
                        Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };

        OrganizLoginRequest organizLoginRequest = new OrganizLoginRequest(OrganizId, OrganizPw, responseListener);
        RequestQueue queue = Volley.newRequestQueue(OrganizLoginActivity.this);
        queue.add(organizLoginRequest);
    }

}