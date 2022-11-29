package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOUsuarioEmpleadoImpl {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    public boolean iniciarSesion(UsuarioEmpleado usuarioEmpleado) {

        String consulta = "select * from empleado where Usuario = ? and Clave = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, usuarioEmpleado.getUsuario());
            pst.setString(2, usuarioEmpleado.getClave());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString(2).equals(usuarioEmpleado.getUsuario())
                        && rs.getString(3).equals(usuarioEmpleado.getClave())) {
                    usuarioEmpleado.setIdEmpleado(Integer.parseInt(rs.getString(1)));
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

    public boolean crear(UsuarioEmpleado usuarioEmpleado) {

        String consulta = "insert into empleado (`Usuario`, `Clave`, `Nombres`, `Apellidos`) "
                + "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, usuarioEmpleado.getUsuario());
            pst.setString(2, usuarioEmpleado.getClave());
            pst.setString(3, usuarioEmpleado.getNombres());
            pst.setString(4, usuarioEmpleado.getApellidos());
            pst.execute();
            System.out.println(pst);
            JOptionPane.showMessageDialog(null, "Empleado registrado");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public boolean actualizar(UsuarioEmpleado usuarioEmpleado) {

        String consulta = "update empleado set Usuario = ?, Clave = ?, Nombres = ?, Apellidos = ?"
                + " where IdEmpleado = ?;";

        try {
            String[] si_no = { "Sí", "No" };
            int valor = JOptionPane.showOptionDialog(null, "¿Está seguro de actualizar el empleado?",
                    "Actualizar Empleado", 0, JOptionPane.QUESTION_MESSAGE, null, si_no, null);
            if (valor == 0) {
                PreparedStatement pst = con.prepareStatement(consulta);
                pst.setString(1, usuarioEmpleado.getUsuario());
                pst.setString(2, usuarioEmpleado.getClave());
                pst.setString(3, usuarioEmpleado.getNombres());
                pst.setString(4, usuarioEmpleado.getApellidos());
                pst.setInt(5, usuarioEmpleado.getIdEmpleado());
                pst.execute();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public boolean eliminar(UsuarioEmpleado usuarioEmpleado) {

        String consulta = "delete from empleado where IdEmpleado = ?";

        try {
            String[] si_no = { "Sí", "No" };
            int valor = JOptionPane.showOptionDialog(null, "¿Está seguro de eliminar el empleado?",
                    "Eliminar Empleado", 0, JOptionPane.QUESTION_MESSAGE, null, si_no, null);
            if (valor == 0) {
                PreparedStatement pst = con.prepareStatement(consulta);
                pst.setString(1, String.valueOf(usuarioEmpleado.getIdEmpleado()));
                pst.execute();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public boolean buscar(UsuarioEmpleado usuarioEmpleado) {

        String consulta = "select * from empleado where IdEmpleado = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, usuarioEmpleado.getIdEmpleado());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                usuarioEmpleado.setIdEmpleado(rs.getInt("IdEmpleado"));
                usuarioEmpleado.setUsuario(rs.getString("Usuario"));
                usuarioEmpleado.setClave(rs.getString("Clave"));
                usuarioEmpleado.setNombres(rs.getString("Nombres"));
                usuarioEmpleado.setApellidos(rs.getString("Apellidos"));
                return true;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public ArrayList<UsuarioEmpleado> listar() {

        String consulta = "select * from empleado";
        ArrayList<UsuarioEmpleado> listUsuEmp = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                UsuarioEmpleado uEmpleado = new UsuarioEmpleado();

                uEmpleado.setIdEmpleado(rs.getInt("IdEmpleado"));
                uEmpleado.setUsuario(rs.getString("Usuario"));
                uEmpleado.setClave(rs.getString("Clave"));
                uEmpleado.setNombres(rs.getString("Nombres"));
                uEmpleado.setApellidos(rs.getString("Apellidos"));

                listUsuEmp.add(uEmpleado);
            }
            return listUsuEmp;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return listUsuEmp;
    }
}
