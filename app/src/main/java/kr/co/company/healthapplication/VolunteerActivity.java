package kr.co.company.healthapplication;
// 봉사활동 캠페인 액티비티
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class VolunteerActivity extends Fragment {
    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private VolunteerAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<VolunteerData> arrayList;

    EditText edit;
    TextView text;
    XmlPullParser xpp;
    Button button;

    String key="cb09c195e1bf493d89e53541426f7b1f";
    String data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_volunteer, container, false);

        // List 설정
        recyclerView = rootView.findViewById(R.id.rvVolunteerList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        adapter = new VolunteerAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        edit= rootView.findViewById(R.id.edit);
//        text= rootView.findViewById(R.id.result);
        button= rootView.findViewById(R.id.button);

        new Thread() {
            @Override
            public void run() {
                getXmlData();
            }
        }.start();


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
////                        data=getXmlData();
//
//                        ((MainActivity) getContext()).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                text.setText(data);
//                            }
//                        });
//                    }
//                }).start();
//            }
//        });

        return rootView;
   }

    public ArrayList<VolunteerData> getXmlData() {
        // String str= edit.getText().toString(); // 검색어 가져오기
        // String search = URLEncoder.encode(str); // encoding

        ArrayList<VolunteerData> volunteerData = new ArrayList<VolunteerData>();


        String queryUrl="https://openapi.gg.go.kr/ServicPartcptnInfo?KEY="+key+"";
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag, titleName = null, company = null, startDate = null, endDate = null, state = null;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("row")) ;// 첫번째 검색결과
                        else if(tag.equals("SERVIC_TITLE")){
//                            buffer.append("제목 : ");
                            xpp.next();
//                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
                            titleName = xpp.getText();
                        }
                        else if(tag.equals("RECRUT_INST_NM")){
//                            buffer.append("기관 : ");
                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
                            company = xpp.getText();
                        }
                        else if(tag.equals("SERVIC_BEGIN_DE")){
//                            buffer.append("시작일 : ");
                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
                            startDate = xpp.getText();
                        }
                        else if(tag.equals("SERVIC_END_DE")){
//                            buffer.append("종료일 : ");
                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
                            endDate = xpp.getText();
                        }
                        else if(tag.equals("RECRUT_STATE_NM")){
//                            buffer.append("상태 : ");
                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
                            state = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("row")) {
                            VolunteerData entity = new VolunteerData();
                            entity.setTitle(titleName);
                            entity.setCompany(company);
                            entity.setStartDate(startDate);
                            entity.setEndDate(endDate);
                            entity.setState(state);
                            VolunteerData mainData = new VolunteerData(titleName, company, startDate, endDate, state);
                            mainData.setTitle(titleName);
                            mainData.setCompany(company);
                            mainData.setStartDate(startDate);
                            mainData.setEndDate(endDate);
                            mainData.setState(state);
                            arrayList.add(mainData);

                            Log.d(">>>titlename", mainData.getTitle().toString());
//                            buffer.append("\n");
                        }// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

//        buffer.append("파싱 끝\n");
        return volunteerData; //StringBuffer 문자열 객체 반환

    }

}