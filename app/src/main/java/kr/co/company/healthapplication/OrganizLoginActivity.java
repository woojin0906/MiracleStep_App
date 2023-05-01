package kr.co.company.healthapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    chatbotTest("현재 날씨 알려줘");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

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

    // 챗봇 테스트
    private void chatbotTest(String userInput) throws Exception {
        try {
            URL url = new URL("https://chatbot-api.run.goorm.site/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject data = new JSONObject();
            data.put("user_input", userInput);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data.toString());
            out.flush();
            out.close();

            String temp = "";
            String content = "";
            InputStream responseBody = conn.getInputStream();
            InputStreamReader responseBodyReader =
                    new InputStreamReader(responseBody, "UTF-8");
            BufferedReader br = new BufferedReader( responseBodyReader );
            while ((temp = br.readLine()) != null) {
                content += temp;
            }
            JSONObject responseJson = new JSONObject(content);
            Log.d("chatGPT 응답", responseJson.toString(2));
            br.close();


            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                //System.out.println(response.toString());
            } else {
                Log.d("HTTP error code : ", String.valueOf(responseCode));
                //System.out.println("HTTP error code : " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}