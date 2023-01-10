package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// Donation값 서버로 전송 (2023-01-10 우진 생성)
public class DonationRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/Donation.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public DonationRequest(String category, Response.Listener<String> listener) {
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("category", category);

        Log.d("카테고리", category);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}