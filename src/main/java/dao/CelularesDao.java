package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.entities.Celulares;

public class CelularesDao {

    Conexion c = new Conexion();

    private boolean validarExistenciaForaneaDetalleVenta(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from detalles_ventas where celulares_fk='" + id + "'");
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }

    public void crear(Celulares celular) {
        try (Connection con = c.conexion()) {
            String sql = "insert into celulares (sku, modelo, marcas_fk, stock, precio, sistemas_operativos_fk, gama) "
                    + "values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, celular.getSku());
            ps.setString(2, celular.getModelo());
            ps.setInt(3, celular.getMarcas_fk());
            ps.setInt(4, celular.getStock());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getSistemas_operativos_fk());
            ps.setString(7, celular.getGama());
            ps.executeUpdate();
            System.out.println("Celular creado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void actualizar(Celulares celular) {
        try (Connection con = c.conexion()) {
            String sql = "update celulares set sku=?, modelo=?, marcas_fk=?, stock=?, precio=?, sistemas_operativos_fk=?, gama=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, celular.getSku());
            ps.setString(2, celular.getModelo());
            ps.setInt(3, celular.getMarcas_fk());
            ps.setInt(4, celular.getStock());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getSistemas_operativos_fk());
            ps.setString(7, celular.getGama());
            ps.setInt(8, celular.getId());
            ps.executeUpdate();
            System.out.println("Celular actualizado correctamente! xD");
        } catch (Exception e) {
            System.out.println("Error en el ingreso de datos: " + e.getMessage());
        }
    }

    public void eliminar(long id) {
        try (Connection con = c.conexion()) {
            if (JOptionPane.showConfirmDialog(null, "¿Desea eliminar el celular?", null, JOptionPane.YES_NO_OPTION) == 0) {
                if (buscar(id) != null) {
                    if (validarExistenciaForaneaDetalleVenta(id) == false) {
                        String sql = "delete from celulares where id=?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setLong(1, id);
                        ps.executeUpdate();
                        System.out.println("Celular eliminado correctamente! xD");
                    } else {
                        System.out.println("Este celular no puede ser eliminado, hay un detalle de venta que lo esta referenciando!");
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

    public List<Celulares> obtenerCelulares() {
        List<Celulares> lista = new ArrayList<>();
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from celulares");
            while (rs.next()) {
                lista.add(new Celulares(
                        rs.getInt(1),
                        rs.getString("sku"),
                        rs.getString("modelo"),
                        rs.getInt("marcas_fk"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getInt("sistemas_operativos_fk"),
                        rs.getString("gama")
                ));
            }
        } catch (SQLException e) {
        }
        return lista;
    }

    public Celulares buscar(long id) {
        try (Connection con = c.conexion()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from celulares");
            while (rs.next()) {
                if (rs.getLong(1) == id) {
                    return new Celulares(
                            rs.getInt(1),
                            rs.getString("sku"),
                            rs.getString("modelo"),
                            rs.getInt("marcas_fk"),
                            rs.getInt("stock"),
                            rs.getDouble("precio"),
                            rs.getInt("sistemas_operativos_fk"),
                            rs.getString("gama")
                    );
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
}