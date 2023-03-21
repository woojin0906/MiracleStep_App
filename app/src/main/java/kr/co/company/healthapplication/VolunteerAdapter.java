package kr.co.company.healthapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VolunteerAdapter extends RecyclerView.Adapter<VolunteerAdapter.CustomViewHolder> {

    private ArrayList<Volunteer> arrayList;

    public VolunteerAdapter(ArrayList<Volunteer> arrayList) {
        this.arrayList = arrayList;
    }

    // list_item에 대한 최초의 뷰 생성
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_volunteer, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.titleName.setText(arrayList.get(position).getTitleName());
        holder.name.setText(arrayList.get(position).getGroupName());
        holder.date.setText(arrayList.get(position).getRecuDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mPosition = holder.getAdapterPosition();
                Context context = view.getContext();

                Intent intent = new Intent(context, VolunteerPostActivity.class);

                intent.putExtra("titleName", arrayList.get(mPosition).getTitleName());
                intent.putExtra("name", arrayList.get(mPosition).getGroupName());
                intent.putExtra("category", arrayList.get(mPosition).getCategory());
                intent.putExtra("state", arrayList.get(mPosition).getState());
                intent.putExtra("company", arrayList.get(mPosition).getCompany());
                intent.putExtra("recuDate", arrayList.get(mPosition).getRecuDate());
                intent.putExtra("personCategory", arrayList.get(mPosition).getPersonCategory());
                intent.putExtra("volunCount", arrayList.get(mPosition).getVolunCount());
                intent.putExtra("day", arrayList.get(mPosition).getDay());
                intent.putExtra("time", arrayList.get(mPosition).getTime());
                intent.putExtra("place", arrayList.get(mPosition).getPlace());
                intent.putExtra("volunDate", arrayList.get(mPosition).getVolunDate());
                intent.putExtra("content", arrayList.get(mPosition).getContent());
                intent.putExtra("managerName", arrayList.get(mPosition).getManagerName());
                intent.putExtra("managerPhone", arrayList.get(mPosition).getManagerPhone());
                intent.putExtra("managerAddress", arrayList.get(mPosition).getManagerAddress());

                (context).startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        //삼합 연산자 (arrayList가 널이 아니면 참인 경우 arrayList.size(), 거짓인 경우 0)
        Log.d(">>>>>>" , String.valueOf(arrayList.size()));
        return (arrayList != null ? arrayList.size() : 0);


    }

    // volunteer 모델의 객체들을 list에 저장
    public void setmovieList(ArrayList<Volunteer> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView titleName, name, date;


        public CustomViewHolder(@NonNull View view) {
            super(view);
            this.titleName=view.findViewById(R.id.titleName);
            this.name=view.findViewById(R.id.name);
            this.date=view.findViewById(R.id.date);
        }
    }
}
