package com.example.android.swipe.vbs.userClasses;

/**
 * Created by android on 10/1/2015.
 */
public class teacher  extends User {
    private String userName;
    private String authorization;
    private int gorevNo;
    private int ikNo;
    private int sgs_ust;

    public teacher(String username, String name, String surname) {
        super(username, name, surname);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public int getGorevNo() {
        return gorevNo;
    }

    public void setGorevNo(int gorevNo) {
        this.gorevNo = gorevNo;
    }

    public int getIkNo() {
        return ikNo;
    }

    public void setIkNo(int ikNo) {
        this.ikNo = ikNo;
    }

    public int getSgs_ust() {
        return sgs_ust;
    }

    public void setSgs_ust(int sgs_ust) {
        this.sgs_ust = sgs_ust;
    }
}
