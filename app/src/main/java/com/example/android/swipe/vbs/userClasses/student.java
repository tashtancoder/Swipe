package com.example.android.swipe.vbs.userClasses;

/**
 * Created by android on 10/1/2015.
 */
public class student extends User {
    private String devre;
    private int sinif;
    private String ktur;

    public student(String userNo, String name, String surname) {
        super(userNo, name, surname);
    }


    public String getDevre() {
        return devre;
    }

    public void setDevre(String devre) {
        this.devre = devre;
    }

    public int getSinif() {
        return sinif;
    }

    public void setSinif(int sinif) {
        this.sinif = sinif;
    }

    public String getKtur() {
        return ktur;
    }

    public void setKtur(String ktur) {
        this.ktur = ktur;
    }


}
