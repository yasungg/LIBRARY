package com.kh.bookjdbc;

import com.kh.bookjdbc.dao.BookDAO;
import com.kh.bookjdbc.vo.BookVO;

import java.util.List;
import java.util.Scanner;

public class Admin {
    private String AdminID = "admin";
    private int AdminPWD = 15571601;

    public void AdminLogin() {
        Scanner sc = new Scanner(System.in);
        BookDAO dao = new BookDAO();
        System.out.print("관리자 ID를 입력하세요. : ");
        String ID = sc.next();
        System.out.print("관리자 비밀번호를 입력하세요. : ");
        int PWD = sc.nextInt();

        if(ID.equals(AdminID) && PWD == AdminPWD) {
            System.out.println("=================관리자 메뉴===================");
            System.out.println("[1]회원정보관리 [2]보유도서목록 [3]보유도서목록 편집");
            System.out.println("[4]현재 대출중인 도서목록");
            int sel = sc.nextInt();
            switch(sel) {
                case 1:
                case 2:
                    List<BookVO> list = dao.bookSelect();
                    dao.bookSelectPrn(list);
                    break;
                case 3:
                    System.out.print("[1]도서목록 추가 [2]도서목록 삭제 [3]도서정보수정");
                    int sel1 = sc.nextInt();
                    switch(sel1) {
                        case 1: dao.bookInsert(); break;
                        case 2: System.out.print("[1]도서명으로 삭제 [2]ISBN코드로 삭제");
                                int sel2 = sc.nextInt();
                                switch(sel2) {
                                    case 1:dao.bookDelete(); break;
                                    case 2:dao.bookDeleteISBN(); break;
                                } break;
                        case 3:dao.bookUpdate();
                    }
                case 4:
                case 5:

            }
        } else System.out.println("관리자 ID, Password를 잘못 입력하셨습니다!!");

    }
}
