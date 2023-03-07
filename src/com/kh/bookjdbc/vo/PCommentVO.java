package com.kh.bookjdbc.vo;

import java.sql.Date;

public class PCommentVO {
    private int comNo;
    private int postNo;
    private String userID;
    private String comment;
    private Date comDate;

    public PCommentVO(int comNo, int postNo, String userID, String comment, Date comDate) {
        this.comNo = comNo;
        this.postNo = postNo;
        this.userID = userID;
        this.comment = comment;
        this.comDate = comDate;
    }

    public int getComNo() {
        return comNo;
    }

    public void setComNo(int comNo) {
        this.comNo = comNo;
    }

    public int getPostNo() {
        return postNo;
    }

    public void setPostNo(int postNo) {
        this.postNo = postNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getComDate() {
        return comDate;
    }

    public void setComDate(Date comDate) {
        this.comDate = comDate;
    }
}
