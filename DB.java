package com;

import java.sql.*;
import java.text.ParseException;

public class DB {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载数据库驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DB() {
    }

    public static void closeAll(ResultSet resultSet,Statement statement,Connection connection){
        DB.closeRs(resultSet);
        DB.closeStmt(statement);
        DB.closeConn(connection);
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "99173706FOX?");//连接数据库
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void closeConn(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Statement getStmt(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    public static PreparedStatement getPStmt(Connection conn, String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    public static void closeStmt(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet executeQuery(Statement stmt, String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void closeRs(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(Connection conn, String sql) {//重载
        ResultSet rs = null;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //插入数据
    public static void saveinfo(String name,String adress,String className) throws ParseException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DB.getConn();
            String sql = "insert into student value (null,?,?,?) ";
            pstmt = DB.getPStmt(conn, sql);
            pstmt.setString(1, name);
            pstmt.setString(2, adress);
            pstmt.setString(3, className);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeStmt(pstmt);
            DB.closeConn(conn);
        }
    }
}
