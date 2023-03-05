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

import kr.co.company.healthapplication.request.HomeRequest;

// 체크리스트 액티비티 (2023-03-05 이수)
public class CalendarActivity extends Fragment implements CalendarAdapter.OnItemListener {
    // 달력
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button btn_previousMonthAction, btn_nextMonthAction, addBtn, cancelBtn;
    private FloatingActionButton btnAdd;

    // 리사이클러뷰
    private ArrayList<CheckListData> arrayList;
    private CheckListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private View layoutChecklist;

    // 일정 추가
    private LinearLayout layoutAdd;

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
        btn_previousMonthAction = rootView.findViewById(R.id.btn_previousMonthAction);
        btn_previousMonthAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
                setMonthView();
            }
        });

        // 다음 달 달력
        btn_nextMonthAction = rootView.findViewById(R.id.btn_nextMonthAction);
        btn_nextMonthAction.setOnClickListener(new View.OnClickListener() {
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
        layoutAdd = rootView.findViewById(R.id.layoutAdd);
        layoutChecklist = rootView.findViewById(R.id.layoutChecklist);
        btnAdd = rootView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               layoutAdd.setVisibility(v.VISIBLE);
               layoutChecklist.setVisibility(v.INVISIBLE);
               btnAdd.setVisibility(v.INVISIBLE);
           }
       });

        addBtn = rootView.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAdd.setVisibility(v.INVISIBLE);
                layoutChecklist.setVisibility(v.VISIBLE);
                btnAdd.setVisibility(v.VISIBLE);
            }
        });

        cancelBtn = rootView.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAdd.setVisibility(v.INVISIBLE);
                layoutChecklist.setVisibility(v.VISIBLE);
                btnAdd.setVisibility(v.VISIBLE);
            }
        });

        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new CheckListAdapter(arrayList, getActivity());
        recyclerView.setAdapter(mainAdapter);

        // checkList메서드 호출
        checkList();

        return rootView;
    }

    // 체크리스트 가져와 뿌려주는 메서드
    private void checkList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);   // 결과 값을 리턴받음.
                    JSONObject jsonObject;

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject = (JSONObject) jsonArray.opt(i);
                        int ListNum = jsonObject.getInt("ListNum");
                        int Checked = jsonObject.getInt("Checked");
                        String CheckContent = jsonObject.getString("CheckContent");

                        Log.d("ListNum", ListNum+"");
                        Log.d("Checked", Checked+"");
                        Log.d("CheckContent", CheckContent);

                        // checked = 0 (체크안됨)
                        // checked = 1 (체크됨)
                        CheckListData mainData;
                        if(Checked==0){
                            mainData = new CheckListData(Checked, UserID, ListNum+"", R.drawable.uncheckbox, CheckContent);
                        }else{
                            mainData = new CheckListData(Checked, UserID, ListNum+"", R.drawable.checkbox, CheckContent);
                        }
                        arrayList.add(mainData);
                    }

                    // 불러오기 전에 데이터(아이템) 초기화 해줘야 중첩 안됨.
                    mainAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        HomeRequest homeRequest = new HomeRequest(UserID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(homeRequest);
    }

    private void initWidgets(ViewGroup rootView) {
        calendarRecyclerView = rootView.findViewById(R.id.rv_calendar);
        monthYearText = rootView.findViewById(R.id.tv_monthYear);
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