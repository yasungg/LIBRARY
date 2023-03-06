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
            System.out.print(e.getDate() + " | ");
            System.out.print(e.getBrdate());
        }
        System.out.println("--------------------------------------------------------------------------------");
    }



}
