package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.DetalleVenta;

public class DetalleVentaDao {

    Conexion c = new Conexion();

    public void crear(DetalleVenta dv) {
        try (Connection con = c.conexion()) {
            String sql = "insert into detalles_ventas (ventas_fk, celulares_fk, cantidad, precio, subtotal) "
                    + "values (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getVentas_fk());
            ps.setInt(2, dv.getCelulares_fk());
            ps.setInt(3, dv.getCantidad());
            ps.setInt(4, dv.getPrecios_fk());
            ps.setDouble(5, dv.getSubtotal());
            ps.executeUpdate();
            System.out.println("Detalle de venta creado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(DetalleVenta dv) {
        try (Connection con = c.conexion()) {
            String sql = "update detalles_ventas set ventas_fk=?, celulares_fk=?, cantidad=?, precio=?, subtotal=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getVentas_fk());
            ps.setInt(2, dv.getCelulares_fk());
            ps.setInt(3, dv.getCantidad());
            ps.setInt(4, dv.getPrecios_fk());
            ps.setDouble(5, dv.getSubtotal());
            ps.setInt(6, dv.getId());
            ps.executeUpdate();
            System.out.println("Detalle de venta actualizado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar el detalle de venta?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    String sql = "delete from detalles_ventas where id=?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setLong(1, id);
                    ps.executeUpdate();
                    System.out.println("Detalle de venta eliminado correctamente! xD");
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

    public List<DetalleVenta> obtenerDetallesVentas() {
        List<DetalleVenta> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_ventas");
            while (rs.next()) {
                lista.add(new DetalleVenta(
                        rs.getInt(1),
                        rs.getInt("ventas_fk"),
                        rs.getInt("celulares_fk"),
                        rs.getInt("cantidad"),
                        rs.getInt("precio"),
                        rs.getDouble("subtotal")
                ));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public DetalleVenta buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_ventas");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new DetalleVenta(
                            rs.getInt(1),
                            rs.getInt("ventas_fk"),
                            rs.getInt("celulares_fk"),
                            rs.getInt("cantidad"),
                            rs.getInt("precio"),
                            rs.getDouble("subtotal")
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<DetalleVenta> listarPorVenta(long idVenta) {
        List<DetalleVenta> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_ventas");
            while (rs.next()) {
                if (rs.getInt("ventas_fk") == idVenta) {
                    lista.add(new DetalleVenta(
                            rs.getInt(1),
                            rs.getInt("ventas_fk"),
                            rs.getInt("celulares_fk"),
                            rs.getInt("cantidad"),
                            rs.getInt("precio"),
                            rs.getDouble("subtotal")
                    ));
                }
            }
        } catch (SQLException e) {
        }
        return lista;
    }
}