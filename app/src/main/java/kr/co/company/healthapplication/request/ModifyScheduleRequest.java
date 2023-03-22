package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ModifyScheduleRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/UpdateUserSchedule.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public ModifyScheduleRequest(String listNum, String userId, String date, String title, Response.Listener<String> responseListener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, responseListener, null);
        // 현재 날짜 구하기

        Log.d("수정 유저 아이디", userId);
        Log.d("수정 날짜", date);
        Log.d("수정전 내용", listNum);
        Log.d("수정 내용", title);

        map = new HashMap<>();
        map.put("userID", userId);
        map.put("listNum", listNum);
        map.put("date", date);
        map.put("title", title);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
