package kr.co.company.healthapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// 기부캠페인 어댑터 (2023-01-09 우진 수정)
public class DonationReceiptsAdapter extends RecyclerView.Adapter<DonationReceiptsAdapter.CustomViewHolder1> {

    private ArrayList<DonationReceiptsData> arrayList;

    public DonationReceiptsAdapter(ArrayList<DonationReceiptsData> arrayList) {
        this.arrayList = arrayList;
    }

    // list_item에 대한 최초의 뷰 생성
    @NonNull
    @Override
    public CustomViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_donation_receipt, parent, false);
        CustomViewHolder1 holder = new CustomViewHolder1(view);

        return holder;
    }

    // 실제 아이템 매칭 역할
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder1 holder, int position) {
        holder.titleName.setText(arrayList.get(position).getTitleName());
        holder.groupName.setText(arrayList.get(position).getGroupName());
        holder.donationDate.setText(arrayList.get(position).getDonationDate());
        holder.donationStep.setText(arrayList.get(position).getDonationStep());
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getIvDonationProfile())
                .into(holder.ivDonationProfile);
    }

        /*// 리사이클러뷰 클릭이벤트(선택된 리사이클러뷰 화면으로 이동)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();

                Intent intent = new Intent(context, DonationPostActivity.class);
                intent.putExtra("titleName", arrayList.get(mPosition).getTitleName());
                intent.putExtra("name", arrayList.get(mPosition).getName());
                intent.putExtra("nowStep", arrayList.get(mPosition).getNowStep());
                intent.putExtra("ivDonationProfile", arrayList.get(mPosition).getIvDonationProfile());

                (context).startActivity(intent);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder1 extends RecyclerView.ViewHolder {

        TextView titleName, groupName, donationDate, donationStep;
        ImageView ivDonationProfile;

        public CustomViewHolder1(@NonNull View view) {
            super(view);
            this.titleName = view.findViewById(R.id.tvTitleName);
            this.groupName = view.findViewById(R.id.tvGroupName);
            this.donationDate = view.findViewById(R.id.tvDonationDate);
            this.donationStep = view.findViewById(R.id.tvDonationStep);
            this.ivDonationProfile = (ImageView) view.findViewById(R.id.ivDonationProfile);

        }
    }
}
