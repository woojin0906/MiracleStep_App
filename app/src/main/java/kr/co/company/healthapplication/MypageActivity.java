package kr.co.company.healthapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/* 신체정보 변경(키, 체중 등등), 회원정보 변경, 기부 신청내역 */

public class MypageActivity extends Fragment {

    private Button btnLogOut;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_mypage,container,false);
        btnLogOut = rootView.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();
                editor.remove("UserID").apply();
                Toast.makeText(getActivity().getApplicationContext(),"로그아웃에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootView;
    }
}