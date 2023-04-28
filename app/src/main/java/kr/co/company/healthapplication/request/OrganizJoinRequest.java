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

public class OrganizJoinRequest extends StringRequest {
    final static private String URL = "http://miraclestep01.dothome.co.kr/OrganizJoin.php";
    private Map<String, String> map;

    public OrganizJoinRequest(String oID, String oPassword, String oName, String oProposer, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);

        Log.d("request", "hi");

        map = new HashMap<>();
        map.put("id", oID);
        map.put("pw", oPassword);
        map.put("name", oName);
        map.put("proposer", oProposer);
        map.put("joinDate", formatedNow);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
