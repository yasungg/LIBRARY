package com.kh.bookjdbc.dao;

import com.kh.bookjdbc.util.Common;
import com.kh.bookjdbc.vo.PostVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PostDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    public List<PostVO> postSelect() {
        List<PostVO> list = new ArrayList<>();
        System.out.println();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM POST";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int postNo = rs.getInt("POST_NO");
                String title = rs.getString("TITLE");
                String userID = rs.getString("USER_ID");
                String content = rs.getString("PCONTENT");
                Date postDate = rs.getDate("POST_DATE");
                PostVO vo = new PostVO(postNo, title, userID, content, postDate);
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 1번 기능: 1번 기능: 게시판 전체 글 + 메뉴 선택 보여주기 2,3,10번 기능 선택
    public int postSelectPrn(List<PostVO> list) {
        System.out.println("======================== 게시판 ========================");
        System.out.println("글 번호              글 제목           글 작성자    글 작성 일자");
        System.out.println("-------------------------------------------------------");
        for (PostVO e : list) {
            System.out.print(e.getPostNo() + "       ");
            System.out.print(e.getTitle() + "     ");
            System.out.print(e.getUserID() + " ");
            //System.out.print(e.getpContent() + " ");
            System.out.print(e.getPostDate() + " ");
            System.out.println();
        }
        System.out.println("---------------------------------------------------------");
        System.out.print("[1] 게시글 작성하기, [2] 게시글 조회하기, [3] 메인 메뉴로 돌아가기 : ");
        int sel = sc.nextInt();
        return sel;
    }

    // 2번 기능: 게시글 작성하기 메뉴
    public void postInsert() {
        System.out.println("글 정보를 입력하세요.");
        System.out.print("게시글 번호: ");
        int postNo = sc.nextInt();
        System.out.print("글 제목: ");
        String title = sc.next();
        System.out.print("글 작성자: ");
        String userID = sc.next();
        System.out.print("글 내용: ");
        String content = sc.next();
        System.out.print("글 작성일: ");
        String postDate = sc.next();

        String sql = "INSERT INTO POST VALUES(?,?,?,?,?)";

        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, postNo);
            pStmt.setString(2, title);
            pStmt.setString(3, userID);
            pStmt.setString(4, content);
            pStmt.setString(5, postDate);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(stmt);
        Common.close(conn);
    }

    // 3번 기능: 게시글 조회하기 메뉴 4,7,8,9번 기능 선택
    public int postShow(List<PostVO> list) {
        System.out.print("조회하려는 게시글 번호 입력: ");
        int num = sc.nextInt();
        String sql = "SELECT * FROM POST WHERE POST_NO = " + num + ";";

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println();
                System.out.println("---------------------------------------------------------");
                System.out.println("글 번호              제목                    작성자   작성일");
                System.out.println(rs.getInt("POST_NO") + " " + rs.getString("TITLE") + " "
                        + rs.getString("USER_ID") + " " + rs.getDate("POST_DATE"));
                System.out.println("내용: ");
                System.out.println(rs.getString("PCONTENT"));
                System.out.println("---------------------------------------------------------");
                System.out.println("댓글 목록");
                System.out.print("번호: " + "pcomment테이블 이어붙이기" + " 작성자: " + "pcomment테이블 이어붙이기" + " 작성일: " + "pcomment테이블 이어붙이기");
                System.out.println("내용: " + "pcomment테이블 이어붙이기");
                System.out.println("---------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[1] 게시글 수정하기, [2] 게시글 삭제하기, [3] 댓글 작성하기, [4] 댓글 수정하기, [5] 댓글 삭제하기 [6] 게시판으로 이동: ");
        int sel = sc.nextInt();
        return sel;
    }

    // 4번 기능: 게시글 수정하기 메뉴, 5,6번 기능 선택
    public int postUpdate() {


        System.out.println("수정할 부분을 선택하세요.");
        System.out.print("[1] 글 제목, [2] 글 내용 : ");
        int sel = sc.nextInt();
        return sel;
    }

    // 5번 기능: 글 제목 수정하기
    public void postTitleUpdate() {
        System.out.print("변경할 글 번호를 입력하세요: ");
        String postID = sc.next();
        System.out.print("변경할 글 제목을 입력하세요: ");
        String title = sc.nextLine();
        String sql = "UPDATE POST SET TITLE = ? WHERE POST_NO = ?;";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, title);
            pStmt.setString(2, postID);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        System.out.println("게시글 수정이 완료되었습니다.");
    }

    // 6번 기능: 글 내용 수정하기
    public void postContentUpdate() {
        System.out.print("변경할 글 번호를 입력하세요: ");
        String postID = sc.next();
        System.out.print("변경할 글 내용을 입력하세요: ");
        String content = sc.nextLine();
        String sql = "UPDATE POST SET CONTENT = ? WHERE POST_NO = ?;";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, content);
            pStmt.setString(2, postID);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        System.out.println("게시글 수정이 완료되었습니다.");
    }

    // 7번 기능: 게시글 삭제하기 메뉴
    public void postDelete() {
        System.out.print("삭제할 글 번호를 입력: ");
        String postNo = sc.next();
        String sql = "DELETE FROM POST WHERE POST_NO = ?";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, postNo);
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        System.out.println("게시글 삭제가 완료되었습니다.");
    }

    // 8번 기능: 댓글 작성하기 메뉴
    public void commentMake() {

    }

    // 9번 기능: 댓글 수정하기 메뉴
    public void commentUpdate() {

    }

    // 10번 기능: 댓글 삭제하기 메뉴
    public void commentDelete() {

    }
}