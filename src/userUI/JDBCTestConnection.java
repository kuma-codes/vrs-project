/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCTestConnection {

    // Replace these with your actual DB credentials
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=testDB;encrypt=true;trustServerCertificate=true";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin456";

    public static void main(String[] args) {
        System.out.println("Testing database connection...");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            if (conn != null) {
                System.out.println("✅ Connection successful!");
            } else {
                System.out.println("⚠️ Connection failed.");
            }
        } catch (SQLException e) {
            System.out.println("❌ JDBC Connection Error:");
            System.out.println(e.getMessage());
        }
    }
}