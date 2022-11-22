package Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Pedido {

    private int idPedido;
    private UsuarioCliente usuarioCliente;
    private double subtotal;
    private double igv;
    private double total;

}
