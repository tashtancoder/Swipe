package com.example.android.swipe.vbs.userClasses;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by android on 10/1/2015.
 */
public class User extends Application implements Parcelable{
    private String name;
    private String surname;
    private String username;
    private int userNo;
    private String userType;
    private String server;
    private int sube;
    private int sgs_alt;
    private int sezon;
    private boolean isActive;
    private String password;


    public User(Parcel in) {
        readFromParcel(in);
    }

    public User(String username, String name, String surname){
        this.username = username;
        this.name = name;
        this.surname = surname;
        isActive = false;
    }

    public User(String username, String name, String password, String surname, int userNo, String userType, int sube, int sezon){
        this.username = username;
        this.userNo = userNo;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.sube = sube;
        this.sezon = sezon;
        this.userType = userType;
        isActive = false;
    }

    public User(String username, String name, String surname, String password){
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        isActive = false;
    }

    public User(String username, int userNo, String name, String surname, String password){
        this.username = username;
        this.userNo = userNo;
        this.name = name;
        this.surname = surname;
        this.password = password;
        isActive = false;
    }

    public User(String name, String surname){
        //this.userNo = userNo;
        this.name = name;
        this.surname = surname;
        isActive = false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getSube() {
        return sube;
    }

    public void setSube(int sube) {
        this.sube = sube;
    }

    public int getSgs_alt() {
        return sgs_alt;
    }

    public void setSgs_alt(int sgs_alt) {
        this.sgs_alt = sgs_alt;
    }

    public int getSezon() {
        return sezon;
    }

    public void setSezon(int sezon) {
        this.sezon = sezon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return name +" "+ surname;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;

        return username == user.username;

    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(userType);
        dest.writeInt(userNo);
        dest.writeInt(sube);
        dest.writeInt(sezon);
        //dest.writeString(server);
        //dest.writeInt(sube);
        //dest.writeInt(sgs_alt);
        //dest.writeInt(sezon);
        //dest.writeValue(isActive);
    }


    public void readFromParcel(Parcel in) {
        name = in.readString();
        surname = in.readString();
        username = in.readString();
        password = in.readString();
        userType = in.readString();
        userNo = in.readInt();
        sube = in.readInt();
        sezon = in.readInt();
        //server = in.readString();
        //sube = in.readInt();
        //sgs_alt = in.readInt();
        //sezon = in.readInt();
        //isActive = (Boolean) in.readValue(Boolean.class.getClassLoader());

    }
}
