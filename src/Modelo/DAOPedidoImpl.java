package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DAOPedidoImpl implements DAOPedido {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    @Override
    public boolean crear(Pedido pedido) {
        String consulta = "insert into pedido "
                + "(IdCliente, Subtotal, Igv, Total)"
                + "values (?,?,?,?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, pedido.getUsuarioCliente().getIdCliente());
            pst.setDouble(2, pedido.getSubtotal());
            pst.setDouble(3, pedido.getIgv());
            pst.setDouble(4, pedido.getTotal());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Pedido registrado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean actualizar(Pedido pedido) {

        return false;
    }

    @Override
    public boolean eliminar(Pedido pedido) {

        return false;
    }

    @Override
    public boolean buscar(Pedido pedido) {

        return false;
    }

    public boolean obtenerIdActual(Pedido pedido) {
        String consulta = "select ifnull(max(idPedido),1) as IdPedido from pedido";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                pedido.setIdPedido(rs.getInt("IdPedido"));
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
