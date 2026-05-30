package com.mycompany.java_nonoy.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // CAMBIO AQUÍ: Se configuró 'base_multi' en la URL de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/base_multi?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456"; // <-- Recuerda verificar si esta es tu contraseña de MySQL

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver de MySQL no encontrado en el proyecto.");
        }
    }
}