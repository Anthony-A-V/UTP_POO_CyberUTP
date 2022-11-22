package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {

    private String usuario = "root";
    private String password = "123456789";
    private String url = "jdbc:mysql://localhost:3306/cyberutp?allowPublicKeyRetrieval=true&useSSL=false";
    public static Connection con;

    public Conexion() {
        con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url, usuario, password);
            // JOptionPane.showMessageDialog(null, "Conexión establecida");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return con;
    }

    public void desconectar() {
        con = null;
    }
}
