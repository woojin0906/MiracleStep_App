package kr.co.company.healthapplication.dbAll;

public class User {
    private String UserID;
    private String UserPassword;
    private String UserName;
    private String UserPhoneNubmer;
    private int UserDStep;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPhoneNubmer() {
        return UserPhoneNubmer;
    }

    public void setUserPhoneNubmer(String userPhoneNubmer) {
        UserPhoneNubmer = userPhoneNubmer;
    }

    public int getUserDStep() {
        return UserDStep;
    }

    public void setUserDStep(int userDStep) {
        UserDStep = userDStep;
    }
}
