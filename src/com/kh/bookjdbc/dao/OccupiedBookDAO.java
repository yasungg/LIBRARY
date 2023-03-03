package com.kh.bookjdbc.dao;

import com.kh.bookjdbc.MemberC;
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


    public void borrow() {
        //도서 대여하면 바뀌어야 하는 것?
        //BOOK TABLE의 IS_OCCUPIED가 O로 바뀐다.
        //대출도서목록에 도서정보와 빌린 회원의 정보가 추가된다.
        System.out.println("대출 신청할 도서명을 입력하세요.");
        sc.nextLine();
        String borrbook = sc.nextLine();
        String sql = "SELECT IS_OCCUPIED FROM BOOK WHERE BOOK_NAME = ?";
        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, borrbook);
            pStmt.executeQuery();
            if(pStmt.equals("X")) {
                String changeXsql = "UPDATE BOOK SET IS_OCCUPIED  = ? WHERE BOOK_NAME = ?";
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(changeXsql);
                pStmt.setString(1, "O");
                pStmt.setString(2, borrbook);
                pStmt.executeUpdate();
                //BOOK TABLE의 IS_OCCUPIED가 바뀌는 것 구현 완료
                //occupied book table에 도서정보와 빌려간 회원정보가 함께 추가되는 기능 구현하면 borrow 완성
                String signname = "SELECT SIGN_NO, USER_NAME FROM MEMBER WHERE USER_ID = ?";
                String occdbook = "SELECT ISBN_NO, BOOK_NAME, AUTHOR, PUB_DATE FROM BOOK WHERE BOOK_NAME = ?";
                pStmt = conn.prepareStatement(signname);
                pStmt.setString(1, memDAO.id);
                rs = pStmt.executeQuery();
                rs.next();
                BigDecimal brsign = rs.getBigDecimal("SIGN_NO");
                String brname = rs.getString("USER_NAME");

                pStmt = conn.prepareStatement(occdbook);
                pStmt.setString(1, borrbook);
                rs = pStmt.executeQuery();
                rs.next();
                String brbname = rs.getString("BOOK_NAME");
                BigDecimal brisbn = rs.getBigDecimal("ISBN_NO");
                String brauth = rs.getString("AUTHOR");
                String brpdate = rs.getString("PUB_DATE");
                pStmt = conn.prepareStatement("SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD HH:mm:ss') AS \"BORROWDATE\" FROM DUAL");
                rs = pStmt.executeQuery();
                rs.next();
                String brdate = rs.getString("BORROWDATE");

            } else if(pStmt.equals("O")) System.out.println("이미 대여된 도서입니다!");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
