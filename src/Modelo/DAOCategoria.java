package Modelo;

public interface DAOCategoria {

    public boolean crear(Categoria categoria);

    public boolean actualizar(Categoria categoria);

    public boolean eliminar(Categoria categoria);

    public boolean buscar(Categoria categoria);

}
