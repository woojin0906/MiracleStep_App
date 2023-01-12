package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// User값 서버로 전송 (2023-01-12 우진 생성)
public class UserStepUpdateRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/UpdateUserStep.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public UserStepUpdateRequest(String userID, int updateUserStep, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("updateUserStep", String.valueOf(updateUserStep));

        Log.d("어디", "업데이트");
        Log.d("아이디", userID);
        Log.d("업데이트된 사용자의 기부 가능 걸음 수", String.valueOf(updateUserStep));

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}