package Database;
import CustomDataType.SavingObject;

import javax.swing.*;
import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;

public class MySQLConnection {
    private String DATABASE;
    private String URL;
    private String USERNAME;
    private Connection conn;
    public MySQLConnection(){

        this.conn = null;
    }
    public boolean Connect(String database, String username, String password){
        String url= "jdbc:Mysql://localhost:3306/" + database + "?useSSL=false";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            System.out.println(e);
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return false;
        }
        this.DATABASE = database;
        this.URL = url;
        this.USERNAME = username;
        return true;
    }

    public void SaveToDB(JPanel panel, String table, ArrayList<SavingObject> data){
        new Thread(()->{
            if(conn==null){
                ShowPopup(panel, "No connection", "Error", 0);
                return;
            }
            if (!table.matches("[a-zA-Z0-9_]+")) {
                ShowPopup(panel,"Invalid table name", "Error", 0);
                return;
            }
            String sql_query = "DELETE FROM "+table;
            try{
                PreparedStatement stmt = conn.prepareStatement(sql_query);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                String sql_create_table = "CREATE TABLE "+table+" (INTEGERING int, FLOATING bigint);";
                try {
                    PreparedStatement stmt = conn.prepareStatement(sql_create_table);
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException ex) {
                    ShowPopup(panel,"Create table failed", "Error", 0);
                    return;
                }
            }
            sql_query = "INSERT INTO "+table+" (INTEGERING, FLOATING) VALUES (?, ?)";
            try {
                int count=0;
                PreparedStatement sql_stmt = conn.prepareStatement(sql_query);
                for(SavingObject i : data){
                    sql_stmt.setInt(1, i.getFirst());
                    sql_stmt.setLong(2, i.getSecond());
                    sql_stmt.addBatch();
                    count++;
                    if (count == 1000) {
                        count=0;
                        sql_stmt.executeBatch();
                    }
                }
                sql_stmt.executeBatch();
                sql_stmt.close();
                ShowPopup(panel,"Saved to Database", "Success", 1);
            } catch (SQLException e) {
                try{
                    if(conn!=null && !conn.getAutoCommit()){
                        conn.rollback();
                    }
                } catch (SQLException ex) {
                    ShowPopup(panel,"Rollback failed", "Error", 0);
                    return;
                }
                ShowPopup(panel,"Insert table failed", "Error", 0);
            }
        }).start();
    }

    public ArrayList<SavingObject> LoadFromDB(JPanel panel, String table){
        ArrayList<SavingObject> loaded_data = new ArrayList<SavingObject>();
        if(conn==null){
            ShowPopup(panel, "No connection", "Error", 0);
            return loaded_data;
        }
        if (!table.matches("[a-zA-Z0-9_]+")) {
            ShowPopup(panel,"Invalid table name", "Error", 0);
            return loaded_data;
        }
        String sql_query = "SELECT INTEGERING, FLOATING FROM "+table;
        try{
            PreparedStatement stmt = conn.prepareStatement(sql_query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int integering = rs.getInt("INTEGERING");
                long floating = rs.getLong("FLOATING");
                loaded_data.add(new SavingObject(integering, floating));
            }
            rs.close();
            stmt.close();
            ShowPopup(panel, "Loaded to File", "Success", 1);
        } catch (SQLException e) {
            ShowPopup(panel, "Load table failed", "Error", 0);
        }
        return loaded_data;
    }

    public Connection connection(){return conn;}
    public String database(){return this.DATABASE;}
    public String url(){return this.URL;}
    public String username(){return this.USERNAME;}

    void ShowPopup(JPanel panel, String message, String title, int type){
        SwingUtilities.invokeLater(()->{
            JOptionPane.showMessageDialog(panel, message, title, type);
        });
    }
}
