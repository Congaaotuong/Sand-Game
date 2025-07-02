package Sand;

import CustomDataType.SavingObject;
import Database.MySQLConnection;
import GUI.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    static int width=400;
    static int height=400;
    static int length=2;
    static FrameController wnd = new FrameController("Sand", "D:\\Java\\Sand\\Images\\images.jpg", true, width*length, height*length);
    static Loadout loadout = new Loadout();
    static MySQLConnection connection = new MySQLConnection();  

    public static void main(String[] args) {
        Start();

        new Thread(()->{
            while(true){
                Update();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {}
            }
        }).start();
    }
    static void Start(){
        wnd.addScene(loadout.loadout_1("world", width, height, length), 0);
        wnd.addScene(loadout.loadout_HomeGame("main menu",width,height,length), 1);
        wnd.addScene(loadout.loadout_Login("login", width, height, length), 2);
        wnd.addScene(loadout.loadout_Database("database", width, height, length), 3);
        wnd.addScene(loadout.loadout_Setting("Setting", width, height, length, "main menu"), 4);
        wnd.addScene(loadout.loadout_Setting("elenmentsList", width, height, length, "main menu"), 5);
        wnd.addScene(loadout.loadout_Setting("Setting2", width, height, length, "world"), 6);
        wnd.addScene(loadout.loadout_Setting("elenmentsList2", width, height, length, "world"), 7);

        wnd.InitFrame();
        wnd.ChangeScene("main menu");
    }

    static void Update(){
        wnd.update();
    }

    public static void RequestScene(String name){
        wnd.ChangeScene(name);
    }
    public static boolean Connect(String database, String username, String password){
        return connection.Connect(database, username, password);
    }
    public static String connectionInfo(){
        if(connection.connection()==null) return "Database: None";
        String ans = "Database: "+connection.database()+"\nUser: "+connection.username();
        return ans;
    }

    public static void Save(JPanel panel, String table, ArrayList<SavingObject> data){
        connection.SaveToDB(panel,table, data);
    }
    public static ArrayList<SavingObject> Load(JPanel panel, String table){
        return connection.LoadFromDB(panel, table);
    }
}