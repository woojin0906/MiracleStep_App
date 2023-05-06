package kr.co.company.healthapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;

import kr.co.company.healthapplication.request.CampaignWriterRequest;
import kr.co.company.healthapplication.request.JoinRequest;

// 캠페인 등록 액티비티 (2023-04-06 우진)
public class CampaignWriterActivity extends AppCompatActivity {

    private EditText edtitleName, edstartdate, eddate, edmaxStep, edcontent;
    private TextView tname;
    private RadioGroup radioGroup;
    private String category = "animal";
    private Button campaign_writer_btn, campaign_picture_btn;
    private ImageButton backBtn;
    private Uri uri;
    private ImageView campaign_imageView;
    private Bitmap bitmap;
    // Preferences Shared
    private SharedPreferences pref;
    private SharedPreferences.Editor  editor;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_writer);

        // 이용자 정보 가져오기.
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("organizId", "_");

        edtitleName = findViewById(R.id.titleName);
        tname = findViewById(R.id.name);
        edstartdate = findViewById(R.id.startdate);
        eddate = findViewById(R.id.date);
        edmaxStep = findViewById(R.id.maxStep);
        edcontent = findViewById(R.id.content);
        campaign_picture_btn = findViewById(R.id.campaign_picture_btn);
        campaign_imageView = findViewById(R.id.campaign_imageView);

        tname.setText(userId);

        campaign_picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityResult.launch(intent);
                }
        });

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.animalRadioButton:
                        category = "animal";
                        break;
                    case R.id.peopleRadioButton:
                        category = "people";
                        break;
                    case R.id.environmentRadioButton:
                        category = "environment";
                        break;
                }
            }
        });

        // 캠페인 등록 클릭 이벤트 메서드
        campaign_writer_btn = findViewById(R.id.campaign_writer_btn);
        campaign_writer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleName = edtitleName.getText().toString();
                String name = tname.getText().toString();
                String startdate = edstartdate.getText().toString();
                String date = eddate.getText().toString();
                String maxStep = edmaxStep.getText().toString();
                String content = edcontent.getText().toString();

                // 모든 정보 입력 확인
                if(titleName.equals("") || startdate.equals("")
                        || date.equals("") || maxStep.equals("") || content.equals("")) {
                    Toast.makeText(getApplicationContext(), "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 목표 걸음수 길이제한
                if(maxStep.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "목표 걸음수를 0이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // JSON 오브젝트를 활용하여 등록 요청을 하는 메서드
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                            boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                            // 등록 성공인 경우.
                            if (success) {
                                Toast.makeText(getApplicationContext(), "등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(CampaignWriterActivity.this, CampaignPostActivity.class);
//                                startActivity(intent);
                                finish();
                            }
                            // 등록 실패인 경우.
                            else {
                                Toast.makeText(getApplicationContext(), "등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해서 요청을 함.
                CampaignWriterRequest campaignWriterRequest = new CampaignWriterRequest(category, titleName, name, startdate, date, maxStep, content, uri.toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(CampaignWriterActivity.this);
                queue.add(campaignWriterRequest);

            }
        });

        // backBtn 클릭 시 DonationActivity 이동
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        Log.d(">>>>>> ", String.valueOf(uri));
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            campaign_imageView.setImageBitmap(bitmap);

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

}