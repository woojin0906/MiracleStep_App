package kr.co.company.healthapplication.dbAll;

// Donation (2023-01-10 우진 생성)

import java.util.Date;

public class Donation {
    private String DCategory;
    private String DGroup;
    private String DName;
    private String DContent;
    private Date DDate;
    private int MaxStep;
    private int NowStep;

    public String getDCategory() {
        return DCategory;
    }

    public void setDCategory(String DCategory) {
        this.DCategory = DCategory;
    }

    public String getDGroup() {
        return DGroup;
    }

    public void setDGroup(String DGroup) {
        this.DGroup = DGroup;
    }

    public String getDName() {
        return DName;
    }

    public void setDName(String DName) {
        this.DName = DName;
    }

    public String getDContent() {
        return DContent;
    }

    public void setDContent(String DContent) {
        this.DContent = DContent;
    }

    public Date getDDate() {
        return DDate;
    }

    public void setDDate(Date DDate) {
        this.DDate = DDate;
    }

    public int getMaxStep() {
        return MaxStep;
    }

    public void setMaxStep(int maxStep) {
        MaxStep = maxStep;
    }

    public int getNowStep() {
        return NowStep;
    }

    public void setNowStep(int nowStep) {
        NowStep = nowStep;
    }

}
