package kr.co.company.healthapplication;

// 기부캠페인리스트 데이터 액티비티 (2023-01-09 우진 수정)
public class DonationData {
    private String titleName;
    private String name;
    private String nowStep;
    private int ivDonationProfile;

    public int getIvDonationProfile() {
        return ivDonationProfile;
    }

    public void setIvDonationProfile(int ivDonationProfile) {
        this.ivDonationProfile = ivDonationProfile;
    }

    public DonationData() {}  // 생성자 메서드

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNowStep() {
        return nowStep;
    }

    public void setNowStep(String nowStep) {
        this.nowStep = nowStep;
    }

    public DonationData(String titleName, String name, String nowStep, int ivDonationProfile) {
        this.titleName = titleName;
        this.name = name;
        this.nowStep = nowStep;
        this.ivDonationProfile = ivDonationProfile;
    }
}
