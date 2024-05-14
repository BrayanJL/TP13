package PaquetePrincipal;

import java.sql.*;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Connection;
        
public class Conexion {
    private static Conexion conexion;
    
    private static String url = "jdbc:mariadb://localhost/TP13";
    private static String user = "root";
    private static String pass = "";
            
    private Conexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Clase Conexion: Error al cargar driver");
        }
    }
    
    public static Connection GetConexion () {
        Connection con = null;
        
        if (conexion == null) {
            conexion = new Conexion(); 
        }
        
        try {
            con = (Connection) DriverManager.getConnection(url,user,pass);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Clase Conexion: Error de conexión");
        }
        
        return con;
        
    }
}
