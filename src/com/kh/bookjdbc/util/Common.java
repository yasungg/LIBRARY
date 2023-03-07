package com.kh.bookjdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Common {
    final static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    final static String ORACLE_ID = "LIBRARY";
    final static String ORACLE_PW = "1234";
    final static String ORACLE_DRV = "oracle.jdbc.driver.OracleDriver";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(ORACLE_DRV);
            conn = DriverManager.getConnection(ORACLE_URL, ORACLE_ID, ORACLE_PW);
            System.out.println("연결 성공!");
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection 해제");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void close(Statement stmt) {
        try {
            if(stmt != null && !stmt.isClosed()) {
                stmt.close();
                System.out.println("Connection 해제");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void close(ResultSet rs) {
        try {
            if(rs != null && !rs.isClosed()) {
                rs.close();
                System.out.println("Connection 해제");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
