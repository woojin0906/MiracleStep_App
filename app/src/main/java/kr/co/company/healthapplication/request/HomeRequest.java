package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class HomeRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/SelectCheckList.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public HomeRequest(String userID, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = now.format(formatter);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("nowDate", nowDate);

        Log.d("검색 아이디", userID);
        Log.d("검색 날짜", nowDate);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}