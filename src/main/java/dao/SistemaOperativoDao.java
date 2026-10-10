package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.SistemaOperativo;

public class SistemaOperativoDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaCelular(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from celulares where sistemas_operativos_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(SistemaOperativo so) {
        try (Connection con = c.conexion()) {
            String sql = "insert into sistemas_operativos (nombre) values (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, so.getNombre());
            ps.executeUpdate();
            System.out.println("Sistema operativo creado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(SistemaOperativo so) {
        try (Connection con = c.conexion()) {
            String sql = "update sistemas_operativos set nombre=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, so.getNombre());
            ps.setInt(2, so.getId());
            ps.executeUpdate();
            System.out.println("Sistema operativo actualizado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar el sistema operativo?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaCelular(id) == false) {
                        String sql = "delete from sistemas_operativos where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Sistema operativo eliminado correctamente! xD");
                    } else {
                        System.out.println("Este sistema operativo no puede ser eliminado, hay un celular que lo esta referenciando!");
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

    public List<SistemaOperativo> obtenerSistemasOperativos() {
        List<SistemaOperativo> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from sistemas_operativos");
            while (rs.next()) {
                lista.add(new SistemaOperativo(rs.getInt(1), rs.getString("nombre")));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public SistemaOperativo buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from sistemas_operativos");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new SistemaOperativo(rs.getInt(1), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}