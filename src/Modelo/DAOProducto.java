package Modelo;

public interface DAOProducto {

    public boolean crear(Producto producto);

    public boolean actualizar(Producto producto);

    public boolean eliminar(Producto producto);

    public boolean buscar(Producto producto);
}
