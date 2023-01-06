package kr.co.company.healthapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

// 랭크리스트 어댑터 액티비티 (2023-01-05 인범 생성)
public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.CustomViewHolder> {

    private ArrayList<RankListData> arrayList;

    public RankListAdapter(ArrayList<RankListData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RankListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // 리스트가 생성될 때 호출 (생명주기)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranklist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankListAdapter.CustomViewHolder holder, int position) {
    // 리스트가 실제 실행될 때 호출
        holder.tvUserRank.setText(arrayList.get(position).getTvUserRank());
        holder.ivUserProfile.setImageResource(arrayList.get(position).getIvUserProfile());
        holder.tvUserId.setText(arrayList.get(position).getTvUserId());
        holder.tvUserPoint.setText(arrayList.get(position).getTvUserPoint());

        holder.itemView.setTag(position);
        // 클릭 이벤트는 필요없음.
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvUserRank;
        protected ImageView ivUserProfile;
        protected TextView tvUserId;
        protected TextView tvUserPoint;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvUserRank = (TextView) itemView.findViewById(R.id.tvUserRank);
            this.ivUserProfile = (ImageView) itemView.findViewById(R.id.ivUserProfile);
            this.tvUserId = (TextView) itemView.findViewById(R.id.tvUserId);
            this.tvUserPoint = (TextView) itemView.findViewById(R.id.tvUserPoint);
        }

    }
}
