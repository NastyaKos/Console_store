package cTest;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class SqlCon {
    public Connection getSqlConnection() throws Exception {
        FileInputStream fis;
        Properties property = new Properties();

        try{
            fis = new FileInputStream("local.properties");
            property.load(fis);
            String url = property.getProperty("url");
            String username = property.getProperty("login");
            String password = property.getProperty("password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);

        }catch(Exception e){
            throw new Exception("Cannot connect to database");
        }
    }
}
