package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// Checked값 서버로 전송 (2023-01-12 인범 수정)
public class CheckListCheckedRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/CheckListChecked.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public CheckListCheckedRequest(String UserID, String ListNum, String Checked, Response.Listener<String> listener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String NowDate = now.format(formatter);

        Log.d("request 사용자 아아디", UserID);

        map = new HashMap<>();
        map.put("UserID", UserID);      // 사용자 id
        map.put("ListNum", ListNum);    // 리스트 번호
        map.put("Checked", Checked);    // 체크 여부
        map.put("NowDate", NowDate);    // 현재 날짜

        Log.d("UserID", UserID);
        Log.d("ListNum", ListNum);
        Log.d("Checked", Checked);
        Log.d("NowDate", NowDate);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }




}