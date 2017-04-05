import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BlackIceMetrics{
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/black_ice_project?useSSL=false", "root", "pass");
            Statement stmt = null;

            stmt = conn.createStatement();

            String sql = "CREATE TABLE IsthereBlackIce "+
                    "(time_local DATETIME, " +
                    "neighbourhood TEXT, " +
                    "BlackIce INT(11));";

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
