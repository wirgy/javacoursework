package com.company;

import com.mysql.cj.MysqlConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySqlConnection {

    private static Connection mysqlConnection;

    public static Connection getConnection(){
        try {

            Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            if (mysqlConnection==null) mysqlConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb1", "root", "" );

            return mysqlConnection;

        } catch (SQLException| ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MysqlConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
