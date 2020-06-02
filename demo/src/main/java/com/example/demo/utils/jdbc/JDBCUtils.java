package com.example.demo.utils.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.utils.YmlUtils;
/*
 *	jdbc 
 */
public class JDBCUtils {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块加载配置文件信息
    static {
        try {
            //获取相关参数的值
            driver = String.valueOf(YmlUtils.getYmlConfig("spring.druid.driver-class-name"));
            url = String.valueOf(YmlUtils.getYmlConfig("spring.druid.jdbcUrl"));
            username = String.valueOf(YmlUtils.getYmlConfig("spring.druid.username"));
            password = String.valueOf(YmlUtils.getYmlConfig("spring.druid.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放资源
     * @param conn
     * @param pstmt
     * @param rs
     */
    public static void relase(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
