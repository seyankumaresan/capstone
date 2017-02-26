import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by seyankumaresan on 2017-02-23.
 */
public class AddId {

    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/black_ice_project", "root", "");
            Statement stmt = null;

            stmt = conn.createStatement();

            String sql = "ALTER TABLE black_ice_project.raw_data ADD ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT;\n" +
                    "ALTER TABLE black_ice_project.raw_data\n" +
                    "MODIFY COLUMN ID INT NOT NULL AUTO_INCREMENT FIRST;";

            System.out.println(sql);

            stmt.execute(sql);

            System.out.println("Created Table");


        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
