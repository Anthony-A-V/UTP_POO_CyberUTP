package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DAODetallePedidoImpl implements DAODetallePedido {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    @Override
    public boolean crear(DetallePedido detPedido) {

        String consulta = "insert into pedido_detalle"
                + " (IdPedido, IdProducto, Cantidad, Importe)"
                + " values (?, ?, ?, ?)";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, detPedido.getPedido().getIdPedido());
            pst.setInt(2, detPedido.getProducto().getIdProducto());
            pst.setInt(3, detPedido.getCantidad());
            pst.setDouble(4, detPedido.getImporte());
            pst.execute();
            // JOptionPane.showMessageDialog(null, "Detalle Pedido registrado");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    @Override
    public boolean actualizar(DetallePedido pedido) {

        return false;
    }

    @Override
    public boolean eliminar(DetallePedido pedido) {

        return false;
    }

    @Override
    public boolean buscar(DetallePedido pedido) {

        return false;
    }
}
