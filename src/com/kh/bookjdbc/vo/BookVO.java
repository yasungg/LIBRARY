package com.kh.bookjdbc.vo;

import java.math.BigDecimal;

public class BookVO {
    private BigDecimal isbn; //ISBN
    private String name; // 도서명
    private String pub; // 출산사
    private String auth; // 지은이
    private String date; // 발행일
    private String occ; // 대출 여부

    public BookVO(BigDecimal isbn, String name, String pub, String auth, String date, String occ) {
        this.isbn = isbn;
        this.name = name;
        this.pub = pub;
        this.auth = auth;
        this.date = date;
        this.occ = occ;
    }

    public BigDecimal getIsbn() {
        return isbn;
    }

    public void setIsbn(BigDecimal isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPub() {
        return pub;
    }

    public void setPub(String pub) {
        this.pub = pub;
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

    public String getOcc() {
        return occ;
    }

    public void setOcc(String occ) {
        this.occ = occ;
    }
}
