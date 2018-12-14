import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import org.apache.commons.dbutils.*;


//Initial database creation
public class generateDatabase {

    private Statement statement;
    final private String[] queries = {
            "CREATE TABLE LOGS( login VARCHAR(12) PRIMARY KEY , pass VARCHAR(12))",
            "CREATE TABLE GAME( gameid VARCHAR(12) PRIMARY KEY,title VARCHAR(50), publisher VARCHAR(30),available VARCHAR(3))",
            "CREATE TABLE MEMBERS(memberid VARCHAR(12) PRIMARY KEY, firstname VARCHAR(30), lastname VARCHAR(30))",
            "CREATE TABLE BORROW(gameid INT PRIMARY KEY , memberid INT, due_date DATE, borrow_date DATE, return_date DATE)"
    };

    public generateDatabase() {

    }

    public void create(){
        //open connection
        Connection con = database.connect();
        try{
            statement = con.createStatement();
            for(String s:queries){
                statement.execute(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(con);
        }
    }
}
