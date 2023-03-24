package kr.co.company.healthapplication;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kr.co.company.healthapplication.request.run.InsertRunRequest;
import kr.co.company.healthapplication.request.run.SelectRunRequest;
import kr.co.company.healthapplication.request.run.UpdateRunRequest;

public class RunActivity extends AppCompatActivity implements SensorEventListener, TMapGpsManager.onLocationChangedCallback {
    // 날씨
    public static String weather121313 = "현재 날씨는 맑은 상태입니다.";
    private double longitude = 37.4481;    // 인하공전 경도
    private double latitude = 126.6585;    // 인하공전 위도
    private static String weatherResult = "";  // 날씨정보
    private ImageView ivWeather;
    private TextView tvTemperatures, tvWeather;
    private String baseDate;            // 조회하고 싶은 날짜
    private String baseTime;            // 조회하고 싶은 시간
    private String weather;             // 날씨 결과
    private String tmperature;

    // T Map
    private String API_Key = "l7xx355994c1c51e45aab7455eafae8ed50f";
    private TMapView tMapView = null;
    private TMapGpsManager tMapGPS = null;
    private ArrayList<TMapPoint> alTMapPoint = new ArrayList<TMapPoint>();

    // 걸음수
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private TextView tvStepCount;
    private static int currentSteps = 0;
    private double countKcal=0.0;
    private TextView tvDistance;
    private TextView tvKcal;
    private int result = 0;

    // 스톱워치(시간)
    private Button startBtn, stopBtn;
    private Chronometer chrono;
    private long pauseOffset;
    private boolean running;
    private int m; // 시간(분)

    // 운동 기록
    private int runTime;
    private double runDistance;
    private int runStepCount;
    private double runKcal;
    private String returnID;
    private String returnRunDate;

    // Preferences Shared
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userId;


    // 거리계산 식
    private int count = 0;
    private double[] lon = new double[1000];
    private double[] lat = new double[1000];
    private double total = 0.0;
    boolean stateRunningTable;

    @Override
    public void onBackPressed() {
        stopRunning();
        getWalkingInfo();
        savingWalkingInfo();

        finish();
    }

    private void savingWalkingInfo() {
        if(returnRunDate != null)
            updateRunning();
        else
            insertRunning();

    }

    private void getWalkingInfo() {
        runTime += runTime + m;
        runDistance += Double.parseDouble(tvDistance.getText().toString().replaceAll("km", ""));
        runStepCount += Integer.parseInt(tvStepCount.getText().toString());
        runKcal += Double.parseDouble(tvKcal.getText().toString().replaceAll("kcal", ""));
    }

    private void stopRunning() {
        result = 0;
        chrono.stop();
        pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
        running = false;
    }

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        // 활동 퍼미션 체크
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        // 걸음 센서 연결
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(this, "No Step Sensor", Toast.LENGTH_SHORT).show();
        }
        initWidgets();
        selectRunning();
        weatherSetting();
        tmapSetting();
        stopwatchSetting();
    }

    private void stopwatchSetting() {
        // 스톱워치(시간)
        chrono = findViewById(R.id.chrono);
        chrono.setFormat("%s");

        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);

        //시작버튼
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = 1;
                if(!running){
                    chrono.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chrono.start();
                    running = true;
                }
            }
        });

        //정지버튼
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = 0;
                chrono.stop();
                pauseOffset = SystemClock.elapsedRealtime() - chrono.getBase();
                running = false;
            }
        });

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h   = (int)(time /3600000);
                m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String hh = h < 10 ? "0"+h: h+"";
                String mm = m < 10 ? "0"+m: m+"";
                String ss = s < 10 ? "0"+s: s+"";
                chrono.setText(hh+":"+mm+":"+ss);
            }
        });
    }

    private void tmapSetting() {
        // T Map
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(API_Key);

        // Initial Setting
        tMapView.setZoomLevel(16);
        tMapView.setIconVisibility(true);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);

        // T Map View Using Linear Layout
        LinearLayout linearLayoutTmap = findViewById(R.id.linearLayoutTmap);
        linearLayoutTmap.addView(tMapView);

        // GPS using T Map
        tMapGPS = new TMapGpsManager(this);

        // Initial Setting
        tMapGPS.setMinTime(100);    // 일정 시간마다 리셋
        tMapGPS.setMinDistance(1);  // 일정 거리마다 리셋s
        //tMapGPS.setProvider(tMapGPS.NETWORK_PROVIDER); //네트워크
        tMapGPS.setProvider(tMapGPS.GPS_PROVIDER);       //GPS

        // 화면중심을 단말의 현재위치로 이동
        tMapView.setTrackingMode(true);
        tMapView.setSightVisible(true);

        tMapGPS.OpenGps();
    }

    private void exerciseSetting() {
    }

    private void weatherSetting() {
        new Thread(() -> {
            try {
                weatherResult = lookUpWeather(longitude, latitude);
                Log.d("날씨정보",weatherResult);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

        // 날씨
        int beginIndex = weatherResult.lastIndexOf(",") + 1;
        int endIndex = weatherResult.length();
        // 혹시 모를 에러 처리하기!!
        if (beginIndex != 0) {
            Log.d("정보", String.valueOf(beginIndex));
            String temperatures = weatherResult.substring(beginIndex, endIndex);    // 기온
            String weather = weatherResult.substring(0, (beginIndex - 1));    // 날씨
            tvTemperatures.setText(temperatures);
            tvWeather.setText(weather);
            if(!weather121313.equals(weather)) {
                // 날씨에 따라 이미지 변경
                if (weather.equals("현재 날씨는 맑은 상태입니다.")) {
                    Glide.with(ivWeather).load(R.mipmap.sun).into(ivWeather);
                    weather121313 = "현재 날씨는 맑은 상태입니다.";
                    ivWeather.setImageResource(R.mipmap.sun);
                } else if (weather.equals("현재 날씨는 비가 오는 상태입니다.")) {
                    Glide.with(ivWeather).load(R.mipmap.rain).into(ivWeather);
                    ivWeather.setImageResource(R.mipmap.rain);
                    weather121313 = "현재 날씨는 비가 오는 상태입니다.";
                } else if (weather.equals("현재 날씨는 구름이 많은 상태입니다.")) {
                    Glide.with(ivWeather).load(R.mipmap.cloudy).into(ivWeather);
                    ivWeather.setImageResource(R.mipmap.cloudy);
                    weather121313 = "현재 날씨는 구름이 많은 상태입니다.";
                } else if (weather.equals("현재 날씨는 흐린 상태입니다.")) {
                    Glide.with(ivWeather).load(R.mipmap.clouds).into(ivWeather);
                    ivWeather.setImageResource(R.mipmap.clouds);
                    weather121313 = "현재 날씨는 흐린 상태입니다.";
                }
            }
        }
    }

    private void initWidgets() {
        tvStepCount = findViewById(R.id.tvStepCount);
        tvDistance = findViewById(R.id.tvDistance);
        tvKcal = findViewById(R.id.tvKcal);

        ivWeather = findViewById(R.id.ivWeather);
        tvTemperatures = findViewById(R.id.tvTemperatures);
        tvWeather = findViewById(R.id.tvWeather);

        Glide.with(this).load(R.mipmap.sun).into(ivWeather);
        userId = getUserID();
    }

    private String getUserID() {
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();pref.getString("UserID", "_");
        return pref.getString("UserID", "_");
    }

    private void selectRunning() {
        // 유저의 러닝테이블 정보 가져오기. (2023-01-09 이수)
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                        JSONObject jsonObject = new JSONObject(response);
                        stateRunningTable =jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                        if (stateRunningTable) {
                            returnID = jsonObject.getString("userID");
                            returnRunDate = jsonObject.getString("runDate");
                            runTime = jsonObject.optInt("runTime", 0);
                            runDistance = jsonObject.optDouble("runDistance", 0.0);
                            runStepCount = jsonObject.optInt("runStep", 0);
                            runKcal = jsonObject.optDouble("runKcal", 0.0);
                       }  else
                            Toast.makeText(getApplicationContext(),"러닝정보를 확인하지 못했습니다.", Toast.LENGTH_SHORT).show();

                    } catch(Exception e){
                        e.printStackTrace();
                        runTime = 0;
                        runDistance = 0.0;
                        runStepCount = 0;
                        runKcal = 0.0;
                    }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        SelectRunRequest runSelectRequest = new SelectRunRequest(userId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RunActivity.this);
        queue.add(runSelectRequest);
    }

    private void updateRunning() {
        //3. 기록 DB에 저장
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);
                    Toast.makeText(getApplicationContext(), "운동기록이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "운동기록 저장이 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        UpdateRunRequest runRequest = new UpdateRunRequest(userId, Integer.toString(runTime), Double.toString(runDistance), Integer.toString(runStepCount), Double.toString(runKcal), responseListener);
        RequestQueue queue = Volley.newRequestQueue(RunActivity.this);
        queue.add(runRequest);
    }

    private void insertRunning() {
        //3. 기록 DB에 저장
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.
                    String jsonString = jsonObject.toString();
                    Log.d("전송여부", jsonString);

                    // 운동기록 저장 성공인 경우.
                    if (success) {
                        Toast.makeText(getApplicationContext(), "운동기록 저장 완료.", Toast.LENGTH_SHORT).show();
                    }
                    // 운동기록 저장 실패인 경우.
                    else {
                        Toast.makeText(getApplicationContext(), "운동기록 저장 실패.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 서버로 Volley를 이용해서 요청을 함.
        InsertRunRequest runRequest = new InsertRunRequest(userId, Integer.toString(runTime), Double.toString(runDistance), Integer.toString(runStepCount), Double.toString(runKcal), responseListener);
        RequestQueue queue = Volley.newRequestQueue(RunActivity.this);
        queue.add(runRequest);
    }

    //걸음수
    public void onStart() {
        super.onStart();
        if(stepCountSensor !=null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            //
            sensorManager.registerListener((SensorEventListener) this,stepCountSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // 걸음 센서 이벤트 발생시
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(result == 1) {
                if (event.values[0] == 1.0f) {
                    // 센서 이벤트가 발생할때 마다 걸음수 증가
                    currentSteps++;
                    tvStepCount.setText(String.valueOf(currentSteps));
                    countKcal = currentSteps * 0.04;
                    tvKcal.setText((String.format("%.2f", countKcal) + "kcal"));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // 지속적으로 위치를 받아와 설정해줌
    @Override
    public void onLocationChange(Location location) {
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        tMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
        double Longitude = location.getLongitude(); //경도
        double Latitude = location.getLatitude();   //위도
        alTMapPoint.add( new TMapPoint(Latitude, Longitude)); //가져온 경도,위도를 Point에 추가

        TMapPolyLine tMapPolyLine = new TMapPolyLine();
        tMapPolyLine.setLineColor(Color.RED);
        tMapPolyLine.setLineWidth(10);
        for( int i=0; i<alTMapPoint.size(); i++ ) {
            tMapPolyLine.addLinePoint( alTMapPoint.get(i) );
        }
        tMapView.addTMapPolyLine("Line", tMapPolyLine); // point값을 polyLine로 그림


        if(count == 0){
            lon[0] = Longitude;
            lat[0] = Latitude;
            lon[1] = Longitude;
            lat[1] = Latitude;
        }else{
            lon[count] = Longitude;     // count로 매번 포인트마다 위도/경도를 대입
            lat[count] = Latitude;
            double d2r = (Math.PI / 180D);
            double dlong = (lon[count] - lon[count-1]) * d2r;
            double dlat = (lat[count] - lat[count-1]) * d2r;
            double a = pow(sin(dlat/2.0), 2) + cos(lat[count-1]*d2r) * cos(lat[count]*d2r) * pow(sin(dlong/2.0), 2);
            double c = 2 * atan2(sqrt(a), sqrt(1-a));
            double d = 6367 * c;

            total += d;
            tvDistance.setText((String.format("%.2f", total)+"km"));    // km단위로 거리 출력
        }
        count++;

    }

    // 날씨 구하는 메서드
    public String lookUpWeather(double dx, double dy) throws IOException, JSONException {
        // 현재 위치 필드 저장
        int ix = (int) dx;
        int iy = (int) dy;
        String nx = String.valueOf(ix);
        String ny = String.valueOf(iy);
        Log.i("날씨: 위도!!", nx);
        Log.i("날씨: 경도!!", ny);

        // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        LocalTime time = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            time = LocalTime.now();
        }
        baseDate = String.valueOf(date).replaceAll("-", "");
        int correctionDate = Integer.parseInt(baseDate) - 1;     // 날씨 API : 매 시각 45분 이후 호출 // 오전 12시인 경우 사용

        // 시간(30분 단위로 맞추기)
        DateTimeFormatter formatter1 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter1 = DateTimeFormatter.ofPattern("HHmm");
        }
        DateTimeFormatter formatter2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter2 = DateTimeFormatter.ofPattern("HH");
        }

        int itime1 = 0; // 실제 시간
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            itime1 = Integer.parseInt(time.format(formatter1));
        }
        int itime2 = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            itime2 = Integer.parseInt(time.format(formatter2)) - 1;
        }

        //  /*06시30분 발표(30분 단위)*/
        if (itime2 <= 7) {
            itime2 = 23;
            baseDate = String.valueOf(correctionDate);
            baseTime = "2100";
        } else {
            // api가 30분 단위로 업데이트
            if (itime1 % 100 >= 30) baseTime = itime2 + "30";
            else baseTime = itime2 + "00";
        }
        // 오전에는 시간이 3자리로 나옴...
        if (baseTime.length() == 3) {
            baseTime = "0" + baseTime;
        }

        String weatherResult = "현재 날씨를 확인할 수가 없어요.";

        Log.i("날씨: 입력일자!!", baseDate);
        Log.i("날씨: 입력시간!!", baseTime);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=eWD3WU%2B78w6UiyRQFINsKmuNGrDvg3JnKDnefyrBx1jEAGOxNI%2FuFwXB5W7LgsBunL2cQz6OqBLIuJQWDES1SQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시30분 발표(30분 단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점 Y 좌표값*/

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();

        Log.d("정보", result);
        //=======이 밑에 부터는 json에서 데이터 파싱해 오는 부분이다=====//

        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");
        Log.i("ITEMS", items);

        // items로 부터 itemlist 를 받기
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);
            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            if (category.equals("SKY")) {
                weather = "현재 날씨는 ";
                if (fcstValue.equals("1")) {
                    weather += "맑은 상태입니다.";
                } else if (fcstValue.equals("2")) {
                    weather += "비가 오는 상태입니다.";
                } else if (fcstValue.equals("3")) {
                    weather += "구름이 많은 상태입니다.";
                } else if (fcstValue.equals("4")) {
                    weather += "흐린 상태입니다.";
                }
            }

            if (category.equals("T3H") || category.equals("T1H")) {
                if(fcstValue.equals("-99")) {
                    fcstValue="10";
                }
                tmperature = fcstValue + " ℃";
            }
            weatherResult = weather + "," + tmperature;
        }
        Log.i("리턴!!", weatherResult);
        return weatherResult;
    }
}