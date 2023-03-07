package com.kh.bookjdbc.vo;

import java.sql.Date;

    public class PostVO {
        private int postNo;
        private String title;
        private String userID;
        private String pContent;
        private Date postDate;

        public PostVO(int postNo, String title, String userID, String pContent, Date postDate) {
            this.postNo = postNo;
            this.title = title;
            this.userID = userID;
            this.pContent = pContent;
            this.postDate = postDate;
        }

        public int getPostNo() {
            return postNo;
        }

        public void setPostNo(int postNo) {
            this.postNo = postNo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getpContent() {
            return pContent;
        }

        public void setpContent(String pContent) {
            this.pContent = pContent;
        }

        public Date getPostDate() {
            return postDate;
        }

        public void setPostDate(Date postDate) {
            this.postDate = postDate;
        }
    }
