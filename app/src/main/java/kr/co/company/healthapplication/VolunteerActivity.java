package kr.co.company.healthapplication;
// 봉사활동 캠페인 액티비티 (2023-03-05 우진 생성)
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class VolunteerActivity extends Fragment {

//    private RecyclerView recyclerView;                                  // 리사이클러뷰
//    private DonationAdapter adapter;                               // 리사이클러뷰 어댑터
//    private LinearLayoutManager linearLayoutManager;
//    private ArrayList<DonationData> arrayList;
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
//
//        // List 설정
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvDonationList);
//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        arrayList = new ArrayList<>();




        return rootView;
    }
}