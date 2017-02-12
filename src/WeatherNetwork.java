/**
 * Created by seyankumaresan on 2016-10-19.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.io.*;

public class WeatherNetwork {

    public static String weather_array[] = new String[361];
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/black_ice_project";
    static final String USER = "root";
    static final String PASS = "";


    public static void main(String[] args) throws Exception{
        String neighbourhoods[] = new String[140];
        readFile(neighbourhoods);

        for(int i = 0; i < 140; i++) {
            getTorontoWeather(neighbourhoods[i]);
            insert_into_db(neighbourhoods[i]);
        }
        //print_buffer();
    }

    public static void getTorontoWeather(String location) throws Exception {

        /*
            This code is Written by Seyan Kumaresan, based on the tutorial available on Mykong.com [4]
         */
        String location_modified = removeSpaces(location);

        String requestUrl = "http://wx.api.pelmorex.com/weather/HourlyForecasts/CA/ON/" + location_modified + "?user_key=e24f7a1fbd181484fa86ba0ddecdf284";

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        BufferedReader inReader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = inReader.readLine()) != null) {
            response.append(inputLine);
        }

        String temp = response.toString();

        extract(temp);
    }

    public static void extract(String temp){

        /*
            This is original code, written by Seyan Kumaresan
         */

        String buffer = "";
        int flag = 0;
        char c_prev = 'S';
        int index = 0;

        for(char c : temp.toCharArray()){
            if(c == '"' && c_prev == ':'){

               weather_array[index] = buffer;
               index += 1;
               buffer = "";
               flag = 1;


                if(index == 361) {
                    break;
                }
            } else {
                if(flag == 1){
                    if(c == ',') {
                        flag = 0;
                    }else if (c != '"' && c != '}' && c != ']') {
                        buffer += c;
                    }
                }
            }

            c_prev = c;
        }

    }


    public static void print_buffer(){
        for(int i = 0; i < 361; i += 1){
            System.out.println(weather_array[i]);
        }
    }

    public static void insert_into_db(String location_old){

        /*
            This is original code, written by Seyan Kumaresan
         */

        String location = removeApostrophe(location_old);

        try{
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/black_ice_project", "root", "");
            Statement stmt = null;

            stmt = conn.createStatement();

            int i = 0;

            for(i = 0; i < 360; i+= 15){


                String sql = "INSERT INTO raw_data VALUES ('" + weather_array[2 + i] + "', '" +
                        weather_array[3 + i] + "', '" + location + "', " + Integer.parseInt(weather_array[4 + i]) +
                        ", " + Integer.parseInt(weather_array[5 + i]) +
                        ", " + Integer.parseInt(weather_array[6 + i]) +
                        ", " + Integer.parseInt(weather_array[7 + i]) +
                        ", " + Integer.parseInt(weather_array[8 + i]) +
                        ", '" + weather_array[9 + i] + "', " + Integer.parseInt(weather_array[10 + i]) +
                        ", " + Integer.parseInt(weather_array[11 + i]) +
                        ", " + Integer.parseInt(weather_array[12 + i]) +
                        ", " + Integer.parseInt(weather_array[13 + i]) +
                        ", " + Integer.parseInt(weather_array[14 + i]) +
                        ", '" + weather_array[15 + i] + "')";

                System.out.println(sql);

                stmt.execute(sql);

            }


        }catch (SQLException se){
            se.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String removeSpaces(String location){
        char c;
        String modified = "";
        for(int i = 0; i < location.length(); i++){
            c = location.charAt(i);

            if(c == ' '){
                modified = modified.concat(Character.toString('_'));
            } else {
                modified = modified.concat(Character.toString(c));
            }
        }

        return modified;
    }

    public static void readFile(String[] neighbourhoods){
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader("neighbourhoods.txt"));

            String line = br.readLine();

            while (line != null) {
                neighbourhoods[i] = line.toString();
                line = br.readLine();
                i++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String removeApostrophe(String location){
        char c;
        String modified = "";
        for(int i = 0; i < location.length(); i++){
            c = location.charAt(i);

            if(c == '\''){
                modified = modified.concat(Character.toString(c));
                modified = modified.concat(Character.toString(c));
            } else {
                modified = modified.concat(Character.toString(c));
            }
        }

        return modified;
    }
}


