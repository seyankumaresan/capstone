import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
  public static void main(String[] args) throws Exception {
    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

    Connection m_Connection = DriverManager.getConnection(
        "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=MyDatabase", "userid", "password");

    Statement m_Statement = m_Connection.createStatement();
    String query = "SELECT * FROM MyTable";

    ResultSet m_ResultSet = m_Statement.executeQuery(query);

    while (m_ResultSet.next()) {
      System.out.println(m_ResultSet.getString(1) + ", " + m_ResultSet.getString(2) + ", "
          + m_ResultSet.getString(3));

    }
  }
}
