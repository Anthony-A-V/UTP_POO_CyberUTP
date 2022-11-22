package Modelo;

public interface DAOUsuarioEmpleado {

    public boolean crear(UsuarioEmpleado usuarioEmpleado);

    public boolean actualizar(UsuarioEmpleado usuarioEmpleado);

    public boolean eliminar(UsuarioEmpleado usuarioEmpleado);

    public boolean buscar(UsuarioEmpleado usuarioEmpleado);
}
