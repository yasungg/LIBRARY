package com.kh.bookjdbc;

import java.util.Scanner;

public class BookMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("[1]회원 로그인 [2]관리자 로그인");
        System.out.print("로그인 옵션을 선택하세요. : ");
        int sel1 = sc.nextInt();
        switch(sel1) {
            case 1:
            case 2:
             Admin admin = new Admin();
             admin.AdminLogin();
            default : System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
        }
    }
}
