package kr.co.company.healthapplication;
// 기부캠페인리스트 데이터 액티비티 (2023-03-21 우진 수정)
public class DonationData {
    private String titleName; // 제목
    private String name; // 기업명
    private String nowStep; // 현재 기부된 걸음 수
    private int ivDonationProfile; // 사진
    private String category; // 카테고리
    private String content; // 내용
    private String date; // 끝나는 기간
    private String startDate; // 시작 기간
    private String maxStep; // 최대 걸음 수
    private String dNum; // 자동 증가 번호

    public DonationData() {}  // 생성자 메서드

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxStep() {
        return maxStep;
    }

    public void setMaxStep(String maxStep) {
        this.maxStep = maxStep;
    }

    public int getIvDonationProfile() {
        return ivDonationProfile;
    }

    public void setIvDonationProfile(int ivDonationProfile) {
        this.ivDonationProfile = ivDonationProfile;
    }

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

    public String getdNum() {
        return dNum;
    }

    public void setdNum(String dNum) {
        this.dNum = dNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public DonationData(String dNum, String titleName, String name, String nowStep, int ivDonationProfile, String content, String date, String startDate, String maxStep) {
        this.dNum = dNum;
        this.titleName = titleName;
        this.name = name;
        this.nowStep = nowStep;
        this.ivDonationProfile = ivDonationProfile;
        this.content = content;
        this.date = date;
        this.startDate = startDate;
        this.maxStep = maxStep;
    }
}
