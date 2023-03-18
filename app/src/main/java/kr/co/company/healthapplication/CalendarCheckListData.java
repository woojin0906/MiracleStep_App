package kr.co.company.healthapplication;

// 달력 체크리스트 데이터 액티비티 (2023-03-06 이수 제작)
public class CalendarCheckListData {

    private String listDate;
    private String userID;
    private int ivProfile;
    private String listIndex;
    private String content;
    private int checked;

    public CalendarCheckListData(int check, String userID, String tvListNum, int ivProfile, String tvContent, String listDate) {
        this.checked = check;
        this.listIndex = tvListNum;
        this.ivProfile = ivProfile;
        this.content = tvContent;
        this.userID = userID;
        this.listDate = listDate;
    }


    public String getListDate() {
        return listDate;
    }

    public void setListDate(String listDate) {
        this.listDate = listDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getIvProfile() {
        return ivProfile;
    }

    public void setIvProfile(int ivProfile) {
        this.ivProfile = ivProfile;
    }

    public String getListIndex() {
        return listIndex;
    }

    public void setListIndex(String listIndex) {
        this.listIndex = listIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getProfile() {
        return 0;
    }
}
