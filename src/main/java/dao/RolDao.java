package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Rol;

public class RolDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaUsuario(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios where roles_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Rol rol) {
        try (Connection con = c.conexion()) {
            String sql = "insert into roles (rol) values (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rol.getRol());
            ps.executeUpdate();
            System.out.println("Rol creado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Rol rol) {
        try (Connection con = c.conexion()) {
            String sql = "update roles set rol=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rol.getRol());
            ps.setInt(2, rol.getId());
            ps.executeUpdate();
            System.out.println("Rol actualizado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar el rol?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaUsuario(id) == false) {
                        String sql = "delete from roles where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Rol eliminado correctamente! xD");
                    } else {
                        System.out.println("Este rol no puede ser eliminado, hay un usuario que lo esta referenciando!");
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

    public List<Rol> obtenerRoles() {
        List<Rol> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from roles");
            while (rs.next()) {
                lista.add(new Rol(rs.getInt(1), rs.getString("rol")));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Rol buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from roles");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new Rol(rs.getInt(1), rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}