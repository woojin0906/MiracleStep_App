package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

// Donation값 서버로 전송 (2023-01-11 우진 생성)
public class DonationUpdateRequest extends StringRequest {
    final static private String URL = "http://miraclestep.ivyro.net/UpdateDonation.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public DonationUpdateRequest(int dNum, int updateStep, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("dNum", String.valueOf(dNum));
        map.put("updateStep", String.valueOf(updateStep));

        Log.d("어디", "업데이트");
        Log.d("글 번호", String.valueOf(dNum));
        Log.d("업데이트된 걸음", String.valueOf(updateStep));

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}