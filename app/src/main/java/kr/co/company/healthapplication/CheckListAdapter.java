package kr.co.company.healthapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import kr.co.company.healthapplication.request.CheckListCheckedRequest;

// 체크리스트 어댑터 액티비티 (2023-01-04 인범 생성)
public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomViewHolder> {

    private ArrayList<CheckListData> arrayList;

    // SharedPreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String rememberID;
    private Context home;


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

        // item을 짧게 눌렀을때 체크
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 추후 데이터 연동하여 체크/해체 판단하여 처리
                String Content = holder.tvContent.getText().toString();
                Toast.makeText(view.getContext(), Content+" 완료!", Toast.LENGTH_SHORT).show();


                holder.ivProfile.setImageResource(R.drawable.checkbox);
                holder.tvContent.setText(Content);
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

    private void checkListChecked(String ListNum, String Checked) { // 버튼 클릭시 리스트번호와 체크상태 가져옴

        // 아이디와 현재날짜(Request)의 리스트번호를 보내서 체크상태를 보고 Checked 업데이트

        // 현재 로그인된 아이디
        HomeActivity home = new HomeActivity();
        pref = home.getPref();
        editor = pref.edit();
        rememberID = pref.getString("UserID", "_");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        };
        CheckListCheckedRequest checkListCheckedRequest = new CheckListCheckedRequest(rememberID, ListNum, Checked, responseListener);
        RequestQueue queue = Volley.newRequestQueue(this.home);
        queue.add(checkListCheckedRequest);

    }
}
