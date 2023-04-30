package kr.co.company.healthapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.CustomViewHolder> {

    private ArrayList<VolunteerData> arrayList;

    public VolunteerAdapter(ArrayList<VolunteerData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Log.d(">>>adapter", arrayList.get(position).getTitle());
        holder.titleName.setText(arrayList.get(position).getTitle());
        holder.name.setText(arrayList.get(position).getCompany());
        holder.startDate.setText(arrayList.get(position).getStartDate());
        holder.endDate.setText(arrayList.get(position).getEndDate());
        holder.state.setText(arrayList.get(position).getState());
    }

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView titleName, name, startDate, endDate, state;

        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.titleName=view.findViewById(R.id.titleName);
            this.name=view.findViewById(R.id.name);
            this.startDate=view.findViewById(R.id.startDate);
            this.endDate=view.findViewById(R.id.endDate);
            this.state=view.findViewById(R.id.state);
        }
    }
}
