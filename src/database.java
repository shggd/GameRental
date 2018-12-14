import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class database {


    public static Connection connect() {
        //Database LINK
        final String host = "jdbc:postgresql://127.0.0.1:5432/project11";
        final String uname = "postgres";
        final String pass = "5354";
        Connection mycon = null;
        try {
            mycon = DriverManager.getConnection(host, uname, pass);
            return mycon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void close(Connection con, PreparedStatement ps, ResultSet rs){
        try { if (rs != null) rs.close(); } catch (Exception e) {};
        try { if (ps != null) ps.close(); } catch (Exception e) {};
        try { if (con != null) con.close(); } catch (Exception e) {};
    }
}


