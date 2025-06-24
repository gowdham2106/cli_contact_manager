package cli_contact_manager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ContactDatabase {
     static String URL = "jdbc:mysql://localhost:3306/contact_manager"; 
     static String USER = "root";           
     static String PASSWORD = "root";
     public static Connection connect() throws SQLException {
         return DriverManager.getConnection(URL, USER, PASSWORD);
     }
}
