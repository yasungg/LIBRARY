package com.kh.bookjdbc.dao;

import com.kh.bookjdbc.util.Common;
import com.kh.bookjdbc.vo.OccupiedBookVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OccupiedBookDAO extends MemDAO{
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    MemDAO memDAO = new MemDAO();
    static String borrbook = "";


    public List<OccupiedBookVO> OCCBSelect() {
        List<OccupiedBookVO> occb = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM OCCUPIED_BOOK";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                BigDecimal sign = rs.getBigDecimal("SIGN_NO");
                String name = rs.getString("USER_NAME");
                String bname = rs.getString("BOOK_NAME");
                BigDecimal isbn = rs.getBigDecimal("ISBN_NO");
                String auth = rs.getString("AUTHOR");
                String date = rs.getString("PUB_DATE");
                String brdate = rs.getString("BORROW_DATE");
                OccupiedBookVO vo = new OccupiedBookVO(sign, name, bname, isbn, auth, date, brdate);
                occb.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return occb;
    }
    public void OCCBSelectPrn(List<OccupiedBookVO> occb) {
        System.out.println("====================================대출도서정보==================================");
        System.out.println("  회원번호  회원이름 도서명   ISBN    저자    발행일");
        System.out.println("---------------------------------------------------------------------------------");
        for(OccupiedBookVO e : occb) {
            System.out.print(e.getSign() + " | ");
            System.out.print(e.getName() + " | ");
            System.out.print(e.getBname() + " | ");
            System.out.print(e.getIsbn() + " | ");
            System.out.print(e.getAuth() + " | ");
            System.out.print(e.getDate() + " | ");
            System.out.print(e.getBrdate());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }
    public List<OccupiedBookVO> personalOCCB() {
        List<OccupiedBookVO> occblist = new ArrayList<>();
        String sqlid = "SELECT SIGN_NO FROM MEMBER WHERE USER_ID = ?";
        String sql = "SELECT M.SIGN_NO, M.USER_NAME, O.BOOK_NAME, O.ISBN_NO, O.AUTHOR, O.PUB_DATE, O.BORROW_DATE FROM" +
                " (SELECT * FROM MEMBER WHERE SIGN_NO = ?) M JOIN (SELECT * FROM OCCUPIED_BOOK) O ON M.SIGN_NO = " +
                "O.SIGN_NO";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sqlid);
            pStmt.setString(1, memDAO.getLogInid());
            rs = pStmt.executeQuery();
            rs.next();
            BigDecimal sign = rs.getBigDecimal("SIGN_NO");

            pStmt = conn.prepareStatement(sql);
            pStmt.setBigDecimal(1, sign);
            rs = pStmt.executeQuery();
            while(rs.next()) {
                BigDecimal rsign = rs.getBigDecimal("SIGN_NO");
                String uname = rs.getString("USER_NAME");
                String bname = rs.getString("BOOK_NAME");
                BigDecimal isbn = rs.getBigDecimal("ISBN_NO");
                String auth = rs.getString("AUTHOR");
                String date = rs.getString("PUB_DATE");
                String brdate = rs.getString("BORROW_DATE");
                OccupiedBookVO occbvo = new OccupiedBookVO(rsign, uname, bname, isbn, auth, date, brdate);
                occblist.add(occbvo);
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return occblist;
    }
    public void personalOCCBPrn(List<OccupiedBookVO> occblist) {
        System.out.println("  회원번호  회원이름  책이름     ISBN     지은이      발행일           대여일자          ");
        System.out.println("---------------------------------------------");
        for (OccupiedBookVO e : occblist) {
            System.out.print(e.getSign() + " | ");
            System.out.print(e.getName() + " | ");
            System.out.print(e.getBname() + " | ");
            System.out.print(e.getIsbn() + " | ");
            System.out.print(e.getAuth() + " | ");
            System.out.print(e.getDate() + " | ");
            System.out.println(e.getBrdate());
        }
        System.out.println("---------------------------------------------");
    }
    public void borrow() {
        //도서 대여하면 바뀌어야 하는 것?
        //BOOK TABLE의 IS_OCCUPIED가 O로 바뀐다.
        //대출도서목록에 도서정보와 빌린 회원의 정보가 추가된다.
        System.out.println("대출 신청할 도서명을 입력하세요.");
        borrbook = sc.nextLine();

        try {
            String sql = "SELECT * FROM BOOK WHERE BOOK_NAME = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, borrbook);
            rs = pStmt.executeQuery();
            if(rs.next()) {
                String isocc = rs.getString("IS_OCCUPIED");
                if (isocc.equals("X")) {
                    try {
                        String changeXsql = "UPDATE BOOK SET IS_OCCUPIED = ? WHERE BOOK_NAME = ?";
                        conn = Common.getConnection();
                        pStmt = conn.prepareStatement(changeXsql);
                        pStmt.setString(1, "O");
                        pStmt.setString(2, borrbook);
                        int rst1 = pStmt.executeUpdate();
                        if (rst1 == 1) {
                            System.out.println("IS_OCCUPIED 변경 완료");
                            addOCCB();
                            System.out.println("대출 성공");
                        } else if (isocc.equals("O")) System.out.println("이미 대여된 도서입니다!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);
    }
    public void addOCCB() {
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String addsql = "SELECT M.SIGN_NO, M.USER_NAME, B.BOOK_NAME, B.ISBN_NO, B.AUTHOR, B.PUB_DATE FROM (SELECT * FROM BOOK WHERE BOOK_NAME = ?) B JOIN (SELECT * FROM MEMBER WHERE USER_ID = ?) M ON B.LIBNO = M.LIBNO";
        try{
            Connection conn1 = Common.getConnection();
            PreparedStatement pStmt1 = conn1.prepareStatement(addsql);
            pStmt1.setString(1, borrbook);
            pStmt1.setString(2, memDAO.getLogInid());
            ResultSet rs1 = pStmt1.executeQuery();
            if(rs1.next()) {
                BigDecimal brsign = rs1.getBigDecimal("SIGN_NO");
                String brname = rs1.getString("USER_NAME");
                String brbname = rs1.getString("BOOK_NAME");
                BigDecimal brisbn = rs1.getBigDecimal("ISBN_NO");
                String brauth = rs1.getString("AUTHOR");
                String brpdate = rs1.getString("PUB_DATE");
                String brdate = formatedNow;
                try {
                    String putinsql = "INSERT INTO OCCUPIED_BOOK VALUES(?,?,?,?,?,?,?)";
                    Connection conn2 = Common.getConnection();
                    PreparedStatement pStmt2 = conn2.prepareStatement(putinsql);
                    pStmt2.setBigDecimal(1, brsign);
                    pStmt2.setString(2, brname);
                    pStmt2.setString(3, brbname);
                    pStmt2.setBigDecimal(4, brisbn);
                    pStmt2.setString(5, brauth);
                    pStmt2.setString(6, brpdate);
                    pStmt2.setString(7, brdate);
                    int rst2 = pStmt2.executeUpdate();
                    System.out.println(rst2);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);
    }


}
