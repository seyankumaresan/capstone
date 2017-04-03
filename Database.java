/**
 * Created by Ishrak Khan
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static String weather_array[] = new String[361];
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/black_ice_project?useSSL=false";
    static final String USER = "root";
    static final String PASS = "pass";
	static final int NUM_DATA = 24;
	
    public static List<WeatherData> getLatestData(String where) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, "");
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM black_ice_project.raw_data ORDER BY time_local DESC "+
				"LIMIT " + NUM_DATA + " WHERE neigbhourhood='"+where+"'";

            ResultSet result = stmt.executeQuery(sql);
			List<WeatherData> list = new ArrayList<WeatherData>();

            while (result.next())
			{
				WeatherData data = new WeatherData(
					result.getInt("ID"),
					result.getTimestamp("time_local").getTime(),
					result.getString("neigbhourhood"),
					result.getInt("temp_c"),
					result.getInt("wind_sp_k"),
					result.getInt("pop"),
					result.getString("desc"),
					result.getInt("fxicon")
				);
				
				list.add(data);
            }

			if ( list.size() < NUM_DATA )
			{
				// this is an error, because we don't have enough historic data to make a 
				// proper prediction here
				return null;
			}
			
			return list;	
        }
		catch (SQLException se)
		{
            se.printStackTrace();
        }
		catch (Exception e)
		{
            e.printStackTrace();
        }
        
		return null;
    }
    public static List<WeatherData> SendPrediction(String where, int BlackIce) {
    try {
        Connection conn = DriverManager.getConnection(DB_URL, USER, "");
        Statement stmt = conn.createStatement();
        String sql = "INSERT INTO IsthereBlackIce VALUES(" + System.currentTimeMillis() + ", " + where + ", " + BlackIce + ",)";
		

        ResultSet result = stmt.executeQuery(sql);
    	}
	    catch (SQLException se)
		{
	        se.printStackTrace();
	    }
		catch (Exception e)
		{
	        e.printStackTrace();
	    }
	    
	return null;
    
    }
}


