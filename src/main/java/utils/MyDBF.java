package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDBF {
    public final String URL = "jdbc:mysql://localhost:3306/artyvenci_database";
    public final String USERNAME = "root";
    public final String PWD = "";

    public static MyDBF instance;

    private Connection connection;

    private MyDBF() {

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static MyDBF getInstance(){
        if(instance==null){
            instance = new MyDBF();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
