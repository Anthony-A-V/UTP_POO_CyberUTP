package Modelo;

public interface DAODetallePedido {

    public boolean crear(DetallePedido detPedido);

    public boolean actualizar(DetallePedido detPedido);

    public boolean eliminar(DetallePedido detPedido);

    public boolean buscar(DetallePedido detPedido);
}
