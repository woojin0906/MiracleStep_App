package kr.co.company.healthapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.company.healthapplication.activity.calendar.CalendarActivity;
import kr.co.company.healthapplication.activity.home.HomeActivity;

// 바텀네비게이션 + 프래그먼트 액티비티 (2023-01-09 우진 수정)
public class MainActivity extends AppCompatActivity {

    // 바텀 네비게이션
    BottomNavigationView bottomNavigationView;

    // 프래그먼트 변수
    Fragment fragment_home;
    Fragment fragment_donation;
    Fragment fragment_rank;
    Fragment fragment_volunteer;
    Fragment fragment_toDoList;
    Fragment fragment_info;

    private long backBtnTime = 0;

    // SharedPreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String rememberID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 프래그먼트 생성
        fragment_home = new HomeActivity();
        fragment_donation = new DonationActivity();
        fragment_volunteer = new VolunteerActivity();
        fragment_toDoList = new CalendarActivity();     // 수정필요 (2023-03-05 이수)
        fragment_info = new MypageActivity();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 프래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_home).commitAllowingStateLoss();

        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.donation:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_donation).commitAllowingStateLoss();
                        return true;
                    case R.id.volunteer:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_volunteer).commitAllowingStateLoss();
                        return true;
                    case R.id.toDoList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_toDoList).commitAllowingStateLoss();
                        return true;
                    case R.id.info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, fragment_info).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}