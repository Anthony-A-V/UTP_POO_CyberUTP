package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DAOCategoriaImpl implements DAOCategoria {

    Conexion cc = new Conexion();
    Connection con = cc.getConexion();

    @Override
    public boolean crear(Categoria categoria) {

        return false;
    }

    @Override
    public boolean actualizar(Categoria categoria) {

        return false;
    }

    @Override
    public boolean eliminar(Categoria categoria) {

        return false;
    }

    @Override
    public boolean buscar(Categoria categoria) {
        String consulta = "select * from categoria where IdCategoria = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setInt(1, categoria.getIdCategoria());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                categoria.setIdCategoria(rs.getInt("IdCategoria"));
                categoria.setNombre(rs.getString("NombreCategoria"));
                return true;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public boolean buscarPorNombre(Categoria categoria) {
        String consulta = "select * from categoria where NombreCategoria = ?";

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            pst.setString(1, categoria.getNombre());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                categoria.setIdCategoria(rs.getInt("IdCategoria"));
                categoria.setNombre(rs.getString("NombreCategoria"));
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return false;
    }

    public ArrayList<Categoria> listar() {

        String consulta = "select * from categoria";
        ArrayList<Categoria> listCat = new ArrayList<>();

        try {
            PreparedStatement pst = con.prepareStatement(consulta);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Categoria cat = new Categoria();

                cat.setIdCategoria(rs.getInt("IdCategoria"));
                cat.setNombre(rs.getString("NombreCategoria"));

                listCat.add(cat);
            }
            return listCat;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            cc.desconectar();
        }
        return listCat;
    }
}
