package com.database;

import java.sql.*;

public class Database {


    public static Connection connect() {
        //Database LINK
        final String host = "jdbc:postgresql://127.0.0.1:5432/project11";//LocalHost
        final String uname = "";
        final String pass = "";
        Connection mycon = null;
        try {
            mycon = DriverManager.getConnection(host, uname, pass);
            return mycon;
        } catch (SQLException e) {
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


