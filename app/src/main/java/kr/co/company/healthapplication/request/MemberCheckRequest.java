package kr.co.company.healthapplication.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// 회원여부를 확인하는 클래스 (2023-01-04 이수)
public class MemberCheckRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/MemberCheck.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public MemberCheckRequest(String email, Response.Listener<String> responseListener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, responseListener, null);
        map = new HashMap<>();
        map.put("userID", email);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}