package kr.co.company.healthapplication.dbAll;

import java.util.Date;

public class UserInfo {
    private String UserID;
    private Date UserInfoDate;
    private Double Height;
    private Double Weight;
    private Date Birth;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public Date getUserInfoDate() {
        return UserInfoDate;
    }

    public void setUserInfoDate(Date userInfoDate) {
        UserInfoDate = userInfoDate;
    }

    public Double getHeight() {
        return Height;
    }

    public void setHeight(Double height) {
        Height = height;
    }

    public Double getWeight() {
        return Weight;
    }

    public void setWeight(Double weight) {
        Weight = weight;
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }
}
