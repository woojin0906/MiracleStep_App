package kr.co.company.healthapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.company.healthapplication.request.DeleteScheduleRequest;
import kr.co.company.healthapplication.request.ModifyScheduleRequest;

public class CalendarPopupActivity extends Activity {

    private TextView tvSelectDate;
    private EditText etScheduleTitle;
    private Button btnModifySchedule, btnDeleteSchedule;
    private String listNum;

    // SharedPreference
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        tvSelectDate = findViewById(R.id.tvSelectDate);
        etScheduleTitle = findViewById(R.id.etScheduleTitle);
        btnModifySchedule = findViewById(R.id.btnModifySchedule);
        btnDeleteSchedule = findViewById(R.id.btnDeleteSchedule);

        Intent intent = getIntent();
        // 현재 로그인된 사용자 아이디 가져오기
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        userId = pref.getString("UserID", "_");

        listNum = intent.getStringExtra("listNum");
        etScheduleTitle.setText(intent.getStringExtra("content"));
        tvSelectDate.setText(intent.getStringExtra("date"));

        btnModifySchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etScheduleTitle.getText().toString().equals(""))
                    modifySchedule(listNum, userId, tvSelectDate.getText().toString(), etScheduleTitle.getText().toString());
                else
                    Toast.makeText(getApplicationContext(), "일정내용을 정리해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSchedule(listNum, userId, tvSelectDate.getText().toString());
            }
        });
    }

    private void modifySchedule(String listNum, String userId, String date, String title) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    if (success)
                        Toast.makeText(getApplicationContext(), "일정이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "일정 수정이 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    //startActivityForResult(intent, RESULT_OK);
                    //setResult(RESULT_OK, intent);
                    //onActivityResult(RESULT_OK, RESULT_OK, intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        ModifyScheduleRequest userSettingUpdateRequest = new ModifyScheduleRequest(listNum, userId, date, title ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(userSettingUpdateRequest);
    }


    private void deleteSchedule(String listNum, String userId, String date) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    if (success)
                        Toast.makeText(getApplicationContext(), "일정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "일정 삭제가 실패하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        // 서버로 Volley를 이용해서 요청을 함.
        DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest(listNum, userId, date ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(deleteScheduleRequest);
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", "Close Popup");
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    /*@Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }*/
}
