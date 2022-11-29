package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOProductoImpl implements DAOProducto {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    @Override
    public boolean crear(Producto producto) {

        String consulta = "insert into producto "
                + "(NombreProducto, Stock, Precio, IdCategoria, IdEmpleado)"
                + " values (?, ?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, producto.getNombreProducto());
            pst.setInt(2, producto.getStock());
            pst.setDouble(3, producto.getPrecio());
            pst.setInt(4, producto.getCategoria().getIdCategoria());
            pst.setInt(5, producto.getUsuarioEmpleado().getIdEmpleado());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto registrado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean actualizar(Producto producto) {

        String consulta = "update producto set"
                + " NombreProducto = ?, Stock = ?, Precio = ?,  IdCategoria = ?, IdEmpleado = ?"
                + " where IdProducto = ?";

        try {
            String[] si_no = { "Sí", "No" };
            int valor = JOptionPane.showOptionDialog(null, "¿Está seguro de actualizar el producto?",
                    "Actualizar Producto", 0, JOptionPane.QUESTION_MESSAGE, null, si_no, null);
            if (valor == 0) {
                PreparedStatement pst = con.prepareStatement(consulta);
                pst.setString(1, producto.getNombreProducto());
                pst.setInt(2, producto.getStock());
                pst.setDouble(3, producto.getPrecio());
                pst.setInt(4, producto.getCategoria().getIdCategoria());
                pst.setInt(5, producto.getUsuarioEmpleado().getIdEmpleado());
                pst.setInt(6, producto.getIdProducto());
                pst.execute();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean eliminar(Producto producto) {

        String consulta = "delete from producto where IdProducto = ?";

        try {
            String[] si_no = { "Sí", "No" };
            int valor = JOptionPane.showOptionDialog(null, "¿Está seguro de eliminar el producto?",
                    "Eliminar Producto", 0, JOptionPane.QUESTION_MESSAGE, null, si_no, null);
            if (valor == 0) {
                PreparedStatement pst = con.prepareStatement(consulta);
                pst.setInt(1, producto.getIdProducto());
                pst.execute();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean buscar(Producto producto) {

        String consulta = "select * from producto where IdProducto = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, producto.getIdProducto());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setNombreProducto(rs.getString("NombreProducto"));
                producto.setStock(rs.getInt("Stock"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.getCategoria().setIdCategoria(rs.getInt("IdCategoria"));
                producto.getUsuarioEmpleado().setIdEmpleado(rs.getInt("IdEmpleado"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public ArrayList<Producto> listar() {

        String consulta = "select * from producto";
        ArrayList<Producto> listProd = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Producto prod = new Producto();
                Categoria cat = new Categoria();
                UsuarioEmpleado usuEmp = new UsuarioEmpleado();
                prod.setCategoria(cat);
                prod.setUsuarioEmpleado(usuEmp);

                prod.setIdProducto(rs.getInt("IdProducto"));
                prod.setNombreProducto(rs.getString("NombreProducto"));
                prod.setStock(Integer.parseInt(rs.getString("Stock")));
                prod.setPrecio(Double.parseDouble(rs.getString("Precio")));
                prod.getCategoria().setIdCategoria(rs.getInt("IdCategoria"));
                prod.getUsuarioEmpleado().setIdEmpleado(rs.getInt("IdEmpleado"));

                listProd.add(prod);
            }
            return listProd;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return listProd;
    }
}
