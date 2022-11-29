package Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Producto extends Conexion {

    private int idProducto;
    private String nombreProducto;
    private double precio;
    private int stock;
    private Categoria categoria;
    private UsuarioEmpleado usuarioEmpleado;

}