package kr.co.company.healthapplication;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

// 기부캠페인 어댑터 (2023-03-21 우진 수정)
public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.CustomViewHolder> {

    private ArrayList<DonationData> arrayList;

    public CampaignAdapter(ArrayList<DonationData> arrayList) {
        this.arrayList = arrayList;
    }

    // list_item에 대한 최초의 뷰 생성
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donation, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    // 실제 아이템 매칭 역할
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String nowStep = myFormatter.format(Integer.parseInt(arrayList.get(position).getNowStep()));

        holder.titleName.setText(arrayList.get(position).getTitleName());
        holder.name.setText(arrayList.get(position).getName());
        holder.nowStep.setText(nowStep);
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getIvDonationProfile())
                .into(holder.ivDonationProfile);

        // 리사이클러뷰 클릭이벤트(선택된 리사이클러뷰 화면으로 이동)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();

                String postMaxStep = myFormatter.format(Integer.parseInt(arrayList.get(mPosition).getMaxStep()));
                String postNowStep = myFormatter.format(Integer.parseInt(arrayList.get(mPosition).getNowStep()));

                Intent intent = new Intent(context, CampaignPostActivity.class);
                intent.putExtra("titleName", arrayList.get(mPosition).getTitleName());
                intent.putExtra("name", arrayList.get(mPosition).getName());
                intent.putExtra("nowStep", postNowStep);
                //intent.putExtra("ivDonationProfile", arrayList.get(mPosition).getIvDonationProfile());
                intent.putExtra("date", arrayList.get(mPosition).getDate());
                intent.putExtra("startDate", arrayList.get(mPosition).getStartDate());
                intent.putExtra("maxStep", postMaxStep);
                intent.putExtra("content", arrayList.get(mPosition).getContent());
                intent.putExtra("category", arrayList.get(mPosition).getCategory());
                intent.putExtra("dNum", arrayList.get(mPosition).getdNum());

                (context).startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView titleName, name, nowStep;
        ImageView ivDonationProfile;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.titleName=view.findViewById(R.id.titleName);
            this.name=view.findViewById(R.id.name);
            this.nowStep=view.findViewById(R.id.nowStep);
            this.ivDonationProfile=(ImageView) view.findViewById(R.id.ivDonationProfile);

        }
    }
}

