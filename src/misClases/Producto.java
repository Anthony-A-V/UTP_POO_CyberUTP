package misClases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Producto {

    private int idProducto;
    private String nombreProducto;
    private String categoria;
    private int cantidad;
    private double precio;
    private byte[] imagen;

    public Producto() {

    }

    public Producto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(String nombreProducto, String categoria, int cantidad, double precio) {
        this.nombreProducto = nombreProducto;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void insertarDatos() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "insert into producto "
                    + "(NombreProducto, Categoria, Cantidad, Precio)"
                    + "values (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, getNombreProducto());
            pst.setString(2, getCategoria());
            pst.setInt(3, getCantidad());
            pst.setDouble(4, getPrecio());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto registrado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void mostrarDatos(JTable tblProductos) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String[] cabecera = { "IdProducto", "NombreProducto", "Categoria", "Cantidad", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        String SQL = "select * from producto";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {
                registros[0] = rs.getString("IdProducto");
                registros[1] = rs.getString("NombreProducto");
                registros[2] = rs.getString("Categoria");
                registros[3] = rs.getString("Cantidad");
                registros[4] = rs.getString("Precio");
                modelo.addRow(registros);
            }
            tblProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void actualizarDatos(JTable tblProductos) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "update producto set"
                    + " NombreProducto = ?, Categoria = ?, Cantidad = ?, Precio = ?"
                    + " where IdProducto = ?;";
            int filaSeccionada = tblProductos.getSelectedRow();
            String id = (String) tblProductos.getValueAt(filaSeccionada, 0);

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, getNombreProducto());
            pst.setString(2, getCategoria());
            pst.setInt(3, getCantidad());
            pst.setDouble(4, getPrecio());
            pst.setString(5, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto actualizado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void eliminarDatos(JTable tblProductos) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        try {
            String SQL = "delete from producto where IdProducto = ?;";
            int filaSeccionada = tblProductos.getSelectedRow();
            String id = (String) tblProductos.getValueAt(filaSeccionada, 0);

            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setString(1, id);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto eliminado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void buscarDato(JTable tblProductos) {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();

        String[] cabecera = { "IdProducto", "NombreProducto", "Categoria", "Cantidad", "Precio" };
        String[] registros = new String[10];
        DefaultTableModel modelo = new DefaultTableModel(null, cabecera);

        String SQL = "select * from producto where IdProducto = ?;";

        try {
            PreparedStatement pst = con.prepareStatement(SQL);
            pst.setInt(1, getIdProducto());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                registros[0] = rs.getString("IdProducto");
                registros[1] = rs.getString("NombreProducto");
                registros[2] = rs.getString("Categoria");
                registros[3] = rs.getString("Cantidad");
                registros[4] = rs.getString("Precio");
                modelo.addRow(registros);
            }
            tblProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public ArrayList<Producto> obtenerListaProductos() {

        ConexionBD cc = new ConexionBD();
        Connection con = cc.getConexion();
        ArrayList<Producto> misProductos = new ArrayList<>();

        String SQL = "select * from producto";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while (rs.next()) {

                Producto objProducto = new Producto();

                objProducto.setIdProducto(rs.getInt("IdProducto"));
                objProducto.setNombreProducto(rs.getString("NombreProducto"));
                objProducto.setCategoria(rs.getString("Categoria"));
                objProducto.setCantidad(rs.getInt("Cantidad"));
                objProducto.setPrecio(rs.getDouble("Precio"));
                objProducto.setImagen(rs.getBytes("Imagen"));

                misProductos.add(objProducto);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return misProductos;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", categoria="
                + categoria + ", cantidad=" + cantidad + ", precio=" + precio + ", image=" + imagen + '}';
    }
}
