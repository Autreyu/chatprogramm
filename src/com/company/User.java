package com.company;

public class User {
    private String userName;
    private int userId;

    public User(String userName, int userId){
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId(){
        return this.userId;
    }

    public String getUserName() { return this.userName; }

}
