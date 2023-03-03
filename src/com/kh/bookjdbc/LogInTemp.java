package com.kh.bookjdbc;

public class LogInTemp {
    private String id;
    private String pwd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public LogInTemp(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
