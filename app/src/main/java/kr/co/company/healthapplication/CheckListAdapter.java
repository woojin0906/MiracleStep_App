package kr.co.company.healthapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import android.util.Log;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.company.healthapplication.request.CheckListCheckedRequest;

// 체크리스트 어댑터 액티비티 (2023-01-12 인범 수정)
public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CustomViewHolder> {

    private ArrayList<CheckListData> arrayList;
    private Activity homeActivity;

    // SharedPreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userID;
    private int checked;

    public CheckListAdapter(ArrayList<CheckListData> arrayList, Activity homeActivity) {
        this.arrayList = arrayList;
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public CheckListAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 리스트가 생성될 때 호출 (생명주기)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_item, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // 리스트가 실제 실행될 때 호출 (arrayList로 부터 가져옴.)
        holder.tvListNum.setText(arrayList.get(position).getTvListNum());
        holder.ivProfile.setImageResource(arrayList.get(position).getIvProfile());
        holder.tvContent.setText(arrayList.get(position).getTvContent());
        userID = arrayList.get(position).getUserID();
        checked = arrayList.get(position).getCheck();

        // 이벤트가 발생했을 때 위치 값을 가져온다. (2023-01-13 이수)
        holder.itemView.setTag(position);

        // ListItem 클릭 이벤트 체크/해제 <- 이게 체크박스 이벤트가 아니라, 리스트 클릭 이벤트 처리....(2023-01-13 이수)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ListNum = holder.tvListNum.getText().toString();


                Log.d("정보", Integer.toString(checked));
                // 0 - 체크 안된 상태 (체크하고 DB반영)
                if(checked == 0){
                    String Content = holder.tvContent.getText().toString();
                    Toast.makeText(view.getContext(), Content+" 완료!", Toast.LENGTH_SHORT).show();

                    checkListChecked(ListNum, "1");    // Update 코드 보고 처음부터 다시 작성
                    arrayList.get(position).setCheck(1);
                    checked = 1;

                    holder.ivProfile.setImageResource(R.drawable.checkbox);
                    //holder.tvContent.setText(Content);
                }
                // 1 - 체크 된 상태 (체크 해제하고 DB반영)
                else if (checked == 1){
                    String Content = holder.tvContent.getText().toString();
                    Toast.makeText(view.getContext(), Content+" 실패,,", Toast.LENGTH_SHORT).show();

                    checkListChecked(ListNum, "0");
                    arrayList.get(position).setCheck(0);
                    checked = 0;
                    holder.ivProfile.setImageResource(R.drawable.uncheckbox);
                }
                else{}
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
//               return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvListNum;       //  할일 체크리스트 (2023-01-13 이수)
        protected ImageView ivProfile;
        protected TextView tvContent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvListNum = (TextView) itemView.findViewById(R.id.tvListNum);
            this.ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);
            this.tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }

    }

    // CheckList클릭 이벤트 처리하는 메서드
    public void checkListChecked(String ListNum, String Checked) { // 버튼 클릭시 리스트번호와 체크상태 가져옴
        // 아이디와 현재날짜(Request)의 리스트번호를 보내서 체크상태를 보고 Checked 업데이트
        // 현재 로그인된 사용자 아이디 가져오기

        //Log.d("로그가 왜 안뜰까?", UserID);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);   // 결과 값을 리턴받음.
                    Log.d("로그", response);

                    boolean success = jsonObject.getBoolean("success"); // php를 통해서 "success"를 전송받음.

                    // 데이터를 정상적으로 처리한 경우
                    if (success) {
                        Log.d("체크리스트", "수정이 완료되었습니다.");
                    }
                    // 데이터를 정상적으로 처리한 경우
                    else {
                        Log.d("체크리스트", "에러가 발생했습니다.");
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        CheckListCheckedRequest checkRequest = new CheckListCheckedRequest(userID, ListNum, Checked, responseListener);
        RequestQueue queue = Volley.newRequestQueue(homeActivity);
        queue.add(checkRequest);
    }

}
