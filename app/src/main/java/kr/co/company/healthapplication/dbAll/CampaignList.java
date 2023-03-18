package kr.co.company.healthapplication.dbAll;

// Donation (2023-01-10 우진 생성)

import java.util.Date;

public class CampaignList {
    private String category; // 분야
    private String hostingGroup; // 기업명
    private String title; // 제목
    private String content; // 내용
    private Date lastDate; // 작성일
    private int maxDonation; // 최대 기부 걸음 수
    private int nowDonation; // 현재 기부된 걸음 수
    private int campaignIndex; // 자동 증가 번호

    public int getCampaignIndex() {
        return campaignIndex;
    }

    public void setCampaignIndex(int campaignIndex) {
        this.campaignIndex = campaignIndex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHostingGroup() {
        return hostingGroup;
    }

    public void setHostingGroup(String hostingGroup) {
        this.hostingGroup = hostingGroup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public int getMaxDonation() {
        return maxDonation;
    }

    public void setMaxDonation(int maxDonation) {
        this.maxDonation = maxDonation;
    }

    public int getNowDonation() {
        return nowDonation;
    }

    public void setNowDonation(int nowDonation) {
        this.nowDonation = nowDonation;
    }

}
