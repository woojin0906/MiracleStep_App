package kr.co.company.healthapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.CustomViewHolder> {

    private ArrayList<DonationInfo> arrayList;
    private Context context;

    public DonationAdapter(ArrayList<DonationInfo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
        holder.titleName.setText(arrayList.get(position).getTitleName());
        holder.name.setText(arrayList.get(position).getName());
        holder.nowStep.setText(arrayList.get(position).getNowStep());

    }

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView titleName, name, nowStep;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.titleName=view.findViewById(R.id.titleName);
            this.name=view.findViewById(R.id.name);
            this.nowStep=view.findViewById(R.id.nowStep);

        }
    }
}
