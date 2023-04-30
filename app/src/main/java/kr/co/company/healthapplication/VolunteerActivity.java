package kr.co.company.healthapplication;
// 봉사활동 캠페인 액티비티
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;


public class VolunteerActivity extends Fragment {
    private RecyclerView recyclerView;
    private VolunteerAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<VolunteerData> arrayList;

    private EditText etSearch;
    private Button btnSearch;
    private String search = "notSearch";

    private String key="cb09c195e1bf493d89e53541426f7b1f";

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

        etSearch= rootView.findViewById(R.id.etSearch);
        btnSearch= rootView.findViewById(R.id.btnSearch);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getXmlData(search);

                ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                                text.setText(data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search = etSearch.getText().toString();

                // 리스트 item초기화
                arrayList = new ArrayList<>();
                adapter = new VolunteerAdapter(arrayList);
                recyclerView.setAdapter(adapter);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData(search);

                        ((MainActivity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
        });

        return rootView;
   }

    public void getXmlData(String search) {
        String queryUrl="https://openapi.gg.go.kr/ServicPartcptnInfo?KEY="+key+"";
        try{
            URL url= new URL(queryUrl); //문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag, titleName = "", company = "", startDate = "", endDate = "", state = "";

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT: // 시작
                        break;
                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("row")); // 첫번째 검색결과
                        else if(tag.equals("SERVIC_TITLE")){ // 제목
                            xpp.next();
                            titleName = xpp.getText();
                        }
                        else if(tag.equals("RECRUT_INST_NM")){ // 기관
                            xpp.next();
                            company = xpp.getText();
                        }
                        else if(tag.equals("SERVIC_BEGIN_DE")){ // 시작일
                            xpp.next();
                            startDate = xpp.getText();
                        }
                        else if(tag.equals("SERVIC_END_DE")){ // 종료일
                            xpp.next();
                            endDate = xpp.getText();
                        }
                        else if(tag.equals("RECRUT_STATE_NM")){ // 상태
                            xpp.next();
                            state = xpp.getText();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("row")) {
                            VolunteerData mainData = new VolunteerData(titleName, company, startDate, endDate, state);

                            // 검색 데이터가 없다면 모두 출력
                            if(search.equals("notSearch")) {
                                arrayList.add(mainData);
                            }
                            else{
                                // 제목에 검색어를 포함하는 정보만 추가
                                if(titleName.contains(search)){
                                    arrayList.add(mainData);
                                }
                            }

                        }// 첫번째 검색결과종료
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

}