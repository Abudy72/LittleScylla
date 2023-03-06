package Logic.ConnectionPooling;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionManager {
    private static final BasicDataSource dataSource = new BasicDataSource();
    static {
        String username = System.getenv("Database_USERNAME");
        String password = System.getenv("Database_PASSWORD");
        String url = System.getenv("Database_URL");
        //Setting database credentials
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        ArrayList<String> datasourceInit = new ArrayList<String>();
        datasourceInit.add("SET SCHEMA 'little_monster';");
        dataSource.setConnectionInitSqls(datasourceInit);
        //dataSource.setMaxActive(20);
        //dataSource.setL
    }

    public static Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
         throw new RuntimeException(e.getMessage());
        }
    }

    public static void releaseConnection(Connection connection){
        try{connection.close();}catch (SQLException e){System.out.println("unable to close connection");}
    }
    private ConnectionManager(){}
}
