package kr.co.company.healthapplication.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/ResetPassword.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public ResetPasswordRequest(String password, Response.Listener<String> responseListener) {
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, responseListener, null);
        map = new HashMap<>();
        map.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}