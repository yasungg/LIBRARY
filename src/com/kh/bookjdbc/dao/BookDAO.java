package com.kh.bookjdbc.dao;

import com.kh.bookjdbc.util.Common;
import com.kh.bookjdbc.vo.BookVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public List<BookVO> bookSelect() {
        List<BookVO> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM BOOK";
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                BigDecimal isbn = rs.getBigDecimal("ISBN_NO");
                String name = rs.getString("BOOK_NAME");
                String pub = rs.getString("PUBLISHER");
                String auth = rs.getString("AUTHOR");
                String date = rs.getString("PUB_DATE");
                String occ = rs.getString("IS_OCCUPIED");
                BookVO vo = new BookVO(isbn, name, pub, auth, date, occ);
                list.add(vo);
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public void bookSelectPrn(List<BookVO> list) {
        System.out.println("======================도서 정보=======================");
        System.out.println("      ISBN       도서명        출판사    지은이    발행일    대출여부");
        System.out.println("-------------------------------------");
        for(BookVO e : list) {
            System.out.print(e.getIsbn() + " ");
            System.out.print(e.getName() + " | ");
            System.out.print(e.getPub() + " | ");
            System.out.print(e.getAuth() + " | ");
            System.out.print(e.getDate() + " | ");
            System.out.println(e.getOcc());
        }
        System.out.println("-------------------------------------");
    }
    public void bookInsert() {
        System.out.print("13자리의 ISBN CODE를 입력하세요 : ");
        BigDecimal isbn = sc.nextBigDecimal();
        System.out.print("책 제목을 입력하세요. : ");
        String name = sc.next();
        System.out.print("출판사 이름을 입력하세요. : ");
        String pub = sc.next();
        System.out.print("지은이 이름을 입력하세요. : ");
        String auth = sc.next();
        System.out.print("발행일을 입력하세요.(YYYY-MM-DD) : ");
        String date = sc.next();
        System.out.print("X를 입력하세요. : ");
        String occ = sc.next();

        String sql = "INSERT INTO BOOK VALUES(?,?,?,?,?,?)";

        try{
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setBigDecimal(1, isbn);
            pStmt.setString(2, name);
            pStmt.setString(3, pub);
            pStmt.setString(4, auth);
            pStmt.setString(5, date);
            pStmt.setString(6, occ);
            pStmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
    public void bookDelete() {
        System.out.print("삭제할 책의 이름을 입력 : ");
        String name = sc.nextLine();
        String sql = "DELETE FROM BOOK WHERE BOOK_NAME = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
    public void bookDeleteISBN() {
        System.out.print("삭제하고자 하는 책의 ISBN 코드르 입력하세요(13자리)");
        BigDecimal isbn = sc.nextBigDecimal();
        String sql = "DELETE FROM BOOK WHERE ISBN_NO = ?";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setBigDecimal(1, isbn);
            pStmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
    public void bookUpdate() {
        System.out.print("수정하고자 하는 도서명을 입력해주세요. : ");
        String name = sc.next();
        System.out.println("수정하려는 도서 정보가 무엇입니까?");
        System.out.println("[1]ISBN코드 [2]도서명 [3]출판사");
        System.out.println("[4]지은이 [5]발행일");
        int sel = sc.nextInt();
        String sql = "UPDATE BOOK SET ? = ? WHERE BOOK_NAME = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            switch(sel) {
                case 1:
                    pStmt.setString(1, "ISBN_NO");
                    System.out.print("수정하고자 하는 ISBN 13자의 CODE를 입력하세요. : ");
                    BigDecimal isbn = sc.nextBigDecimal();
                    pStmt.setBigDecimal(2, isbn);
                    pStmt.setString(3, name);
                    break;
                case 2:
                    pStmt.setString(1, "BOOK_NAME");
                    System.out.print("수정하고자 하는 도서명을 입력하세요. : ");
                    String fixedName = sc.next();
                    pStmt.setString(2, fixedName);
                    pStmt.setString(3, name);
                    break;
                case 3:
                    pStmt.setString(1, "PUBLISHER");
                    System.out.print("수정하고자 하는 출판사명을 입력하세요. : ");
                    String pub = sc.next();
                    pStmt.setString(2, pub);
                    pStmt.setString(3, name);
                    break;
                case 4:
                    pStmt.setString(1, "AUTHOR");
                    System.out.print("수정하고자 하는 지은이 이름을 입력하세요. : ");
                    String auth = sc.next();
                    pStmt.setString(2, auth);
                    pStmt.setString(3, name);
                    break;
                case 5:
                    pStmt.setString(1, "PUB_DATE");
                    System.out.print("수정하고자 하는 발행일을 입력하세요. : ");
                    String date = sc.next();
                    pStmt.setString(2, date);
                    pStmt.setString(3, name);
                    break;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
}
