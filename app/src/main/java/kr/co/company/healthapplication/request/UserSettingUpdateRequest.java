package kr.co.company.healthapplication.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserSettingUpdateRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/UpdateUserSetting.php";
    private Map<String, String> map;

    public UserSettingUpdateRequest(String userId, String settingHeight, String settingWeight, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId", userId);
        map.put("userHeight", settingHeight);
        map.put("userWeight", settingWeight);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
