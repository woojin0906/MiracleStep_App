package kr.co.company.healthapplication;

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
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// 랭크 액티비티 (2023-01-06 인범 수정)
public class RankActivity extends Fragment {

    // 리사이클러뷰
    private ArrayList<RankListData> arrayList;
    private RankListAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    // 랭킹 버튼
    private Button btnTotalRank, btnDonaRank, btnStepRank;
    private TextView tvRankTitle, tvRankStep;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_rank, container, false);

        // List 설정
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvRankList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new RankListAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        // list값 세팅
        for(int i=1;i<=100;i++){
            RankListData mainData = new RankListData(""+i, R.drawable.user, "Leeinbeom", ""+24397);
            arrayList.add(mainData);
        }
        mainAdapter.notifyDataSetChanged();


        // 랭킹 버튼 클릭 이벤트
        tvRankTitle = rootView.findViewById(R.id.tvRankTitle);
        tvRankStep = rootView.findViewById(R.id.tvRankStep);

        btnTotalRank = rootView.findViewById(R.id.btnTotalRank);
        btnTotalRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 전체버튼 클릭 시
                tvRankTitle.setText("전체 사용자 랭킹");
                tvRankStep.setText("점");

            }
        });

        btnDonaRank = rootView.findViewById(R.id.btnDonaRank);
        btnDonaRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 기부버튼 클릭 시
                tvRankTitle.setText("기부 사용자 랭킹");
                tvRankStep.setText("원");

            }
        });

        btnStepRank = rootView.findViewById(R.id.btnStepRank);
        btnStepRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 걸음버튼 클릭 시
                tvRankTitle.setText("걷기 사용자 랭킹");
                tvRankStep.setText("걸음");

            }
        });

        return rootView;
    }
}