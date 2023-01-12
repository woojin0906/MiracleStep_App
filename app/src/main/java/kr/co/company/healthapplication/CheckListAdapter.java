package kr.co.company.healthapplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// 체크리스트 어댑터 액티비티 (2023-01-12 인범 수정)
public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomViewHolder> {

    private ArrayList<CheckListData> arrayList;
    private HomeActivity home;

    // SharedPreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String rememberID;

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
        holder.tvListNum.setText(arrayList.get(position).getTvListNum());
        holder.ivProfile.setImageResource(arrayList.get(position).getIvProfile());
        holder.tvContent.setText(arrayList.get(position).getTvContent());

        holder.itemView.setTag(position);

        // ListItem 클릭 이벤트 체크/해제
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ListNum = holder.tvListNum.getText().toString();
                home = new HomeActivity();

                Drawable temp = holder.ivProfile.getDrawable();
                Drawable temp1 = home.getContext().getDrawable(R.drawable.checkbox);

                Bitmap tmpBitmap = ((BitmapDrawable)temp).getBitmap();
                Bitmap tmpBitmap1 = ((BitmapDrawable)temp1).getBitmap();

                // 0 - 체크 안된 상태 (체크하고 DB반영)
                if(tmpBitmap.equals(tmpBitmap1)){
                    String Content = holder.tvContent.getText().toString();
                    Toast.makeText(view.getContext(), Content+" 완료!", Toast.LENGTH_SHORT).show();

                    //HomeActivity의 checkListChecked메서드 호출하여 처리
                    home.checkListChecked(ListNum, "1");    // Update 코드 보고 처음부터 다시 작성

                    holder.ivProfile.setImageResource(R.drawable.checkbox);
                    holder.tvContent.setText(Content);
                }
                // 1 - 체크 된 상태 (체크 해제하고 DB반영)
                else{
                    String Content = holder.tvContent.getText().toString();
                    Toast.makeText(view.getContext(), Content+" 실패,,", Toast.LENGTH_SHORT).show();

                    //HomeActivity의 checkListChecked메서드 호출하여 처리
                    home.checkListChecked(ListNum, "0");

                    holder.ivProfile.setImageResource(R.drawable.uncheckbox);
                    holder.tvContent.setText(Content);
                }
            }
        });

//        // item을 길게 눌렀을때 체크 해제
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                String curName = holder.tvContent.getText().toString();
//                Toast.makeText(view.getContext(), curName+" 실패!", Toast.LENGTH_SHORT).show();
//
//                holder.ivProfile.setImageResource(R.drawable.uncheckbox);
//                holder.tvContent.setText(curName);
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvListNum;
        protected ImageView ivProfile;
        protected TextView tvContent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvListNum = (TextView) itemView.findViewById(R.id.tvListNum);
            this.ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            this.tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }

    }

}
