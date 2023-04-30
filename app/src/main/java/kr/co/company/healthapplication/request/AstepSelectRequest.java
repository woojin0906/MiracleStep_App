package kr.co.company.healthapplication.request;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AstepSelectRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/SelectAstep.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public AstepSelectRequest(String id, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        Log.d("astepID", id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}