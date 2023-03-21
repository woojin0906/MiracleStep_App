package kr.co.company.healthapplication;
// 봉사활동 캠페인 액티비티 (2023-03-05 우진 생성)
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class VolunteerActivity extends Fragment {

    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Volunteer> arrayList;
    private VolunteerAdapter adapter;
    Volunteer v = null;

    XmlPullParser xpp;
    private String data;
    TextView text;

    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_volunteer, container, false);

        // 스피너 설정
        spinner = rootView.findViewById(R.id.spinner);

        ArrayAdapter arrAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.spinner_array, android.R.layout.simple_spinner_dropdown_item);
        //R.array.spinner_array는 정해놓은 값 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter); //어댑터에 연결해줍니다.

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvVolunteerList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        arrayList = new ArrayList<Volunteer>();
        adapter = new VolunteerAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();

        return rootView;
    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String queryUrl = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrCategoryList?9yPpiG%2BRuqf2HZdGejBfaq3OtNl5MITxR3hqJv5nShg66gl1nJaVjepPF4KIUf169zuRKYnlhS5omW7xmXVb6w%3D%3D";


            try {
                boolean btitleName = false, bgroupName = false, bcategory = false, bstate = false, bcompany = false, brecuDate = false, bpersonCategory = false, bvolunCount = false, bday = false, btime = false, bplace = false, bvolunDate = false, bcontent = false, bmanagerName = false, bmanagerPhone = false, bmanagerAddress = false;

                URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
                InputStream is = url.openStream(); //url위치로 입력스트림 연결

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();//xml파싱을 위한
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

                String tag;

                xpp.next();
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:

                            break;
                        case XmlPullParser.END_DOCUMENT:
                            break;
                        case XmlPullParser.END_TAG:
                            if (xpp.getName().equals("item") && v != null) {
                                arrayList.add(v);
                            }
                            break;
                        case XmlPullParser.START_TAG:
                            if (xpp.getName().equals("item")) {
                                v = new Volunteer();
                            }
                            if (xpp.getName().equals("progrmSj")) btitleName = true;
                            if (xpp.getName().equals("mnnstNm")) bgroupName = true;
                            if (xpp.getName().equals("srvcClCode")) bcategory = true;
                            if (xpp.getName().equals("progrmSttusSe")) bstate = true;
                            if (xpp.getName().equals("nanmmbyNm")) bcompany = true;
                            if (xpp.getName().equals("progrmEndde")) brecuDate = true;
                            if (xpp.getName().equals("adultPosblAt")) bpersonCategory = true;
                            if (xpp.getName().equals("rcritNmpr")) bvolunCount = true;
                            if (xpp.getName().equals("actWkdy")) bday = true;
                            if (xpp.getName().equals("actBeginTm")) btime = true;
                            if (xpp.getName().equals("actPlace")) bplace = true;
                            if (xpp.getName().equals("progrmBgnde")) bvolunDate = true;
                            if (xpp.getName().equals("progrmCn")) bcontent = true;
                            if (xpp.getName().equals("nanmmbyNmAdmn")) bmanagerName = true;
                            if (xpp.getName().equals("telno")) bmanagerPhone = true;
                            if (xpp.getName().equals("postAdres")) bmanagerAddress = true;
                            break;

                        case XmlPullParser.TEXT:
                            if (btitleName == true) {
                                v.setTitleName(xpp.getText());
                                btitleName = false;
                            } else if (bgroupName == true) {
                                v.setGroupName(xpp.getText());
                                bgroupName = false;
                            } else if (bcategory == true) {
                                v.setCategory(xpp.getText());
                                bcategory = false;
                            } else if (bstate == true) {
                                v.setState(xpp.getText());
                                bstate = false;
                            } else if (bcompany == true) {
                                v.setCompany(xpp.getText());
                                bcompany = false;
                            } else if (brecuDate == true) {
                                v.setRecuDate(xpp.getText());
                                brecuDate = false;
                            } else if (bpersonCategory == true) {
                                v.setPersonCategory(xpp.getText());
                                bpersonCategory = false;
                            } else if (bvolunCount == true) {
                                v.setVolunCount(Integer.parseInt(xpp.getText()));
                                bvolunCount = false;
                            } else if (bday == true) {
                                v.setDay(xpp.getText());
                                bday = false;
                            } else if (btime == true) {
                                v.setTime(xpp.getText());
                                btime = false;
                            } else if (bplace == true) {
                                v.setPlace(xpp.getText());
                                bplace = false;
                            } else if (bvolunDate == true) {
                                v.setVolunDate(xpp.getText());
                                bvolunDate = false;
                            } else if (bcontent == true) {
                                v.setContent(xpp.getText());
                                bcontent = false;
                            } else if (bmanagerName == true) {
                                v.setManagerName(xpp.getText());
                                bmanagerName = false;
                            } else if (bmanagerPhone == true) {
                                v.setManagerPhone(xpp.getText());
                                bmanagerPhone = false;
                            } else if (bmanagerAddress == true) {
                                v.setManagerAddress(xpp.getText());
                                bmanagerAddress = false;
                            }
                            break;
                    }
                    eventType = xpp.next();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}