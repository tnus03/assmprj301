/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */


import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=LeaveRequestSystem";
        String user = "sa";
        String password = "123456";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, user, password);
    }
}

