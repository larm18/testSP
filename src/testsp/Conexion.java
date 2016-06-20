/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsp;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Deser
 */
public abstract class Conexion {
    
    protected static Connection Conexion()
    {
        Connection conexion=null;
        
        try
        {   
            Class.forName("com.mysql.jdbc.Driver");
            String servidor="jdbc:mysql://localhost/pruebaconexion";
            String usuario="root";
            String contrasenia="";
            conexion=(Connection)DriverManager.getConnection(servidor, usuario, contrasenia);
            //JOptionPane.showMessageDialog(null, "Conexion Exiosa");
        }
        catch(ClassNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        finally
        {
            return conexion;
        }
    }
}
