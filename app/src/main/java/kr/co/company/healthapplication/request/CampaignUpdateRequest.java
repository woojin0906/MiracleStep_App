package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

// User값 서버로 전송 (2023-03-18 우진 수정)
public class CampaignUpdateRequest extends StringRequest {

    final static private String URL = "http://miraclestep01.dothome.co.kr/CampaignUpdate.php"; // 서버 URL 설정 (PHP 파일 연동.)
    private Map<String, String> map;

    public CampaignUpdateRequest(int rNum, String titleName, String startdate, String date, int maxStep, String content, String ivDonationProfile, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("dNum", String.valueOf(rNum));
        map.put("titleName", titleName);
        map.put("startdate", startdate);
        map.put("date", date);
        map.put("content", content);
        map.put("maxStep", String.valueOf(maxStep));
        map.put("contentImage", ivDonationProfile);

        Log.d("어디", "업데이트");
        Log.d("번호", String.valueOf(rNum));
        Log.d("제목", titleName);
        Log.d("시작날짜", startdate);
        Log.d("종료날짜", date);
        Log.d("내용", content);
        Log.d("최대 걸음 수", String.valueOf(maxStep));
        Log.d("이미지", ivDonationProfile);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
