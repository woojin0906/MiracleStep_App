package kr.co.company.healthapplication;

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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.company.healthapplication.request.CheckListCheckedRequest;
import kr.co.company.healthapplication.request.HomeRequest;
import kr.co.company.healthapplication.request.UserInfoSelectRequest;

// 홈 액티비티 (2023-01-11 인범 수정.)
public class HomeActivity extends Fragment {

    private ImageView ivRun;

    // 리사이클러뷰
    private ArrayList<CheckListData> arrayList;
    private CheckListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 러닝
    private Button btnRun;

    // SharedPreference
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public static String UserID;


    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_home, container, false);

        // 현재 로그인된 사용자 아이디 가져오기
        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        UserID = pref.getString("UserID", "_");

        // 러닝 버튼
        btnRun = rootView.findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RunActivity.class);
                startActivity(intent);
            }
        });


        // 러닝 GIF
        ivRun = rootView.findViewById(R.id.ivRun);
        Glide.with(this).load(R.raw.run2).into(ivRun);


        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvCheckList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new CheckListAdapter(arrayList, getActivity());
        recyclerView.setAdapter(mainAdapter);

        // checkList메서드 호출
        checkList();

        //mainAdapter.notifyDataSetChanged();

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


}