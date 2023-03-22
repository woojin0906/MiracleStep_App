package kr.co.company.healthapplication.request.calendar;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// 회원가입 시 서버로 회원가입 정보 전송 (2023-01-02 이수)
public class InsertCheckListRequest extends StringRequest {

    final static private String URL = "http://miraclestep01.dothome.co.kr/InsertCheckList.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public InsertCheckListRequest(String userID, String date, String scheduleTitle, Response.Listener<String> responseListener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, responseListener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("date", date);
        map.put("scheduleTitle", scheduleTitle);

        Log.d("입력된 아이디 1", userID);
        Log.d("입력된 date 1", date);
        Log.d("입력된 scheduleTitle 1", scheduleTitle);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}