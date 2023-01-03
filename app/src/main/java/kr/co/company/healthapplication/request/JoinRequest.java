package kr.co.company.healthapplication.request;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;


// 회원가입 시 서버로 회원가입 정보 전송 (2023-01-02 이수)
public class JoinRequest extends StringRequest {

    final static private String URL = "http://miraclestep.ivyro.net/Register.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public JoinRequest(String userID, String userPassword, String userName, String UserPhoneNumber,
                       double userHeight, double userWeight, Date userBirth, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);

        Log.d("전화번호", UserPhoneNumber);
        Log.d("운동날짜", formatedNow);
        Log.d("생년월일", userBirth+"");

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPassword);
        map.put("userName", userName);
        map.put("UserPhoneNumber", UserPhoneNumber);
        map.put("UserInfoDate", formatedNow);
        map.put("userHeight", userHeight+"");
        map.put("userWeight", userWeight+"");
        map.put("userBirth", userBirth+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
