/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_inter;

/**
 *
 * @author mvale
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/hola"; // tu base de datos
        String user = "root"; // tu usuario de MySQL
        String password = "2222"; // tu contraseña de MySQL

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver de MySQL");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al1 conectar a la base de datos");
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        // Solo para probar la conexión
        getConnection();
    }
}
