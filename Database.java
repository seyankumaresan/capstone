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

/*
	The purpose of this program is to 
*/

public class Database {

    public static String weather_array[] = new String[361];
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/black_ice_project?useSSL=false";
    static final String USER = "root";
    static final String PASS = "pass";
	static final int NUM_DATA = 24;
	static final int NUM_HOODS = 140;
	
    public static List<WeatherData> getLatestData(String where) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();

            System.out.println(where);
            where = where.replaceAll("'", "''");
            if (where.equals("Wexford-Maryvale"))
            	where = "Dorset Park";
            System.out.println(where);
            String sql = "SELECT * FROM black_ice_project.raw_data WHERE neighbourhood='"+where+"' ORDER BY time_local DESC "+
				"LIMIT " + NUM_DATA;

            ResultSet result = stmt.executeQuery(sql);
			List<WeatherData> list = new ArrayList<WeatherData>();

            while (result.next())
			{
				WeatherData data = new WeatherData(
					result.getInt("ID"),
					//result.getTimestamp("time_local").getTime(),
					result.getString("time_local"),
					result.getString("neighbourhood"),
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
    public static List<WeatherData> SendPrediction(String time, String where, int BlackIce) {
    try {

    	//System.out.println("Database insert: " + time);


        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        where = where.replaceAll("'", "''");
        String sql = "INSERT INTO IsthereBlackIce (time_local, neighbourhood, BlackIce) VALUES('" + time + "', '" + where + "', " + BlackIce + ")";
		

       	stmt.executeUpdate(sql);
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

        public static List<Neighbourhood> getNeighbourhoods() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM black_ice_project.neighbourhoods";

            ResultSet result = stmt.executeQuery(sql);
			List<Neighbourhood> list = new ArrayList<Neighbourhood>();

            while (result.next())
			{
				Neighbourhood data = new Neighbourhood(
					
					result.getString("neighbourhood")
				);
				
				data.neighbourhood = data.neighbourhood.replaceAll("'", "\\'");
				list.add(data);
            }

			if ( list.size() < NUM_HOODS )
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




 public static List<WeatherData> AddWeatherData(String Name) {
    try {

    	//System.out.println("Database insert: " + time);

        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = conn.createStatement();
        Name = Name.replaceAll("'","\\'");
        String sql = "INSERT INTO neighbourhoods (neighbourhood) VALUES('" + Name + "')";
		

       	stmt.executeUpdate(sql);
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


