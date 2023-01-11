package kr.co.company.healthapplication;

// 기부캠페인리스트 데이터 액티비티 (2023-01-09 우진 수정)
public class DonationReceiptsData {
    private String titleName;
    private String groupName;
    private String donationDate;
    private int donationStep;
    private int ivDonationProfile;

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonationStep() {
        return String.valueOf(donationStep);
    }

    public void setDonationStep(int donationStep) {
        this.donationStep = donationStep;
    }

    public int getIvDonationProfile() {
        return ivDonationProfile;
    }

    public void setIvDonationProfile(int ivDonationProfile) {
        this.ivDonationProfile = ivDonationProfile;
    }

    public DonationReceiptsData() {}  // 생성자 메서드

    public DonationReceiptsData(String titleName, String groupName, String donationDate, int donationStep, int ivDonationProfile) {
        this.titleName = titleName;
        this.groupName = groupName;
        this.donationDate = donationDate;
        this.donationStep = donationStep;
        this.ivDonationProfile = ivDonationProfile;
    }
}
