package Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class DetallePedido {
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private double importe;

}