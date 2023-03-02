package com.kh.bookjdbc.vo;

import java.math.BigDecimal;

public class OccupiedBookVO {
    private BigDecimal sign;
    private String name;
    private String bname;
    private BigDecimal isbn;
    private String auth;
    private String date;

    public OccupiedBookVO(BigDecimal sign, String name, String bname, BigDecimal isbn, String auth, String date) {
        this.sign = sign;
        this.name = name;
        this.bname = bname;
        this.isbn = isbn;
        this.auth = auth;
        this.date = date;
    }

    public BigDecimal getSign() {
        return sign;
    }

    public void setSign(BigDecimal sign) {
        this.sign = sign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public BigDecimal getIsbn() {
        return isbn;
    }

    public void setIsbn(BigDecimal isbn) {
        this.isbn = isbn;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
