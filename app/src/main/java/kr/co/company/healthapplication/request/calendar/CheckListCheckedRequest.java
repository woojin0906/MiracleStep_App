package kr.co.company.healthapplication.request.calendar;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CheckListCheckedRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/CheckListChecked.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public CheckListCheckedRequest(String userID, String listNum, String checked, Response.Listener<String> listener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = now.format(formatter);

        Log.d("request 사용자 아아디", userID);

        map = new HashMap<>();
        map.put("UserID", userID);      // 사용자 id
        map.put("ListNum", listNum);    // 리스트 번호
        map.put("Checked", checked);    // 체크 여부
        map.put("NowDate", nowDate);    // 현재 날짜
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}