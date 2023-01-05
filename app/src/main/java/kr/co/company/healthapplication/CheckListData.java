package kr.co.company.healthapplication;

// 체크리스트 데이터 액티비티 (2023-01-04 인범 생성)
public class CheckListData {

    private int ivProfile;
    private String tvContent;

    public CheckListData(int ivProfile, String tvContent) {
        this.ivProfile = ivProfile;
        this.tvContent = tvContent;
    }

    public int getIv_profile() {
        return ivProfile;
    }

    public void setIv_profile(int ivProfile) {
        this.ivProfile = ivProfile;
    }

    public String getTv_content() {
        return tvContent;
    }

    public void setTv_content(String tvContent) {
        this.tvContent = tvContent;
    }
}
