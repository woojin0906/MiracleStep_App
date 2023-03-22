package kr.co.company.healthapplication.request.run;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SelectRunRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/SelectRunning.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public SelectRunRequest(String userID, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = now.format(formatter);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("runDate", nowDate);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}