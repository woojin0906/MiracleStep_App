package kr.co.company.healthapplication.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class OrganizLoginRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/OrganizLogin.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public OrganizLoginRequest(String organizId, String organizPw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", organizId);
        map.put("pw", organizPw);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}