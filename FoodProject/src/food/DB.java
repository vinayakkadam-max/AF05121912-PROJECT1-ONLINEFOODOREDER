package food;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/food_db",
                "root",
                "root"   // 👉 replace with your MySQL password
            );

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}