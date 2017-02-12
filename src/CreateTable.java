import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable{
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/black_ice_project", "root", "");
            Statement stmt = null;

            stmt = conn.createStatement();

            String sql = "CREATE DATABASE black_ice_project";

            System.out.println(sql);

            stmt.execute(sql);

            System.out.println("Created Database");

            sql = "CREATE TABLE raw_data "+
                    "(time_gmt DATETIME, " +
                    "time_local DATETIME, " +
                    "neighbourhood TEXT, " +
                    "temp_c INT(11), " +
                    "temp_f INT(11), " +
                    "feels_c INT (11), " +
                    "fells_f INT (11), " +
                    "pop INT(11), " +
                    "wind_dir TEXT, " +
                    "wind_sp_k INT(11), " +
                    "wind_sp_m INT(11), " +
                    "wind_gust_k INT(11), " +
                    "wind_gust_m INT(11), " +
                    "fxicon INT(11), " +
                    "desc TEXT)";

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