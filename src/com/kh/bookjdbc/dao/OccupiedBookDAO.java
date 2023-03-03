package com.kh.bookjdbc.dao;

import com.kh.bookjdbc.util.Common;
import com.kh.bookjdbc.vo.OccupiedBookVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    String borrbook = "";


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
            System.out.print(e.getDate());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }


    public void checkborrow() {
        //도서 대여하면 바뀌어야 하는 것?
        //BOOK TABLE의 IS_OCCUPIED가 O로 바뀐다.
        //대출도서목록에 도서정보와 빌린 회원의 정보가 추가된다.
        System.out.println("대출 신청할 도서명을 입력하세요.");

        borrbook = sc.nextLine();
        String sql = "SELECT IS_OCCUPIED FROM BOOK WHERE BOOK_NAME = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, borrbook);
            rs = pStmt.executeQuery();
            if(rs.next()) {
                String isocc = rs.getString("IS_OCCUPIED");
                if (isocc.equals("X")) {
                    borrow();
                    System.out.println("성공");
                } else if (isocc.equals("O")) System.out.println("이미 대여된 도서입니다!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);
    }
    public void borrow() {
        MemDAO memDAO1 = new MemDAO();
        try {
            conn = Common.getConnection();
            String changeXsql = "UPDATE BOOK SET IS_OCCUPIED = ? WHERE BOOK_NAME = ?";
            pStmt = conn.prepareStatement(changeXsql);
            pStmt.setString(1, "O");
            pStmt.setString(2, borrbook);
            int rst1 = pStmt.executeUpdate();
            if (rst1 == 1) {
                System.out.println("IS_OCCUPIED 변경 완료");
                String sql = "SELECT M.SIGN_NO, M.USER_NAME, B.BOOK_NAME, B.ISBN_NO, B.AUTHOR, B.PUB_DATE FROM (SELECT * FROM BOOK WHERE BOOK_NAME = ?) B JOIN (SELECT * FROM MEMBER WHERE USER_ID = ?) M ON B.LIBNO = M.LIBNO";
                try {
                    conn = Common.getConnection();
                    pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1, borrbook);
                    pStmt.setString(2, memDAO1.id);
                    rs = pStmt.executeQuery();
                    while (rs.next()) {
                        BigDecimal brsign = rs.getBigDecimal("SIGN_NO");
                        String brname = rs.getString("USER_NAME");
                        String brbname = rs.getString("BOOK_NAME");
                        BigDecimal brisbn = rs.getBigDecimal("ISBN_NO");
                        String brauth = rs.getString("AUTHOR");
                        String brpdate = rs.getString("PUB_DATE");

                        String brdate = null;
                        String instbrsql = "INSERT INTO OCCUPIED_BOOK VALUES(?,?,?,?,?,?,?)";
                        pStmt = conn.prepareStatement(instbrsql);
                        pStmt.setBigDecimal(1, brsign);
                        pStmt.setString(2, brname);
                        pStmt.setString(3, brbname);
                        pStmt.setBigDecimal(4, brisbn);
                        pStmt.setString(5, brauth);
                        pStmt.setString(6, brpdate);
                        pStmt.setString(7, brdate);
                        pStmt.executeUpdate();
                    }
                } catch (Exception e) {
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

    public void checkbrdate() {
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:mm:ss') AS \"BORROWDATE\" FROM DUAL");
            rs = pStmt.executeQuery();
            rs.next();
            String brdate = rs.getString("BORROWDATE");
            System.out.println(brdate);
            pStmt = conn.prepareStatement("UPDATE OCCUPIED_BOOK SET BORROW_DATE = ? WHERE BOOK_NAME = ?");
            pStmt.setString(1, brdate);
            pStmt.setString(2, borrbook);
            pStmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(rs);
        Common.close(pStmt);
        Common.close(conn);
    }
}
