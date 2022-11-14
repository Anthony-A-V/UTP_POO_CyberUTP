package misClases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UsuarioEmpleado extends Usuario {

    private int idEmpleado;
    String nombres;
    String apellidos;

    public UsuarioEmpleado() {
        super();
    }

    public UsuarioEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public UsuarioEmpleado(String usuario, String clave) {
        super(usuario, clave);
    }

    public UsuarioEmpleado(String nombres, String apellidos, String clave) {
        super(clave);
        this.nombres = nombres;
        this.apellidos = apellidos;

    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdidEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public boolean iniciarSesion() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String consulta = "select * from empleado where IdEmpleado = ? and Clave = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, getUsuario());
            pst.setString(2, getClave());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals(getUsuario()) && rs.getString(2).equals(getClave())) {
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

    public void registrarEmpleado() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "insert into empleado (`Clave`, `Nombres`, `Apellidos`) "
                    + "VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, getClave());
            pst.setString(2, getNombres());
            pst.setString(3, getApellidos());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Empleado registrado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void mostrarEmpleados(JTable tblEmpleados) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String[] cabecera = { "IdEmpleado", "Clave", "Nombres", "Apellidos" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        String SQL = "select * from empleado";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                registros[0] = rs.getString("IdEmpleado");
                registros[1] = rs.getString("Clave");
                registros[2] = rs.getString("Nombres");
                registros[3] = rs.getString("Apellidos");
                modelo.addRow(registros);
            }
            tblEmpleados.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void actualizarDatos(JTable tblEmpleados) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "update empleado set"
                    + " Clave = ?, Nombres = ?, Apellidos = ?"
                    + " where IdEmpleado = ?;";
            int filaSeccionada = tblEmpleados.getSelectedRow();
            String id = (String) tblEmpleados.getValueAt(filaSeccionada, 0);

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, getClave());
            pst.setString(2, getNombres());
            pst.setString(3, getApellidos());
            pst.setString(4, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Empleado actualizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void eliminarDatos(JTable tblEmpleados) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "delete from empleado where IdEmpleado = ?;";
            int filaSeccionada = tblEmpleados.getSelectedRow();
            String id = (String) tblEmpleados.getValueAt(filaSeccionada, 0);

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Empleado eliminado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void buscarEmpleados(JTable tblEmpleados) {
        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();
        String[] cabecera = { "IdEmpleado", "Clave", "Nombres", "Apellidos" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);
        String SQL = "select * from empleado where IdEmpleado= ?";
        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setInt(1, getIdEmpleado());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("IdEmpleado");
                registros[1] = rs.getString("Clave");
                registros[2] = rs.getString("Nombres");
                registros[3] = rs.getString("Apellidos");
                modelo.addRow(registros);
            }
            tblEmpleados.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

}
