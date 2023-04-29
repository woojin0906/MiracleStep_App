package kr.co.company.healthapplication.request;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Campaign값 서버로 전송 (2023-04-29 우진)
public class CampaignWriterRequest extends StringRequest {

    final static private String URL = "http://miraclestep01.dothome.co.kr/CampaignInsert.php";
    private Map<String, String> map;

    public CampaignWriterRequest(String category, String titlename, String name, String startdate, String date,
                      String maxStep, String content, Response.Listener<String> listener){
        // post방식으로 listener를 서버에 전송.
        super(Method.POST, URL, listener, null);

        Log.d("category", category);
        Log.d("titleName", titlename);
        Log.d("name", name);
        Log.d("startdate", startdate);
        Log.d("date", date);
        Log.d("maxStep", maxStep);
        Log.d("content", content);

        map = new HashMap<>();
        map.put("category", category);
        map.put("titleName", titlename);
        map.put("name", name);
        map.put("startdate", startdate);
        map.put("date", date);
        map.put("maxStep", maxStep);
        map.put("content", content);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
