package kr.co.company.healthapplication.request;


import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserRemoveRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/UserRemove.php";
    private Map<String, String> map;

    public UserRemoveRequest(String id, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
