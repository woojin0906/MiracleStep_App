package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private RankActivity rankActivity;
    private RunningActivity runningActivity;
    private Button buttonRank;
    private Button buttonRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rankActivity = new RankActivity();
        runningActivity = new RunningActivity();

        // 프래그먼트 매니저 획득
        FragmentManager fragmentManager = getSupportFragmentManager();

        //프래그먼트 Transaction 획득
        //프래그먼트를 올리거나 교체하는 작업을 Transaction이라고 합니다.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //프래그먼트를 FrameLayout의 자식으로 등록해줍니다.
        fragmentTransaction.add(R.id.fragmentFrame, rankActivity);
        //commit을 하면 자식으로 등록된 프래그먼트가 화면에 보여집니다.
        fragmentTransaction.commit();

        buttonRank = findViewById(R.id.buttonRank);
        buttonRunning = findViewById(R.id.buttonRunning);

        buttonRank.setOnClickListener(v -> {
            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction ft1 = fragmentManager.beginTransaction();
            ft1.replace(R.id.fragmentFrame, rankActivity);
            ft1.commit();
        });

        buttonRunning.setOnClickListener(v -> {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction ft2 = fragmentManager.beginTransaction();
            ft2.replace(R.id.fragmentFrame, runningActivity);
            ft2.commit();
        });

    }
}