package kr.co.company.healthapplication.dbAll;

import android.widget.ImageView;

public class CheckList {

    private String tvListNum;
    private ImageView ivProfile;
    private String tvContent;

    public CheckList(String tvListNum, ImageView ivProfile, String tvContent) {
        this.tvListNum = tvListNum;
        this.ivProfile = ivProfile;
        this.tvContent = tvContent;
    }

    public String getTvListNum() {
        return tvListNum;
    }

    public void setTvListNum(String tvListNum) {
        this.tvListNum = tvListNum;
    }

    public ImageView getIvProfile() {
        return ivProfile;
    }

    public void setIvProfile(ImageView ivProfile) {
        this.ivProfile = ivProfile;
    }

    public String getTvContent() {
        return tvContent;
    }

    public void setTvContent(String tvContent) {
        this.tvContent = tvContent;
    }
}
