/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.modelo;
import mvc.dto.Medidas;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import mvc.dto.Medidores;
import org.postgresql.util.PSQLException;

/**
 *
 * @author luisfernandolins
 */
public class AcessoBD {
    
    String driverName = "com.mysql.jdbc.Driver";
    String connectionUrl = "jdbc:postgresql://localhost:5432/";
    String dbName = "tempumidade";
    String userId = "tempumidade";
    String password = "tempumidade";

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    ArrayList<Medidas> listadeMedidas = null;
    ArrayList<Medidores> listadeMedidores = null;
    Medidas medida = new Medidas();
    Medidores medidor = new Medidores();
    
    public ArrayList<Medidas> getMedidas(String medidor, Date datetime, String periodo){
        try{ 
            connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
            statement=connection.createStatement();
            Calendar c = Calendar.getInstance();
            Date newDate = null;
            
            switch(periodo){
                case "year":
                    c.setTime(datetime);
                    c.add(Calendar.YEAR, -1);
                    newDate = c.getTime();
                    break;
                case "month":
                    c.setTime(datetime);
                    c.add(Calendar.MONTH, -1);
                    newDate = c.getTime();
                    break;
                case "week":
                    c.setTime(datetime);
                    c.add(Calendar.WEEK_OF_MONTH, -1);
                    newDate = c.getTime();
                    break;
                case "day":
                    c.setTime(datetime);
                    c.add(Calendar.DAY_OF_MONTH, -1);
                    newDate = c.getTime();
                    break;
                default:
                    c.setTime(datetime);
                    c.add(Calendar.YEAR, -1);
                    newDate = c.getTime();
                    break;
            }
            listadeMedidas = new ArrayList();
            
            
            String sql = "SELECT nome from medidores where tabela='" + medidor + "';";
            try{
            resultSet = statement.executeQuery(sql);
            }
            catch (PSQLException e){
                return listadeMedidas;
            }
            String nomeMedidor = null;
            while(resultSet.next()){
                nomeMedidor = resultSet.getString("nome");
            }
            
            sql ="SELECT * FROM " + medidor + " where CAST(datahora AS Date) <= '" + datetime + "' AND CAST(datahora AS Date) > '" + newDate + "'";
            
            try{
            resultSet = statement.executeQuery(sql);
            }
            catch (PSQLException e){
                return listadeMedidas;
            }
            
            while(resultSet.next()){
                medida = new Medidas();
                medida.setMedidor(nomeMedidor);
                medida.setTemperatura(resultSet.getString("temperatura"));
                medida.setUmidade(resultSet.getString("umidade"));
                medida.setDatahora(resultSet.getString("datahora"));
                medida.setSerial(resultSet.getString("serial"));
                
                listadeMedidas.add(medida);
                
            }
            
            return listadeMedidas;
            
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public ArrayList<Medidores> getMedidor(){
        try{ 
            connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
            statement=connection.createStatement();
            
            
            String sql ="select * from medidores ORDER BY serialno_medidores ASC";

            resultSet = statement.executeQuery(sql);
            
            listadeMedidores = new ArrayList();
            while(resultSet.next()){
                medidor = new Medidores();
                medidor.setNome(resultSet.getString("nome"));
                medidor.setTabela(resultSet.getString("tabela"));
               
                
                listadeMedidores.add(medidor);
                
            }
            
            return listadeMedidores;
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public ArrayList<Medidores> criarMedidor(String nome){
        
        try{ 
            connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
            statement=connection.createStatement();
            String formatted = String.format("%03d", 1);
            
            resultSet = statement.executeQuery("select serialno_medidores from medidores order by serialno_medidores asc");
            while(resultSet.next()){
                if(resultSet.isLast()){
            int sequence_num = resultSet.getInt("serialno_medidores") + 1;
            formatted = String.format("%03d", sequence_num);
                }
            }
            
            String sql ="INSERT INTO medidores (nome, tabela) values ('" + nome + "', 'medidor" + formatted + "');";

            int executeQuery = statement.executeUpdate(sql);
            
            sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'tempumidade' AND table_name = 'medidor" +formatted + "';";
            resultSet = statement.executeQuery(sql);
            
            try{
            String sql2 ="CREATE TABLE medidor" +formatted+" (like medidor001)";
            executeQuery = statement.executeUpdate(sql2);
            }
        
            catch (PSQLException e){
                
            }
            
            
            sql ="select * from medidores ORDER BY serialno_medidores ASC";

            resultSet = statement.executeQuery(sql);
            
            listadeMedidores = new ArrayList();
            while(resultSet.next()){
                medidor = new Medidores();
                medidor.setNome(resultSet.getString("nome"));
                medidor.setTabela(resultSet.getString("tabela"));
               
                
                listadeMedidores.add(medidor);
                
            }
            
            return listadeMedidores;
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public ArrayList<Medidores> editarMedidor(String novoNome,String tabela){
        
        try{ 
            connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
            statement=connection.createStatement();
            
            String sql = "UPDATE medidores SET nome = '" + novoNome +"' WHERE tabela = '" + tabela + "';";

            int rs = statement.executeUpdate(sql);
            
            sql ="select * from medidores ORDER BY serialno_medidores ASC";

            ResultSet rs2 = statement.executeQuery(sql);
            
            listadeMedidores = new ArrayList();
            while(rs2.next()){
                medidor = new Medidores();
                medidor.setNome(rs2.getString("nome"));
                medidor.setTabela(rs2.getString("tabela"));
               
                
                listadeMedidores.add(medidor);
                
            }
            
            return listadeMedidores;
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
    
    public ArrayList<Medidores> excluirMedidor(String tabela){
        
        try{ 
            connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
            statement=connection.createStatement();
            
            String sql = "DELETE from medidores where tabela = '" + tabela +"';";

            int rs = statement.executeUpdate(sql);
            
            sql ="select * from medidores";

            ResultSet rs2 = statement.executeQuery(sql);
            
            listadeMedidores = new ArrayList();
            while(rs2.next()){
                medidor = new Medidores();
                medidor.setNome(rs2.getString("nome"));
                medidor.setTabela(rs2.getString("tabela"));
               
                
                listadeMedidores.add(medidor);
                
            }
            
            return listadeMedidores;
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }

}
