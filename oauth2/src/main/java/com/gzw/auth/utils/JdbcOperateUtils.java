package com.gzw.auth.utils;

import com.gzw.auth.domain.TokenTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JdbcOperateUtils {

    /**
     * 查询token是否存在
     * @param principal
     * @return
     */
    public static String query(String principal) {
        Connection connection = ConnectionUtils.getConn();
        String sql = "SELECT access FROM tokentemplate WHERE principal = ?";
        PreparedStatement preparedStatement = null;
        String access = "gzw";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, principal);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                access = resultSet.getString(1);
            }
            return access;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.releaseConnection(connection);
        }
        return access;
    }

    /**
     * 持久化token到数据库
     *
     * @param tokenTemplate
     */
    public static void update(TokenTemplate tokenTemplate) {
        Connection connection = ConnectionUtils.getConn();
        String sql1 = "INSERT INTO tokentemplate VALUES (?,?,?,?,?,?,?)";
        String sql2 = "UPDATE tokentemplate SET tokenType=?,expiration=?,access=?,auth_to_access=?,uname_to_access=?,client_id_to_access=? WHERE principal=? ";
        PreparedStatement preparedStatement = null;
        if (query(tokenTemplate.getPrincipal()).equals("gzw")){
            try {
                preparedStatement = connection.prepareStatement(sql1);
                preparedStatement.setString(1, tokenTemplate.getPrincipal());
                preparedStatement.setString(2, tokenTemplate.getTokenType());
                preparedStatement.setString(3, dateToString(tokenTemplate.getExpiration()));
                preparedStatement.setString(4, tokenTemplate.getAccess());
                preparedStatement.setString(5, tokenTemplate.getAuth_to_access());
                preparedStatement.setString(6, tokenTemplate.getUname_to_access());
                preparedStatement.setString(7, tokenTemplate.getClient_id_to_access());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                ConnectionUtils.releaseConnection(connection);
            }
        }else {
            try {
                preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setString(7, tokenTemplate.getPrincipal());
                preparedStatement.setString(1, tokenTemplate.getTokenType());
                preparedStatement.setString(2, dateToString(tokenTemplate.getExpiration()));
                preparedStatement.setString(3, tokenTemplate.getAccess());
                preparedStatement.setString(4, tokenTemplate.getAuth_to_access());
                preparedStatement.setString(5, tokenTemplate.getUname_to_access());
                preparedStatement.setString(6, tokenTemplate.getClient_id_to_access());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                ConnectionUtils.releaseConnection(connection);
            }

        }

    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
        return sdf.format(date);
    }
}
