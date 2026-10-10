package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Venta;

public class VentaDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaDetalleVenta(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_ventas where ventas_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Venta venta) {
        try (Connection con = c.conexion()) {
            String sql = "insert into ventas (usuarios_fk, total) values (?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, venta.getUsuarios_fk());
            ps.setDouble(2, venta.getTotal());
            ps.executeUpdate();
            System.out.println("Venta creada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Venta venta) {
        try (Connection con = c.conexion()) {
            String sql = "update ventas set usuarios_fk=?, total=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, venta.getUsuarios_fk());
            ps.setDouble(2, venta.getTotal());
            ps.setInt(3, venta.getId());
            ps.executeUpdate();
            System.out.println("Venta actualizada correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar la venta?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaDetalleVenta(id) == false) {
                        String sql = "delete from ventas where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Venta eliminada correctamente! xD");
                    } else {
                        System.out.println("Esta venta no puede ser eliminada, hay un detalle de venta que la esta referenciando!");
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

    public List<Venta> obtenerVentas() {
        List<Venta> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from ventas");
            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("fecha");
                OffsetDateTime fechaOd = ts != null ? ts.toInstant().atOffset(ZoneOffset.UTC) : null;
                lista.add(new Venta(
                        rs.getInt(1),
                        rs.getInt("usuarios_fk"),
                        fechaOd,
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Venta buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from ventas");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    Timestamp ts = rs.getTimestamp("fecha");
                    OffsetDateTime fechaOd = ts != null ? ts.toInstant().atOffset(ZoneOffset.UTC) : null;
                    return new Venta(
                            rs.getInt(1),
                            rs.getInt("usuarios_fk"),
                            fechaOd,
                            rs.getDouble("total")
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}