package kr.co.company.healthapplication.activity.calendar;

import static kr.co.company.healthapplication.activity.calendar.CalendarUtils.daysInMonthArray;
import static kr.co.company.healthapplication.activity.calendar.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import kr.co.company.healthapplication.R;
import kr.co.company.healthapplication.request.calendar.SelectCalendarCheckListRequest;
import kr.co.company.healthapplication.request.calendar.InsertCheckListRequest;

// 2023-03-22 이수 작성.
public class CalendarActivity extends Fragment implements CalendarAdapter.OnItemListener {
    // 달력
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button btnPreviousMonthAction, btnNextMonthAction, btnAddScheduleInfo, btnCancelScheduleInfo;
    private FloatingActionButton btnAddSchedule;

    // 리사이클러뷰
    private ArrayList<CalendarCheckListData> array_CheckList;
    private CalendarCheckListAdapter calendarCheckListAdapter;
    private RecyclerView rvCheckList;
    private LinearLayoutManager linearLayoutManager;
    private View layoutChecklist;

    // 일정 추가
    private LinearLayout layoutAddSchedule;
    private TextView tvSelectDate;
    private EditText etScheduleTitle;

    // SharedPreference
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static String userID;

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_calendar, container, false);

        userID = getUserID();

        initWidgets(rootView);
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        selectCheckList();

        return rootView;
    }

    private String getUserID() {
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();pref.getString("UserID", "_");
        return pref.getString("UserID", "_");
    }

    private void selectCheckList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    array_CheckList = new ArrayList<>();
                    calendarCheckListAdapter = new CalendarCheckListAdapter(array_CheckList, getActivity(), String.valueOf(CalendarUtils.selectedDate));
                    rvCheckList.setAdapter(calendarCheckListAdapter);

                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject;

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        int listIndex = jsonObject.getInt("listIndex");
                        int checked = jsonObject.getInt("checked");
                        String content = jsonObject.getString("content");
                        CalendarCheckListData mainData;
                        if(checked==0)
                            mainData = new CalendarCheckListData(checked, userID, listIndex+"", R.drawable.uncheckbox, content, String.valueOf(CalendarUtils.selectedDate));
                        else
                            mainData = new CalendarCheckListData(checked, userID, listIndex+"", R.drawable.checkbox, content, String.valueOf(CalendarUtils.selectedDate));
                        array_CheckList.add(mainData);
                    }
                    calendarCheckListAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        SelectCalendarCheckListRequest calendarRequest = new SelectCalendarCheckListRequest(userID, String.valueOf(CalendarUtils.selectedDate) , responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(calendarRequest);
    }

    private void insertCheckList(String date, String scheduleTitle) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getActivity(), "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        selectCheckList();
                    }
                    else {
                        Toast.makeText(getActivity(), "일정 저장이 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "관리자에게 문의 부탁드립니다.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        };
        InsertCheckListRequest insertCheckListRequest = new InsertCheckListRequest(userID, date, scheduleTitle, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(insertCheckListRequest);
    }

    private void initWidgets(ViewGroup rootView) {
        calendarRecyclerView = rootView.findViewById(R.id.rvCalendar);
        monthYearText = rootView.findViewById(R.id.tvMonthYear);

        btnPreviousMonthAction = rootView.findViewById(R.id.btnPreviousMonthAction);
        btnPreviousMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
                selectCheckList();
            }
        });

        btnNextMonthAction = rootView.findViewById(R.id.btnNextMonthAction);
        btnNextMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
                setMonthView();
                selectCheckList();
            }
        });

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

        etScheduleTitle = rootView.findViewById(R.id.etScheduleTitle);
        btnAddScheduleInfo = rootView.findViewById(R.id.btnAddScheduleInfo);
        btnAddScheduleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1 = String.valueOf(CalendarUtils.selectedDate);
                String scheduleTitle = etScheduleTitle.getText().toString();
                if(!scheduleTitle.equals("")) {
                    insertCheckList(date1, scheduleTitle);
                    etScheduleTitle.setText("");
                    layoutAddSchedule.setVisibility(v.INVISIBLE);
                    layoutChecklist.setVisibility(v.VISIBLE);
                    btnAddSchedule.setClickable(true);
                }
                else
                    Toast.makeText(getActivity(), "일정을 작성해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelScheduleInfo = rootView.findViewById(R.id.btnCancelScheduleInfo);
        btnCancelScheduleInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAddSchedule.setVisibility(v.INVISIBLE);
                layoutChecklist.setVisibility(v.VISIBLE);
                btnAddSchedule.setClickable(true);
                etScheduleTitle.setText("");
            }
        });

        // List 설정
        rvCheckList = (RecyclerView) rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvCheckList.setLayoutManager(linearLayoutManager);
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
            selectCheckList();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(getContext(), CalendarActivity.class));
    }
}