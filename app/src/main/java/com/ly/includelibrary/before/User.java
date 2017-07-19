package com.ly.includelibrary.before;

/**
 * Created by Administrator on 2017/7/8 0008.
 */

public class User {

    String name;
    String password ;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
