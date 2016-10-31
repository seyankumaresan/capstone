/**
 * Created by seyankumaresan on 2016-10-19.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.net.ssl.HttpsURLConnection;

public class WeatherNetwork {

    public static void main(String[] args) throws Exception{
        toronto();
        //brampton();
    }
    public static void toronto() throws Exception {
        String url = "http://wx.api.pelmorex.com/weather/HourlyForecasts/CA/ON/m5g2e4?user_key=e24f7a1fbd181484fa86ba0ddecdf284";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        String temp = response.toString();

        for(char c : temp.toCharArray()){
            System.out.print(c);
            if(c == '{' || c == '[' || c == ',')
                System.out.print("\n");
        }


    }

    public static void brampton() throws Exception{
        String url = "http://wx.api.pelmorex.com/weather/HourlyForecasts/CA/ON/Brampton?user_key=e24f7a1fbd181484fa86ba0ddecdf284";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        System.out.println(response.toString());
        in.close();
    }
}


