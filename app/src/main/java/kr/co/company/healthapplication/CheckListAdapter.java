package kr.co.company.healthapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 체크리스트 어댑터 액티비티 (2023-01-04 인범 생성)
public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomViewHolder> {

    private ArrayList<CheckListData> arrayList;

    public CheckListAdapter(ArrayList<CheckListData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CheckListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // 리스트가 생성될 때 호출 (생명주기)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.CustomViewHolder holder, int position) {
    // 리스트가 실제 실행될 때 호출
        holder.ivProfile.setImageResource(arrayList.get(position).getIv_profile());
        holder.tvContent.setText(arrayList.get(position).getTv_content());

        holder.itemView.setTag(position);

        // item을 짧게 눌렀을때 체크
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 추후 데이터 연동하여 체크/해체 판단하여 처리
                String curName = holder.tvContent.getText().toString();
                Toast.makeText(view.getContext(), curName+" 완료!", Toast.LENGTH_SHORT).show();

                holder.ivProfile.setImageResource(R.drawable.checkbox);
                holder.tvContent.setText(curName);
            }
        });

        // item을 길게 눌렀을때 체크 해제
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String curName = holder.tvContent.getText().toString();
                Toast.makeText(view.getContext(), curName+" 실패!", Toast.LENGTH_SHORT).show();

                holder.ivProfile.setImageResource(R.drawable.uncheckbox);
                holder.tvContent.setText(curName);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView ivProfile;
        protected TextView tvContent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            this.tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }

    }
}
