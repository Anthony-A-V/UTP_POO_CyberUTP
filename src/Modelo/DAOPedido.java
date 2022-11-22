package Modelo;

public interface DAOPedido {

    public boolean crear(Pedido pedido);

    public boolean actualizar(Pedido pedido);

    public boolean eliminar(Pedido pedido);

    public boolean buscar(Pedido pedido);
}
