package Database;
import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/sandgame?useSSl=false";
    private static final String USERNAME ="root";
    private static final String PASSWORD ="thanhthcsldp1";
    public static Connection getConnection()  throws SQLException {
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);

}
}
