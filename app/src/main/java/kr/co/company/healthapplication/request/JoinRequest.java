package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


// 회원가입 시 서버로 회원가입 정보 전송
public class JoinRequest extends StringRequest {

    final static private String URL = "http://miraclestep01.dothome.co.kr/Register.php";
    private Map<String, String> map;

    public JoinRequest(String userID, String userPassword, String userName, String UserPhoneNumber,
                       int userHeight, int userWeight, String userBirth, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        // Request까지는 잘 넘어옴
        Log.d("id", userID);
        Log.d("name", userName);
        Log.d("height", userHeight+"");

        map = new HashMap<>();
        map.put("id", userID);
        map.put("pw", userPassword);
        map.put("name", userName);
        map.put("phoneNumber", UserPhoneNumber);
        map.put("birth", userBirth);
        map.put("height", userHeight+"");
        map.put("weight", userWeight+"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}