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

// Donation값 서버로 전송 (2023-03-18 우진 수정)
public class DonationInsertRequest extends StringRequest {

    final static private String URL = "http://miraclestep01.dothome.co.kr/InsertDonation.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public DonationInsertRequest(int dNum, String userId, String userStep, Response.Listener<String> listener) {

        // post방식으로 listener를 서버에 전송.
        super(Request.Method.POST, URL, listener, null);

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);

        map = new HashMap<>();
        map.put("dNum", String.valueOf(dNum));
        map.put("userID", userId);
        map.put("userStep", userStep);
        map.put("ddate", String.valueOf(formatedNow));

        Log.d("어디", "인서트");
        Log.d("글번호", String.valueOf(dNum));
        Log.d("아이디", userId);
        Log.d("기부날짜", formatedNow);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
