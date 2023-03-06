package com.kh.bookjdbc;

import com.kh.bookjdbc.dao.BookDAO;
import com.kh.bookjdbc.dao.MemDAO;
import com.kh.bookjdbc.dao.OccupiedBookDAO;
import com.kh.bookjdbc.util.Common;
import com.kh.bookjdbc.vo.BookVO;
import com.kh.bookjdbc.vo.OccupiedBookVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class MemberC {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);
    MemDAO memDAO = new MemDAO();
    OccupiedBookDAO occbDAO = new OccupiedBookDAO();
    BookDAO bookDAO = new BookDAO();


    public void memberLogin() {
        int tmp = memDAO.Login();
        if(tmp == 1) {
            System.out.println("로그인 성공");
            System.out.println("[1]도서 대여 [2]게시판 [3]마이페이지");
            int sel = sc.nextInt();
            switch(sel) {
                case 1:
                    List<BookVO> booklist = bookDAO.bookSelect();
                    bookDAO.bookSelectPrn(booklist);
                    occbDAO.borrow();
                    break;
                case 2:

                case 3:
                    System.out.println("[1]내 도서대출목록 [2]회원정보수정 [3]회원 탈퇴");
                    int sel2 = sc.nextInt();
                    switch(sel2) {
                        case 1:
                                List<OccupiedBookVO> occblist = memDAO.personalOCCB();
                                memDAO.personalOCCBPrn(occblist);
                            break;
                        case 2: memDAO.memUpdate(); break;
                        case 3: memDAO.memDelete(); break;
                    }
            }
        }else if(tmp == -1) {
            System.out.println("아이디/비밀번호가 일치하지 않습니다.");
            memDAO.Login();
        }
    }

}
