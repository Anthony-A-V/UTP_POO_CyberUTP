package misClases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class UsuarioCliente extends Usuario {

    private String direccion;
    private int dni;
    private int telefono;

    public UsuarioCliente(String usuario, String clave) {
        super(usuario, clave);
    }

    public UsuarioCliente(String usuario, String clave, String direccion, int dni, int telefono) {
        super(usuario, clave);
        this.direccion = direccion;
        this.dni = dni;
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public boolean iniciarSesion() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String consulta = "select * from Usuario where Usuario = ? and Clave = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, getUsuario());
            pst.setString(2, getClave());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString(2).equals(getUsuario()) && rs.getString(3).equals(getClave())) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return false;
    }

    public boolean registrarCliente() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String consulta = "insert into usuario (Usuario, Clave, Dni, Telefono, Direccion) "
                + "values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, getUsuario());
            pst.setString(2, getClave());
            pst.setInt(3, getDni());
            pst.setInt(4, getTelefono());
            pst.setString(5, getDireccion());
            pst.execute();
            return true;

        } catch (SQLException ex) {

            if (String.valueOf(ex).contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(null, "Usuario ya registrado", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, ex, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    public int obtenerId() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();
        int idUsuario = 0;

        String consulta = "select idUsuario from usuario where Usuario = ? and Clave = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, getUsuario());
            pst.setString(2, getClave());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idUsuario = Integer.parseInt(rs.getString("IdUsuario"));
                return idUsuario;
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
        return idUsuario;
    }
}
