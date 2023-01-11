package kr.co.company.healthapplication;

import java.util.Date;

// 기부캠페인리스트 데이터 액티비티 (2023-01-10 우진 수정)
public class DonationData {
    private String titleName;
    private String name;
    private String nowStep;
    private int ivDonationProfile;
    private String category;
    private String content;
    private String date;
    private String maxStep;
    private String dNum;

    public String getdNum() {
        return dNum;
    }

    public void setdNum(String dNum) {
        this.dNum = dNum;
    }

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

    public DonationData(String dNum, String titleName, String name, String nowStep, int ivDonationProfile, String content, String date, String maxStep) {
        this.dNum = dNum;
        this.titleName = titleName;
        this.name = name;
        this.nowStep = nowStep;
        this.ivDonationProfile = ivDonationProfile;
        this.content = content;
        this.date = date;
        this.maxStep = maxStep;
    }
}
