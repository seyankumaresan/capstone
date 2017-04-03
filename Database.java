/**
 * Created by Ishrak
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.io.*;
import WeatherData;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public static String weather_array[] = new String[361];
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/black_ice_project?useSSL=false";
    static final String USER = "root";
    static final String PASS = "pass";

	/*
	 * we need to change this number so that we get 24 hours worth of data. So we are updating
	 * the database on a 6 hour interval, then we need to retrieve 4 pieces of weather data.
	 */
	static final int NUM_DATA = 4;
	
    public static List<WeatherData> getLatestData(String where) {
        try {
            Connection conn = DriverManager.getConnection(DBL_URL, USER, "");
            Statement stmt = null;
            
			stmt = conn.createStatement();
			
            String sql = "SELECT * FROM black_ice_project.raw_data ORDER BY time_local DESC "+
				"LIMIT " + NUM_DATA + " WHERE neigbhourhood='"+where"'";
            ResultSet result = stmt.executeQuery(sql);
            String sqlData = "";
			List<WeatherData> list = new ArrayList<WeatherData>();

            while (result.next())
			{
				WeatherData data = new WeatherData(
					result.getInteger("ID"),
					result.getTimestamp("time_local").getTime(),
					result.getString("neigbhourhood"),
					result.getInteger("temp_c"),
					result.getInteger("wind_sp_k"),
					result.getInteger("pop"),
					result.getString("desc"),
					result.getInteger("fxicon")
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
}


