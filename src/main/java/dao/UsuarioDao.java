package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Usuario;

public class UsuarioDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaVenta(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from ventas where usuarios_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Usuario usuario) {
        try (Connection con = c.conexion()) {
            String sql = "insert into usuarios (personas_fk, username, contraseña, roles_fk) values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getPersonas_fk());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContraseña());
            ps.setInt(4, usuario.getRoles_fk());
            ps.executeUpdate();
            System.out.println("Usuario creado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Usuario usuario) {
        try (Connection con = c.conexion()) {
            String sql = "update usuarios set personas_fk=?, username=?, contraseña=?, roles_fk=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuario.getPersonas_fk());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContraseña());
            ps.setInt(4, usuario.getRoles_fk());
            ps.setInt(5, usuario.getId());
            ps.executeUpdate();
            System.out.println("Usuario actualizado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar el usuario?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaVenta(id) == false) {
                        String sql = "delete from usuarios where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Usuario eliminado correctamente! xD");
                    } else {
                        System.out.println("Este usuario no puede ser eliminado, hay una venta que lo esta referenciando!");
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

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios");
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt(1),
                        rs.getInt("personas_fk"),
                        rs.getString("username"),
                        rs.getString("contraseña"),
                        rs.getInt("roles_fk")
                ));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Usuario buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from usuarios");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new Usuario(
                            rs.getInt(1),
                            rs.getInt("personas_fk"),
                            rs.getString("username"),
                            rs.getString("contraseña"),
                            rs.getInt("roles_fk")
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
    
    public boolean existeUsername(String username) {
        String sql = "select id from usuarios where username = ?";
        try (Connection con = c.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar username: " + e.getMessage());
            return true;
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "insert into usuarios (personas_fk, username, contraseña, roles_fk) values (?, ?, ?, ?)";
        try (Connection con = c.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuario.getPersonas_fk());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getContraseña());
            ps.setInt(4, usuario.getRoles_fk());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario iniciarSesion(String username, String password) {
        String sql = "select id, personas_fk, username, contraseña, roles_fk from usuarios where username = ? AND contraseña = ?";
        try (Connection con = c.conexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setPersonas_fk(rs.getInt("personas_fk"));
                    usuario.setUsername(rs.getString("username"));
                    usuario.setContraseña(rs.getString("contraseña"));
                    usuario.setRoles_fk(rs.getInt("roles_fk"));
                    return usuario;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la autenticación: " + e.getMessage());
        }
        return null;
    }
}