package kr.co.company.healthapplication.request.home;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserRunStepRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/SelectUserRunStep.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public UserRunStepRequest(String userID, String nowDate, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        Log.d("아이디", userID);
        Log.d("날짜", nowDate);
        map = new HashMap<>();
        map.put("userID", userID);
        map.put("nowDate", nowDate);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {        return map;
    }
}