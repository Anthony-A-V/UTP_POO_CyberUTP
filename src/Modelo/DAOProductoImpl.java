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
                + "(NombreProducto, Categoria, Stock, Precio, IdEmpleado)"
                + "values (?,?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, producto.getNombreProducto());
            pst.setString(2, producto.getCategoria());
            pst.setInt(3, producto.getStock());
            pst.setDouble(4, producto.getPrecio());
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
                + " NombreProducto = ?, Categoria = ?, Stock = ?, Precio = ?"
                + " where IdProducto = ?;";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, producto.getNombreProducto());
            pst.setString(2, producto.getCategoria());
            pst.setInt(3, producto.getStock());
            pst.setDouble(4, producto.getPrecio());
            pst.setInt(5, producto.getIdProducto());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto actualizado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean eliminar(Producto producto) {

        String consulta = "delete from producto where IdProducto = ?;";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, producto.getIdProducto());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Producto eliminado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean buscar(Producto producto) {

        String consulta = "select * from producto where IdProducto = ?;";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, producto.getIdProducto());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                producto.setIdProducto(rs.getInt("IdProducto"));
                producto.setNombreProducto(rs.getString("NombreProducto"));
                producto.setCategoria(rs.getString("Categoria"));
                producto.setStock(rs.getInt("Stock"));
                producto.setPrecio(rs.getDouble("Precio"));
            }
            return true;
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

                prod.setIdProducto(rs.getInt("IdProducto"));
                prod.setNombreProducto(rs.getString("NombreProducto"));
                prod.setCategoria(rs.getString("Categoria"));
                prod.setStock(Integer.parseInt(rs.getString("Stock")));
                prod.setPrecio(Double.parseDouble(rs.getString("Precio")));

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
