package com.project.orcamentofly.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {

    public static Connection getConexao() throws ClassNotFoundException, SQLException {

        String DRIVER = "com.mysql.cj.jdbc.Driver";
        String URL = "jdbc:mysql://localhost:3306/banco";
        String USERNAME = "teste";
        String PASSWORD = "TesTe@12";

        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
