package kr.co.company.healthapplication;
// 봉사활동 캠페인 액티비티 (2023-03-05 우진 생성)
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class VolunteerActivity extends Fragment {

    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_volunteer, container, false);
        spinner = rootView.findViewById(R.id.spinner);

//        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_dropdown_item);
//        //R.array.spinner_array는 정해놓은 값 / android.R.layout.simple_spinner_dropdown_item은 기본으로 제공해주는 형식입니다.
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter); //어댑터에 연결해줍니다.
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            } //이 오버라이드 메소드에서 position은 몇번째 값이 클릭됬는지 알 수 있습니다.
//            //getItemAtPosition(position)를 통해서 해당 값을 받아올수있습니다.
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) { }
//        });
        return null;
    }
}