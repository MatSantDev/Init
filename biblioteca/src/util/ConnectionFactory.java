package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Exception.DBException;

public class ConnectionFactory {

    private static Connection conn = null;

    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/orcamento_db";
        String user = "root";
        String password = "";

        try {
            if (conn == null){
                conn = DriverManager.getConnection(url,user,password);


            }
        } catch (SQLException e) {
            throw new DBException(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

}
