package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conexion(){
    Connection c= null;
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tecnostore_s2", "root", "1108");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }
}
