package kr.co.company.healthapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.ArrayList;

public class DonationReceiptsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;                                  // 리사이클러뷰
    private DonationReceiptsAdapter adapter;                               // 리사이클러뷰 어댑터
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationReceiptsData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_receipts);

        // List 설정
        recyclerView = (RecyclerView) findViewById(R.id.rvDonationReceiptsList);
        linearLayoutManager = new LinearLayoutManager(new DonationReceiptsActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        adapter = new DonationReceiptsAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        // list 값 세팅
        for(int i=1; i<=10; i++) {
            DonationReceiptsData donationData = new DonationReceiptsData("'따뜻한 플레이'"+i, "Google Play"+i, "2023-01-"+i , i, R.drawable.bear2);
            arrayList.add(donationData);
        }
        adapter.notifyDataSetChanged();
    }
}