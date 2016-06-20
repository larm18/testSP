/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsp;

import java.sql.CallableStatement;
import java.sql.ResultSet;

/**
 *
 * @author Deser
 */
public class TestSP extends Conexion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String nombre = "Nombre de Prueba";
        int edad = 18;
        
        //IngresarPersona(nombre, edad);
        //ActualizarPersona(21, nombre, edad);
        //EliminarPersona(21);
        MostrarPersona();
        //BuscarPersonaNombre(nombre);
    }
    
    /*
     * NOTAS
     * boolean execute()
     *      Este metodo regresa un valor booleano
     *      TRUE: Si el resultado es un ResultSet (SELECT)
     *      FALSE: Si el resultado es un entero indicando el numero de filas afectadas
     * ResultSet executeQuery()
     *      Se usa generalmente para leer datos de la DB (SELECT)
     * int executeUpdate()
     *      Generalmente se utiliza para modificar tablas en la base de datos (INSERT, DELETE, UPDATE)
     */
    
    static boolean res;
    
    public static void IngresarPersona(String nombre, int edad)
    {
        try
        {
            CallableStatement statement = Conexion().prepareCall("{call sp_RegistrarPersona(?,?)}");
         
            /*
            statement.setString("nombre", nombre);
            statement.setInt("edad", edad);
            */
            statement.setString(1, nombre);
            statement.setInt(2, edad);
                    
            statement.executeUpdate();
            
            System.out.println("Persona Registrada Exitosamente");
        }
        
        catch(Exception e)
        {
            System.out.println("Se ha presentado un error " + e.getMessage());
        }
    }
    
    public static void ActualizarPersona(int id, String nombre, int edad)
    {
        int FilasAfectadas = 0;
        
        try
        {
            CallableStatement statement =  Conexion().prepareCall("{call sp_ActualizarPersona(?,?,?)}");
            
            statement.setInt(1, id);
            statement.setString(2, nombre);
            statement.setInt(3, edad);
            
            /*
                Regrera un entero indicando
                (1) Si se realizo un cambio en la base de datos
                (0) Si no se modifico la base de datos
            */
            
            FilasAfectadas = statement.executeUpdate();
            System.out.println(FilasAfectadas);
        }
        
        catch (Exception e)
        {
           System.out.println("Se ha presentado un error " + e.getMessage());
        }
        
        finally
        {
            if( FilasAfectadas == 1)
            {
                System.out.println("Actualizacion Correcta");                        
            }
            else
            {
                System.out.println("No se modificaron los registros");
            }
        }    
    }
    
    public static void EliminarPersona(int ID)
    {
        try
        {
            CallableStatement statement = Conexion().prepareCall("{call sp_EliminarPersona(?)}");
           
            statement.setInt(1, ID);
                    
            System.out.println(statement.executeUpdate());
            
            System.out.println("Registro eliminado correctamente ");
        }
        
        catch(Exception e)
        {
            System.out.println("Se ha presentado un error " + e.getMessage());
        }
    }
    
    public static void MostrarPersona()
    {
        try
        {
            CallableStatement statement = Conexion().prepareCall("{call sp_MostrarPersona()}");
            
            res = statement.execute();
            System.out.println(res);
            
            // Este metodo pone un puntor en la primera fila y con el metodo next, se mueve a la siguiente fila
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next())
            {
                System.out.println(resultSet.getInt("ID"));
                System.out.println(resultSet.getString("Nombre"));
                System.out.println(resultSet.getInt("Edad"));
                System.out.println();
            }
        }
        
        catch (Exception e)
        {
            System.out.println("Se ha presentado un error " + e.getMessage());
        }
    }
    
    // Ejemplo para regresar un solo Dato
    public static void BuscarPersonaNombre(String nombre) 
    {
        try
        {
            CallableStatement statement = Conexion().prepareCall("{call sp_BuscarPorNombre(?)}");
            
            statement.setString(1, nombre);
            
            res = statement.execute();
            System.out.println(res);
            
            ResultSet resultSet = statement.executeQuery();
            
            int numeroColumnas = resultSet.getMetaData().getColumnCount();
            for (int i=1; i<=numeroColumnas; i++)
            {
                System.out.println(resultSet.getMetaData().getColumnName(i));
            }
            
            // Esperando que solo exista 1 mismo nombre
            if(resultSet.next())
            {
                System.out.println(resultSet.getInt("ID"));
                System.out.println(resultSet.getString("Nombre"));
                System.out.println(resultSet.getInt("Edad"));    
            }
            
        }
        
        catch (Exception e)
        {
            System.out.println("Se ha presentado un error " + e.getMessage());
        }
    }
}
