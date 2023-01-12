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

public class UserSettingUpdateRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/UpdateUserSetting.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public UserSettingUpdateRequest(String returnUserID, String settingUserPhoto, String settingId, String settingName, String settingPhoneNumber, String settingHeight, String settingWeight, Response.Listener<String> listener) {
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);
        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nowDate = now.format(formatter);

        Log.d("전송 날짜", nowDate);

        map = new HashMap<>();
        map.put("returnUserID", returnUserID);
        map.put("userImg", settingUserPhoto);
        map.put("userID", settingId);
        map.put("userName", settingName);
        map.put("userPhone", settingPhoneNumber);
        map.put("userHeight", settingHeight);
        map.put("userWeight", settingWeight);
        map.put("userDate", nowDate);
    }
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
