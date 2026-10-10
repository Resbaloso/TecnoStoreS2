package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Personas;

public class PersonasDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaUsuario(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios where personas_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Personas personas) {
        try (Connection con = c.conexion()) {
            String sql = "insert into personas (nombre, identificacion, correo, telefono) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personas.getNombre());
            ps.setString(2, personas.getIdentificacion());
            ps.setString(3, personas.getCorreo());
            ps.setInt(4, personas.getTelefono());
            ps.executeUpdate();
            System.out.println("Persona creada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Personas personas) {
        try (Connection con = c.conexion()) {
            String sql = "update personas set nombre=?, identificacion=?, correo=?, telefono=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, personas.getNombre());
            ps.setString(2, personas.getIdentificacion());
            ps.setString(3, personas.getCorreo());
            ps.setInt(4, personas.getTelefono());
            ps.setInt(5, personas.getId());
            ps.executeUpdate();
            System.out.println("Persona actualizada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar la persona?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaUsuario(id) == false) {
                        String sql = "delete from personas where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Persona eliminada correctamente! xD");
                    } else {
                        System.out.println("Esta persona no puede ser eliminada, hay un usuario que la esta referenciando!");
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

    public List<Personas> obtenerPersonas() {
        List<Personas> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from personas");
            while (rs.next()) {
                lista.add(new Personas(rs.getInt(1), rs.getString("nombre"), rs.getString("identificacion"), rs.getString("correo"), rs.getInt("telefono")));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Personas buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from personas");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new Personas(rs.getInt(1), rs.getString("nombre"), rs.getString("identificacion"), rs.getString("correo"), rs.getInt("telefono"));
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}