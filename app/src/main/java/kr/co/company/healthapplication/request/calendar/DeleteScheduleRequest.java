package kr.co.company.healthapplication.request.calendar;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DeleteScheduleRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/DeleteSchedule.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public DeleteScheduleRequest(String listIndex, String userId, String date, Response.Listener<String> responseListener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, responseListener, null);
        // 현재 날짜 구하기

        Log.d("삭제 유저 아이디", userId);
        Log.d("삭제 날짜", date);
        Log.d("삭제 번호", listIndex);

        map = new HashMap<>();
        map.put("userID", userId);
        map.put("date", date);
        map.put("listNum", listIndex);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

