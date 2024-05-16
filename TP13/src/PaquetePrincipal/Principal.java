package PaquetePrincipal;

import java.sql.*;
import javax.swing.JOptionPane;
import org.mariadb.jdbc.Connection;

public class Principal {
    public static void main(String[] args) {
        Connection cn = Conexion.GetConexion();
        String sql;
        
        try {
            
            //3. Insertar 3 alumnos
            
            sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado)"
                    + "VALUES (123456, 'Sosa', 'Carolina', '1990-12-05', 1)";
            
            PreparedStatement ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado)"
                    + "VALUES (234567, 'Villalba', 'Roque', '1993-03-26', 0)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            sql = "INSERT INTO alumno (dni, apellido, nombre, fechaNacimiento, estado)"
                    + "VALUES (345678, 'Cruz', 'Fabio', '2003-07-10', 1)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            //4. Insertar 4 materias
            
            sql = "INSERT INTO materia (nombre, año, estado)"
                    + "VALUES ('Laboratorio de programación 1', 2, 1)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            sql = "INSERT INTO materia (nombre, año, estado)"
                    + "VALUES ('Programación web 1', 2, 1)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            sql = "INSERT INTO materia (nombre, año, estado)"
                    + "VALUES ('Estructuras de datos y algoritmos', 2, 1)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            sql = "INSERT INTO materia (nombre, año, estado)"
                    + "VALUES ('Ingeniería de software', 2, 1)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
            //5. Inscribir a los 3 alumnos en 2 materias cada uno.
            
            sql = "SELECT idAlumno, idMateria FROM alumno, materia "
                    + "WHERE idMateria = (SELECT MIN(idMateria) FROM materia);";
            
            ps = cn.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id_alumno = rs.getInt("idAlumno");
                int id_materia = rs.getInt("idMateria");
                
                sql = "INSERT INTO inscripcion (nota, idAlumno, idMateria)"
                       + "VALUES ( ? , ? , ? )";
                
                ps = cn.prepareStatement(sql);
                
                ps.setInt(1, 8);
                ps.setInt(2, id_alumno);
                ps.setInt(3, id_materia);
                
                ps.executeUpdate();
                
                ps.setInt(1, 9);
                ps.setInt(3, id_materia+1);
                
                ps.executeUpdate();

            }
            
            //6. Listar los datos de los alumnos con calificaciones superiores a 8.
            
            sql = "SELECT dni, apellido, nombre, fechaNacimiento, estado FROM alumno "
                    + "INNER JOIN inscripcion ON alumno.idAlumno = inscripcion.idAlumno "
                    + "WHERE nota > 8";
            
            ps = cn.prepareStatement(sql);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                System.out.println(rs.getInt("dni"));
                System.out.println(rs.getString("apellido"));
                System.out.println(rs.getString("nombre"));
                System.out.println(rs.getDate("fechaNacimiento"));
                System.out.println(rs.getBoolean("estado"));
                System.out.println("");
            }
            
            //7. Desinscribir un alumno de una de la materias.
            
            sql = "DELETE FROM inscripcion "
                    + "WHERE idInscripto = (SELECT MIN(idInscripto) FROM inscripcion)";
            
            ps = cn.prepareStatement(sql);
            
            ps.executeUpdate();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
