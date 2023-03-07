package com.kh.bookjdbc;

import com.kh.bookjdbc.dao.BookDAO;
import com.kh.bookjdbc.dao.MemDAO;
import com.kh.bookjdbc.dao.OccupiedBookDAO;
import com.kh.bookjdbc.vo.BookVO;
import com.kh.bookjdbc.vo.MemVO;
import com.kh.bookjdbc.vo.OccupiedBookVO;

import java.util.List;
import java.util.Scanner;

public class AdminLogIn {
    private String AdminID = "admin";
    private int AdminPWD = 15571601;

    public void AdminLogin() {
        Scanner sc = new Scanner(System.in);
        BookDAO bookdao = new BookDAO();
        OccupiedBookDAO occupiedBookDAO = new OccupiedBookDAO();
        MemDAO memDAO = new MemDAO();
        System.out.print("관리자 ID를 입력하세요. : ");
        String ID = sc.next();
        System.out.print("관리자 비밀번호를 입력하세요. : ");
        int PWD = sc.nextInt();

        if(ID.equals(AdminID) && PWD == AdminPWD) {
            System.out.println("=================관리자 메뉴===================");
            System.out.println("[1]회원정보조회 [2]보유도서목록 [3]보유도서목록 편집 [4]현재 대출중인 도서목록");
            int sel = sc.nextInt();
            switch(sel) {
                case 1:
                    List<MemVO> memlist = memDAO.memSelect();
                    memDAO.memSelectPrn(memlist);
                case 2:
                    List<BookVO> list = bookdao.bookSelect();
                    bookdao.bookSelectPrn(list);
                    break;
                case 3:
                    System.out.print("[1]도서목록 추가 [2]도서목록 삭제 [3]도서정보수정");
                    int sel1 = sc.nextInt();
                    switch(sel1) {
                        case 1: bookdao.bookInsert(); break;
                        case 2: System.out.print("[1]도서명으로 삭제 [2]ISBN코드로 삭제");
                                int sel2 = sc.nextInt();
                                switch(sel2) {
                                    case 1: bookdao.bookDelete(); break;
                                    case 2: bookdao.bookDeleteISBN(); break;
                                } break;
                        case 3: bookdao.bookUpdate(); break;
                    }
                case 4:
                    List<OccupiedBookVO> occb = occupiedBookDAO.OCCBSelect();
                    occupiedBookDAO.OCCBSelectPrn(occb);break;
                default : System.out.println("다시 입력하세요!");
            }
        } else System.out.println("관리자 ID, Password를 잘못 입력하셨습니다!!");
    }
}
