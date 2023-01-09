package kr.co.company.healthapplication.dbAll;

// Rank (2023-01-10 인범 수정)

public class Rank {

    private String id;
    private String TotalUserDonation;
    private String TotalUserStep;
    private String category;

    public Rank(String id, String totalUserDonation, String totalUserStep, String category) {
        this.id = id;
        TotalUserDonation = totalUserDonation;
        TotalUserStep = totalUserStep;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotalUserDonation() {
        return TotalUserDonation;
    }

    public void setTotalUserDonation(String totalUserDonation) {
        TotalUserDonation = totalUserDonation;
    }

    public String getTotalUserStep() {
        return TotalUserStep;
    }

    public void setTotalUserStep(String totalUserStep) {
        TotalUserStep = totalUserStep;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
