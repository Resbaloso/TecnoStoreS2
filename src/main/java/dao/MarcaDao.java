package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Marca;

public class MarcaDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaCelular(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from celulares where marcas_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Marca marca) {
        try (Connection con = c.conexion()) {
            String sql = "insert into marcas (nombre) values (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, marca.getNombre());
            ps.executeUpdate();
            System.out.println("Marca creada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Marca marca) {
        try (Connection con = c.conexion()) {
            String sql = "update marcas set nombre=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getId());
            ps.executeUpdate();
            System.out.println("Marca actualizada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar la marca?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaCelular(id) == false) {
                        String sql = "delete from marcas where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Marca eliminada correctamente! xD");
                    } else {
                        System.out.println("Esta marca no puede ser eliminada, hay un celular que la esta referenciando!");
                    }
                } else {
                    System.out.println("Operacion cancelada, el id no existe");
                }
            } else {
                System.out.println("Operacion cancelada correctamente!");
            }
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public List<Marca> obtenerMarcas() {
        List<Marca> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from marcas");
            while (rs.next()) {
                lista.add(new Marca(rs.getInt(1), rs.getString("nombre")));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Marca buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from marcas");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new Marca(rs.getInt(1), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}