package com.kh.bookjdbc.dao;

    import com.kh.bookjdbc.MemberC;
    import com.kh.bookjdbc.util.Common;
    import com.kh.bookjdbc.vo.MemVO;
    import com.kh.bookjdbc.util.Common;
    import com.kh.bookjdbc.vo.MemVO;
    import com.kh.bookjdbc.vo.OccupiedBookVO;

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
        String id = "";
        public int Login() {
            System.out.println("로그인할 정보를 입력하세요.");
            System.out.print("아이디를 입력하세요 : ");
            id = sc.next();
            System.out.print("비밀번호를 입력하세요 : ");
            String pw = sc.next();
            String sql = "SELECT USER_PW FROM MEMBER WHERE USER_ID = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, id);
                rs = pStmt.executeQuery();
                rs.next();
                if(rs.getString("USER_PW").equals(pw)) {
                    return 1;
                }
                else {
                    return -1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
            return 0;
        }

        public List<MemVO> memSelect() {
            List<MemVO> list = new ArrayList<>();
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                String sql = "SELECT SIGN_NO, USER_ID, RPAD(SUBSTR(USER_PW, 1, 2), 18, '*'), USER_NAME, PHONE FROM MEMBER";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    BigDecimal signNo = rs.getBigDecimal("SIGN_NO");
                    String memSid = rs.getString("USER_ID");
                    String pw = rs.getString("USER_PW");
                    String name = rs.getString("USER_NAME");
                    String phone = rs.getString("PHONE");
                    MemVO vo = new MemVO(signNo, memSid, pw, name, phone);
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
        public List<OccupiedBookVO> personalOCCB() {
            List<OccupiedBookVO> occblist = new ArrayList<>();
            String sqlid = "SELECT SIGN_NO FROM MEMBER WHERE USER_ID = ?";
            String sql = "SELECT M.SIGN_NO, M.USER_NAME, O.BOOK_NAME, O.ISBN_NO, O.AUTHOR, O.PUB_DATE FROM (SELECT * FROM MEMBER WHERE SIGN_NO = ?) M JOIN (SELECT * FROM OCCUPIED_BOOK) O ON M.SIGN_NO = O.SIGN_NO";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sqlid);
                pStmt.setString(1, id);
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
            System.out.println("회원번호  회원아이디  회원비밀번호  회원이름  전화번호");
            System.out.println("---------------------------------------------");
            for (OccupiedBookVO e : occblist){
                System.out.print(e.getSign() + " | ");
                System.out.print(e.getName() + " | ");
                System.out.print(e.getBname() + " | ");
                System.out.print(e.getIsbn() + " | ");
                System.out.print(e.getAuth() + " | ");
                System.out.println(e.getDate());
                }
            System.out.println("---------------------------------------------");

        }
    }


