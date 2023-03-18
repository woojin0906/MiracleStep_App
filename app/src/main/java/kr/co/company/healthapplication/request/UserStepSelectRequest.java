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

// User값 서버로 전송 (2023-03-18 우진 수정)
public class UserStepSelectRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/SelectUserStep.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public UserStepSelectRequest(String userID, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);

        Log.d("검색 아이디", userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
