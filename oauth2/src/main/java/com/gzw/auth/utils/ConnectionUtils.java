package com.gzw.auth.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

    public static Connection getConn() {
        Connection connection = null;
        String driver = "com.mysql.jdbc.Driver";// 驱动程序类名
        String url = "jdbc:mysql://localhost:3306/gzw?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "root";
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    // 释放数据库连接
    public static void releaseConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
