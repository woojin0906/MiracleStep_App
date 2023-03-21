package kr.co.company.healthapplication;
// 봉사활동 캠페인 클래스 (2023-03-18 우진 수정)
public class Volunteer {

    private String titleName;
    private String groupName;
    private String category;
    private String state;
    private String company;
    private String recuDate;
    private String personCategory;
    private int volunCount;
    private String day;
    private String time;
    private String place;
    private String volunDate;
    private String content;
    private String managerName;
    private String managerPhone;
    private String managerAddress;

    public Volunteer() {
        super();
    }

    public Volunteer(String titleName, String groupName, String category, String state, String company, String recuDate, String personCategory, int volunCount, String day, String time, String place, String volunDate, String content, String managerName, String managerPhone, String managerAddress) {
        this.titleName = titleName;
        this.groupName = groupName;
        this.category = category;
        this.state = state;
        this.company = company;
        this.recuDate = recuDate;
        this.personCategory = personCategory;
        this.volunCount = volunCount;
        this.day = day;
        this.time = time;
        this.place = place;
        this.volunDate = volunDate;
        this.content = content;
        this.managerName = managerName;
        this.managerPhone = managerPhone;
        this.managerAddress = managerAddress;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRecuDate() {
        return recuDate;
    }

    public void setRecuDate(String recuDate) {
        this.recuDate = recuDate;
    }

    public String getPersonCategory() {
        return personCategory;
    }

    public void setPersonCategory(String personCategory) {
        this.personCategory = personCategory;
    }

    public int getVolunCount() {
        return volunCount;
    }

    public void setVolunCount(int volunCount) {
        this.volunCount = volunCount;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVolunDate() {
        return volunDate;
    }

    public void setVolunDate(String volunDate) {
        this.volunDate = volunDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public void setManagerAddress(String managerAddress) {
        this.managerAddress = managerAddress;
    }
}
