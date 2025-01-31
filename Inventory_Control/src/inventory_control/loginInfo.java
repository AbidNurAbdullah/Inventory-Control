
package inventory_control;

import java.sql.Connection;
import java.sql.DriverManager;


public class loginInfo {
   public static Connection connectDb(){
     
     try
     {
        Class.forName("com.mysql.jdbc.Driver");
         Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "");
         return connect;
     }  
     catch (Exception e) {
            e.printStackTrace();
            return null;
   }
   }
}   

