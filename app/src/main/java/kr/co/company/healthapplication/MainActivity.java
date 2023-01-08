package kr.co.company.healthapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private RankActivity rankActivity;
    private MypageActivity mypageActivity;
    private HomeActivity homeActivity;
    private DonationActivity donationActivity;

    private Button buttonRank, buttonRunning, buttonInfo, buttonDonation;
    private ImageButton buttonHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rankActivity = new RankActivity();
        mypageActivity = new MypageActivity();
        homeActivity = new HomeActivity();
        donationActivity = new DonationActivity();

        // 프래그먼트 매니저 획득
        FragmentManager fragmentManager = getSupportFragmentManager();

        //프래그먼트 Transaction 획득
        //프래그먼트를 올리거나 교체하는 작업을 Transaction이라고 합니다.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //프래그먼트를 FrameLayout의 자식으로 등록해줍니다.
        fragmentTransaction.add(R.id.fragmentFrame, homeActivity);
        //commit을 하면 자식으로 등록된 프래그먼트가 화면에 보여집니다.
        fragmentTransaction.commit();

        buttonRank = findViewById(R.id.rankBtn);
        buttonRunning = findViewById(R.id.runningBtn);
        buttonInfo = findViewById(R.id.infoBtn);
        buttonHome = findViewById(R.id.homeBtn);
        buttonDonation = findViewById(R.id.donationBtn);

        buttonRank.setOnClickListener(v -> {
            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ft1 = fragmentManager.beginTransaction();
            ft1.replace(R.id.fragmentFrame, rankActivity);
            ft1.commit();
        });

        buttonRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, RunActivity.class);
                startActivity(intent);
            }
        });

        buttonInfo.setOnClickListener(v -> {
            FragmentManager fm3 = getSupportFragmentManager();
            FragmentTransaction ft3 = fragmentManager.beginTransaction();
            ft3.replace(R.id.fragmentFrame, mypageActivity);
            ft3.commit();
        });

        buttonHome.setOnClickListener(v -> {
            FragmentManager fm4 = getSupportFragmentManager();
            FragmentTransaction ft4 = fragmentManager.beginTransaction();
            ft4.replace(R.id.fragmentFrame, homeActivity);
            ft4.commit();
        });

        buttonDonation.setOnClickListener(v -> {
            FragmentManager fm5 = getSupportFragmentManager();
            FragmentTransaction ft5 = fragmentManager.beginTransaction();
            ft5.replace(R.id.fragmentFrame, donationActivity);
            ft5.commit();
        });

    }
}