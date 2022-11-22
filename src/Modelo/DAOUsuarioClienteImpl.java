package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DAOUsuarioClienteImpl implements DAOUsuarioCliente {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    public boolean iniciarSesion(UsuarioCliente usuarioCliente) {

        String consulta = "select * from cliente where Usuario = ? and Clave = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, usuarioCliente.getUsuario());
            pst.setString(2, usuarioCliente.getClave());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString(2).equals(usuarioCliente.getUsuario())
                        && rs.getString(5).equals(usuarioCliente.getClave())) {
                    usuarioCliente.setIdCliente(Integer.parseInt(rs.getString(1)));
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean crear(UsuarioCliente usuarioCliente) {

        String consulta = "insert into cliente (Usuario, Nombres, Apellidos, Clave, Dni, Telefono, Direccion) "
                + "values (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, usuarioCliente.getUsuario());
            pst.setString(2, usuarioCliente.getNombres());
            pst.setString(3, usuarioCliente.getApellidos());
            pst.setString(4, usuarioCliente.getClave());
            pst.setInt(5, usuarioCliente.getDni());
            pst.setInt(6, usuarioCliente.getTelefono());
            pst.setString(7, usuarioCliente.getDireccion());
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
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean actualizar(UsuarioCliente usuarioCliente) {
        return false;
    }

    @Override
    public boolean eliminar(UsuarioCliente usuarioCliente) {
        return false;
    }

    @Override
    public boolean buscar(UsuarioCliente usuarioCliente) {
        String consulta = "select * from cliente where IdCliente = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, usuarioCliente.getIdCliente());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                usuarioCliente.setIdCliente(rs.getInt("IdCliente"));
                usuarioCliente.setUsuario(rs.getString("Usuario"));
                usuarioCliente.setNombres(rs.getString("Nombres"));
                usuarioCliente.setApellidos(rs.getString("Apellidos"));
                usuarioCliente.setClave(rs.getString("Clave"));
                usuarioCliente.setDni(rs.getInt("Dni"));
                usuarioCliente.setTelefono(rs.getInt("Telefono"));
                usuarioCliente.setDireccion(rs.getString("Direccion"));
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

}
