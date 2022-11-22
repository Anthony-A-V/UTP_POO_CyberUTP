package Modelo;

public interface DAOUsuarioCliente {

    public boolean crear(UsuarioCliente usuarioCliente);

    public boolean actualizar(UsuarioCliente usuarioCliente);

    public boolean eliminar(UsuarioCliente usuarioCliente);

    public boolean buscar(UsuarioCliente usuarioCliente);
}
