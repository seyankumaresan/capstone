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


    }

}
