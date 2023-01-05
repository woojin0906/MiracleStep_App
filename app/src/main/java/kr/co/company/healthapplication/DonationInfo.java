package kr.co.company.healthapplication;

public class DonationInfo {
    private String titleName;
    private String name;
    private int nowStep;

    public DonationInfo() {}  // 생성자 메서드

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

    public int getNowStep() {
        return nowStep;
    }

    public void setNowStep(int nowStep) {
        this.nowStep = nowStep;
    }

    public DonationInfo(String titleName, String name, int nowStep) {
        this.titleName = titleName;
        this.name = name;
        this.nowStep = nowStep;
    }
}
