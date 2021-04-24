/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Usuário
 */
public class MysqlConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/sicoinb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection connectDB() throws SQLException {
      
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        return conn;
    }
}
