package com.sheygam.java_17_07_01_18;

import java.io.Serializable;

/**
 * Created by gregorysheygam on 07/01/2018.
 */

public class User implements Serializable{
    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + email;
    }

    public static User newInstance(String str){
        String[] arr = str.split(",");
        User user = new User(arr[0],arr[1]);
        return user;
    }
}
