package kr.co.company.healthapplication;

import static kr.co.company.healthapplication.CalendarUtils.daysInMonthArray;
import static kr.co.company.healthapplication.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

import kr.co.company.healthapplication.request.CalendarRequest;

// 체크리스트 액티비티 (2023-03-05 이수)
public class CalendarActivity extends Fragment implements CalendarAdapter.OnItemListener {
    // 달력
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button btnPreviousMonthAction, btnNextMonthAction, btnAddScheduleInfo, btnCancelScheduleInfo;
    private FloatingActionButton btnAddSchedule;

    // 리사이클러뷰
    private ArrayList<CalendarCheckListData> arrayList;
    private CalendarCheckListAdapter mainAdapter;
    private RecyclerView rvCheckList;
    private LinearLayoutManager linearLayoutManager;
    private View layoutChecklist;

    // 일정 추가
    private LinearLayout layoutAddSchedule;
    private TextView tvSelectDate;

    // SharedPreference
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static String UserID;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_calendar, container, false);

        // 현재 로그인된 사용자 아이디 가져오기
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        UserID = pref.getString("UserID", "_");

        // 이전 달 달력
        btnPreviousMonthAction = rootView.findViewById(R.id.btnPreviousMonthAction);
        btnPreviousMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        // 다음 달 달력
        btnNextMonthAction = rootView.findViewById(R.id.btnNextMonthAction);
        btnNextMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
                setMonthView();
            }
        });
        initWidgets(rootView);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        // 일정 추가 버튼
        layoutAddSchedule = rootView.findViewById(R.id.layoutAddSchedule);
        layoutChecklist = rootView.findViewById(R.id.layoutChecklist);
        tvSelectDate = rootView.findViewById(R.id.tvSelectDate);
        btnAddSchedule = rootView.findViewById(R.id.btnAddSchedule);
        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               layoutAddSchedule.setVisibility(v.VISIBLE);
               btnAddSchedule.setClickable(false);
               String date = String.valueOf(CalendarUtils.selectedDate);
               tvSelectDate.setText(date.substring(0, 4)+"년 "+date.substring(5, 7)+"월 "+date.substring(8, 10)+"일");
           }
       });

        btnAddScheduleInfo = rootView.findViewById(R.id.btnAddScheduleInfo);
        btnAddScheduleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddSchedule.setVisibility(v.INVISIBLE);
                layoutChecklist.setVisibility(v.VISIBLE);
                btnAddSchedule.setClickable(true);
            }
        });

        btnCancelScheduleInfo = rootView.findViewById(R.id.btnCancelScheduleInfo);
        btnCancelScheduleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddSchedule.setVisibility(v.INVISIBLE);
                layoutChecklist.setVisibility(v.VISIBLE);
                btnAddSchedule.setClickable(true);
            }
        });

        // List 설정
        rvCheckList = (RecyclerView) rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCheckList.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new CalendarCheckListAdapter(arrayList, getActivity());
        rvCheckList.setAdapter(mainAdapter);

        // checkList 호출 메서드
        selectCheckList();

        return rootView;
    }

    // 체크리스트 가져오는 메서드
    private void selectCheckList() {
        Log.d("여기 지나가요. 1111","hello");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 결과 값을 리턴받음.
                    JSONObject jsonObject;

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        int listIndex = jsonObject.getInt("listIndex");
                        int checked = jsonObject.getInt("checked");
                        String content = jsonObject.getString("content");

                        Log.d("에러 ListNum", listIndex+"");
                        Log.d("에러 Checked", checked+"");
                        Log.d("에러 CheckContent", content);

                        // checked = 0 (체크안됨)
                        // checked = 1 (체크됨)
                        CalendarCheckListData mainData;
                        if(checked==0){
                            mainData = new CalendarCheckListData(checked, UserID, listIndex+"", R.drawable.uncheckbox, content, String.valueOf(CalendarUtils.selectedDate));
                        }else{
                            mainData = new CalendarCheckListData(checked, UserID, listIndex+"", R.drawable.checkbox, content, String.valueOf(CalendarUtils.selectedDate));
                        }
                        arrayList.add(mainData);
                    }

                    // 불러오기 전에 데이터(아이템) 초기화 해줘야 중첩 안됨.
                    mainAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d("여기 지나가요. 7777","hello");
                    e.printStackTrace();
                }
            }
        };
        CalendarRequest calendarRequest = new CalendarRequest(UserID, String.valueOf(CalendarUtils.selectedDate) , responseListener);
        Log.d("여기 지나가요. 3333", String.valueOf(calendarRequest));
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.d("여기 지나가요. 4444","hello");
        queue.add(calendarRequest);
        Log.d("여기 지나가요. 5555", String.valueOf(queue));
    }

    private void initWidgets(ViewGroup rootView) {
        calendarRecyclerView = rootView.findViewById(R.id.rvCalendar);
        monthYearText = rootView.findViewById(R.id.tvMonthYear);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        if (date != null) {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(getContext(), CalendarActivity.class));
    }
}