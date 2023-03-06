package com.kh.bookjdbc;

import com.kh.bookjdbc.dao.BookDAO;
import com.kh.bookjdbc.dao.MemDAO;
import com.kh.bookjdbc.dao.OccupiedBookDAO;
import com.kh.bookjdbc.vo.BookVO;

import java.util.List;
import java.util.Scanner;

public class BookMain {
    public static void main(String[] args) {
        Admin admin = new Admin();
        BookDAO bookdao = new BookDAO();
        MemDAO memDAO = new MemDAO();
        MemberC memC = new MemberC();
        Scanner sc = new Scanner(System.in);
        System.out.println("[1]회원 가입 [2]회원 로그인 [3]도서 검색 [4]관리자 로그인");
        System.out.print("로그인 옵션을 선택하세요. : ");
        int sel1 = sc.nextInt();
        switch(sel1) {
            case 1:
                memDAO.memInsert();break;
            case 2:
                memC.memberLogin();break;
            case 3:
                List<BookVO> indexList = bookdao.indexBook();
                bookdao.IndexPrn(indexList); break;
            case 4:
                admin.AdminLogin(); break;
            case 5:
                OccupiedBookDAO occdao = new OccupiedBookDAO();
                occdao.borrow();

            default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
        }
    }
}
