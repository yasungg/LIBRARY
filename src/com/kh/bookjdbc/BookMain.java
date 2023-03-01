package com.kh.bookjdbc;

import java.util.Scanner;

public class BookMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("[1]회원 [2]관리자 : ");
        int sel1 = sc.nextInt();
        switch(sel1) {
            case 1:
            case 2:
             Admin admin = new Admin();
             admin.AdminLogin();
        }
    }
}
