import com.kh.bookjdbc.dao.MemDAO;
import com.kh.bookjdbc.util.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Member {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    MemDAO memDAO = new MemDAO();
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
            if (rs.next()) {
                if (rs.getString(1).equals(pw)) {

                    return 1;
                } else {

                    return 0;
                }
            }

            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }
    public void memberLogin() {
        Login();
        if(Login() == 1) {
            System.out.println("로그인 성공");
            System.out.println("[1]도서 대여 [2]게시판 [3]마이페이지");
            int sel = sc.nextInt();
            switch(sel) {
                case 1:
                case 2:
                case 3:
                    System.out.println("[1]내 도서대출목록 [2]회원정보수정 [3]회원 탈퇴");
                    int sel2 = sc.nextInt();
                    switch(sel2) {
                        case 1:
                            try {
                                conn = Common.getConnection();
                                pStmt = conn.prepareStatement();
                            }catch(Exception e) {
                                e.printStackTrace();
                            }
                        case 2: memDAO.memUpdate();
                        case 3: memDAO.memDelete();

                    }
            }


        }else if(Login() == 0) {
            System.out.println("비밀번호 불일치");
        }else if(Login() == -1) {
            System.out.println("아이디가 없습니다.");
        }
    }

}
