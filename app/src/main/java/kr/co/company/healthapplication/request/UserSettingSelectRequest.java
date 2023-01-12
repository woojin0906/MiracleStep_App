package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserSettingSelectRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/SelectUserSetting.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public UserSettingSelectRequest(String userID, Response.Listener<String> listener){
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