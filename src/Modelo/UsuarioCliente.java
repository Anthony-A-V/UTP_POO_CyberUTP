package Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UsuarioCliente extends Usuario {

    private int idCliente;
    private String nombres;
    private String apellidos;
    private int dni;
    private int telefono;
    private String direccion;

}