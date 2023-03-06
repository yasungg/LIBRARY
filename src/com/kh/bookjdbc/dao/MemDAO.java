package com.kh.bookjdbc.dao;

    import com.kh.bookjdbc.util.Common;
    import com.kh.bookjdbc.vo.MemVO;
    import com.kh.bookjdbc.vo.OccupiedBookVO;

    import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
import java.util.Scanner;

    // 회원 정보 조회
    public class MemDAO {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        Scanner sc = new Scanner(System.in);
        private static String logInid = "";
        private static String pw = "";
        static String borrbook = "";

        public String getPw() {
            return pw;
        }

        public void setPw(String pw) {
            this.pw = pw;
        }

        public String getLogInid() {
            return logInid;
        }

        public void setLogInid(String logInid) {
            this.logInid = logInid;
        }

        public int Login() {
            System.out.println("로그인할 정보를 입력하세요.");
            System.out.print("아이디를 입력하세요 : ");
            logInid = sc.next();
            setLogInid(logInid);
            System.out.print("비밀번호를 입력하세요 : ");
            pw = sc.next();
            setPw(pw);
            String sql = "SELECT USER_PW FROM MEMBER WHERE USER_ID = ?";
            try {
                conn = Common.getConnection();
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1, logInid);
                System.out.println(logInid);
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
                String sql = "SELECT SIGN_NO, USER_ID, RPAD(SUBSTR(USER_PW, 1, 2), 18, '*') AS \"USER_PW\", USER_NAME, PHONE FROM MEMBER";
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
            String sql = "DELETE FROM MEMBER WHERE USER_NAME = ? AND USER_ID = ? AND USER_PW = ?";
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
            System.out.println("회원탈퇴가 완료되었습니다.");
        }
        public void memUpdate() {
            System.out.print("변경할 회원의 이름을 입력 하세요 : ");
            String name = sc.next();
            System.out.println("수정하려는 회원의 정보를 선택하세요 : [1]회원아이디 [2]회원비밀번호 [3]회원전화번호 ");
            int sel = sc.nextInt();
            try{
                conn = Common.getConnection();
                switch(sel) {
                    case 1 :
                        String sql = "UPDATE MEMBER SET USER_ID = ? WHERE USER_NAME = ?";
                        pStmt = conn.prepareStatement(sql);
                        System.out.print("새로운 아이디를 입력하세요 : ");
                        String id = sc.next();
                        pStmt.setString(1,id);
                        pStmt.setString(2,name);
                        System.out.println("아이디가 수정되었습니다 : " + id);
                        break;
                    case 2 :
                        String sql1 = "UPDATE MEMBER SET USER_PW = ? WHERE USER_NAME = ?";
                        pStmt = conn.prepareStatement(sql1);
                        System.out.print("새로운 비밀번호를 입력하세요 : ");
                        String pw = sc.next();
                        pStmt.setString(1,pw);
                        pStmt.setString(2,name);
                        System.out.println("비밀번호가 수정되었습니다 : " + pw);
                        break;
                    case 3 :
                        String sql2 = "UPDATE MEMBER SET PHONE = ? WHERE USER_NAME = ?";
                        pStmt = conn.prepareStatement(sql2);
                        System.out.print("새로운 전화번호를 입력하세요 : ");
                        String phone = sc.next();
                        pStmt.setString(1,phone);
                        pStmt.setString(2,name);
                        System.out.println("전화번호가 수정되었습니다 : " + phone);
                        break;
                }
                pStmt.executeUpdate();

            } catch(Exception e) {
                e.printStackTrace();
            }
            Common.close(rs);
            Common.close(pStmt);

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
                pStmt.setString(1, logInid);
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
            System.out.println(logInid);
            String addsql = "SELECT M.SIGN_NO, M.USER_NAME, B.BOOK_NAME, B.ISBN_NO, B.AUTHOR, B.PUB_DATE FROM (SELECT * FROM BOOK WHERE BOOK_NAME = ?) B JOIN (SELECT * FROM MEMBER WHERE USER_ID = ?) M ON B.LIBNO = M.LIBNO";
            try{
                Connection conn1 = Common.getConnection();
                PreparedStatement pStmt1 = conn1.prepareStatement(addsql);
                pStmt1.setString(1, borrbook);
                pStmt1.setString(2, logInid);
                ResultSet rs1 = pStmt1.executeQuery();
                if(rs1.next()) {
                    BigDecimal brsign = rs1.getBigDecimal("SIGN_NO");
                    String brname = rs1.getString("USER_NAME");
                    String brbname = rs1.getString("BOOK_NAME");
                    BigDecimal brisbn = rs1.getBigDecimal("ISBN_NO");
                    String brauth = rs1.getString("AUTHOR");
                    String brpdate = rs1.getString("PUB_DATE");
                    String brdate = formatedNow;
                    int libno = 1;
                    System.out.println(brsign);
                    try {
                        String putinsql = "INSERT INTO OCCUPIED_BOOK VALUES(?,?,?,?,?,?,?,?)";
                        Connection conn2 = Common.getConnection();
                        PreparedStatement pStmt2 = conn2.prepareStatement(putinsql);
                        pStmt2.setBigDecimal(1, brsign);
                        pStmt2.setString(2, brname);
                        pStmt2.setString(3, brbname);
                        pStmt2.setBigDecimal(4, brisbn);
                        pStmt2.setString(5, brauth);
                        pStmt2.setString(6, brpdate);
                        pStmt2.setString(7, brdate);
                        pStmt2.setInt(8, libno);
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



