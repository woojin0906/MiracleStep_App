package kr.co.company.healthapplication;

// 랭크리스트 데이터 액티비티 (2023-01-05 인범 생성)
public class RankListData {

    private String tvUserRank;
    private int ivUserProfile;
    private String tvUserId;
    private String tvUserPoint;

    public RankListData(String tvUserRank, int ivUserProfile, String tvUserId, String tvUserPoint) {
        this.tvUserRank = tvUserRank;
        this.ivUserProfile = ivUserProfile;
        this.tvUserId = tvUserId;
        this.tvUserPoint = tvUserPoint;
    }

    public String getTvUserRank() {
        return tvUserRank;
    }

    public void setTvUserRank(String tvUserRank) {
        this.tvUserRank = tvUserRank;
    }

    public int getIvUserProfile() {
        return ivUserProfile;
    }

    public void setIvUserProfile(int ivUserProfile) {
        this.ivUserProfile = ivUserProfile;
    }

    public String getTvUserId() {
        return tvUserId;
    }

    public void setTvUserId(String tvUserId) {
        this.tvUserId = tvUserId;
    }

    public String getTvUserPoint() {
        return tvUserPoint;
    }

    public void setTvUserPoint(String tvUserPoint) {
        this.tvUserPoint = tvUserPoint;
    }
}
