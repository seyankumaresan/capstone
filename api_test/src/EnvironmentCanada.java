import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by seyankumaresan on 2016-10-19.
 */
public class EnvironmentCanada{
    public static void main(String[] args) throws Exception{
        String url = "http://geo.weather.gc.ca/geomet/?lang=E&service=WMS&request=GetCapabilities";

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
