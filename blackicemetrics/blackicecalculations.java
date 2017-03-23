import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");

    Connection m_Connection = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/black_ice_project?useSSL=false", "root", "pass");

    Statement m_Statement = m_Connection.createStatement();
    String query = "SELECT * FROM raw_data";
		   
    try {
    stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
	DateTime time_local = rs.getDateTime("time_local");
        String neighbourhood = rs.getString("neighbourhood");
        int temp_c = rs.getInt("temp_c");
	int temp_f = rs.getInt("temp_f");
        int feels_c = rs.getInt("feels_c");
	int feels_f = rs.getInt("feels_f");
	int pop = rs.getInt("pop");
        String wind_dir = rs.getString("wind_dir");
        int wind_sp_k = rs.getInt("wind_sp_k");
	int wind_sp_m = rs.getInt("wind_sp_m");
        int wind_gust_k = rs.getInt("wind_gust_k");
	int wind_gust_m = rs.getInt("wind_gust_m" );
	int fxicon = rs.getInt("fxicon");
	int BlackIce = 0;
        System.out.println(time_local + "\t" + neighbourhood +
                           "\t" + temp_c + "\t" + wind_dir +
                           "\t" + fxicon);
    
	statement.executeUpdate("INSERT INTO IsthereBlackIce " + "VALUES (" + time_local + ", " + neighbourhood + ", " + BlackIce + ", )");

}

/*	if Air temp &lt; 5 C (Maintained for 6 hrs at least)

 Wind speed &lt; 36 km/h (Maintained for 6 hrs at least)

 PoP &gt; 70% within last 12 hrs

 Fxicon description: any precipitation type (i.e rain, shower, fog, snow) &lt; heavy rain

 Air temp decrease of 11.1 C past 16.1 C within 12 hours	
	*/
  }
}
