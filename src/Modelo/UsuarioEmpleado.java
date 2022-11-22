package Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UsuarioEmpleado extends Usuario {

    private int idEmpleado;
    private String nombres;
    private String apellidos;

}