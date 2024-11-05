package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {

    public final String URL = "jdbc:mysql://localhost:3306/artyvenci_database";
    public final String USERNAME = "root";
    public  final String PWD = "";
    public static MyDB instance;
    private Connection connection;


    private MyDB() {

        try {
            connection = DriverManager.getConnection(URL,USERNAME,PWD);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
    }

    public static MyDB getInstance(){
        if(instance==null){
            instance = new MyDB();
        }
        return instance;

    }

    public Connection getConnection() {
        return connection;
    }
}
