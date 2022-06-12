package com.example.Resturant;

public class Report {
    String UserName;
    String rate;
    String comment;

    public Report(String userName, String rate, String comment) {
        UserName = userName;
        this.rate = rate;
        this.comment = comment;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
