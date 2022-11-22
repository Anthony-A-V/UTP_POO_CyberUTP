package Modelo;

@FunctionalInterface

public interface IPedidoExistente {
    boolean pedidoExistente(DetallePedido pedido);
}