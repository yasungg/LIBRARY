package com.kh.bookjdbc.dao;

    import com.kh.bookjdbc.util.Common;
    import com.kh.bookjdbc.vo.MemVO;
    import com.kh.bookjdbc.util.Common;
    import com.kh.bookjdbc.vo.MemVO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

    // 회원 정보 조회
    public class MemDAO {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);

        public List<MemVO> memSelect() {
            List<MemVO> list = new ArrayList<>();
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                String sql = "SELECT * FROM MEMBER";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    BigDecimal signNo = rs.getBigDecimal("SIGN_NO");
                    String id = rs.getString("USER_ID");
                    String pw = rs.getString("USER_PW");
                    String name = rs.getString("USER_NAME");
                    String phone = rs.getString("PHONE");
                    MemVO vo = new MemVO(signNo, id, pw, name, phone);
                    list.add(vo);
                }
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }

        public void memSelectPrn(List<MemVO> list) {
            System.out.println("회원번호  회원아이디  회원비밀번호  회원이름  전화번호");
            System.out.println("---------------------------------------------");
            for (MemVO e : list) {
                System.out.print(e.getSignNo() + " ");
                System.out.print(e.getId() + " ");
                System.out.print(e.getPw() + " ");
                System.out.print(e.getName() + " ");
                System.out.print(e.getPh() + " ");
                System.out.println();
            }
            System.out.println("---------------------------------------------");
        }

        // 로그인 기능 구현


        // 회원가입
        public void memInsert() {
            System.out.println("회원 가입할 정보를 입력하세요.");
            System.out.print("회원번호(10자리) : ");
            BigDecimal signNo = sc.nextBigDecimal();
            String id = null;
            try {
                while (true) {
                    System.out.print("회원아이디 : ");
                    id = sc.next();
                    String sql = "SELECT USER_ID FROM MEMBER WHERE USER_ID = ?";
                    conn = Common.getConnection();
                    pStmt = conn.prepareStatement(sql);
                    pStmt.setString(1, id);
                    rs = pStmt.executeQuery();
                    if (rs.next()) {
                        if (rs.getString(1).equalsIgnoreCase(id)) {
                            System.out.println("이미 사용중인 아이디입니다.");
                            return;
                        } else System.out.println("사용가능한 아이디 입니다.");
                    }
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(pStmt);
            Common.close(conn);
            System.out.print("회원비밀번호 : ");
            String pw = sc.next();
            System.out.print("회원이름 : ");
            String name = sc.next();
            System.out.print("전화번호 : ");
            String number = sc.next();
            String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?)";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setBigDecimal(1, signNo);
                pStmt.setString(2, id);
                pStmt.setString(3, pw);
                pStmt.setString(4, name);
                pStmt.setString(5, number);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(stmt);
            Common.close(conn);
            System.out.println(name + "님의 회원가입이 성공적으로 완료되었습니다.");
        }

        // 회원 탈퇴
        public void memDelete() {
            System.out.print("삭제할 회원의 이름을 입력 하세요 : ");
            String name = sc.next();
            System.out.print("삭제할 회원의 아이디를 입력 하세요 : ");
            String id = sc.next();
            System.out.print("삭제할 회원의 비밀번호를 입력 하세요 : ");
            String pw = sc.next();
            String sql = "DELETE FROM MEMBER WHERE USER_NAME = ?, USER_ID = ?, USER_PW = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, name);
                pStmt.setString(2, id);
                pStmt.setString(3, pw);
                pStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(pStmt);
            Common.close(conn);
        }
        public void memUpdate() {
            System.out.print("변경할 회원의 이름을 입력 하세요 : ");
            String name = sc.next();
            System.out.print("회원아이디 : ");
            String id = sc.next();
            System.out.print("회원비밀번호 : ");
            String pw = sc.next();
            System.out.print("회원전화번호 : ");
            String phone = sc.next();

            String sql = "UPDATE MEMBER SET USER_ID = ?, USER_PW = ?, PHONE =? WHERE USER_NAME = ?";

            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1,id);
                pStmt.setString(2,pw);
                pStmt.setString(3,phone);
                pStmt.setString(4,name);
                pStmt.executeUpdate();
            } catch(Exception e) {
                e.printStackTrace();
            }
            Common.close(stmt);
            Common.close(conn);
        }
    }


