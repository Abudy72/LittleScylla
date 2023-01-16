package Logic.ConnectionPooling;

import org.apache.commons.dbcp.BasicDataSource;

import java.net.URI;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionManager {
    private static final BasicDataSource dataSource = new BasicDataSource();
    static {
        String username = null, password = null,dbUrl = null;
        /*URI dbUri;
        try{
            dbUri = new URI(System.getenv("DATABASE_URL"));
            username = dbUri.getUserInfo().split(":")[0]; //jdbc:postgresql://localhost:5432/postgres
            password = dbUri.getUserInfo().split(":")[1];
            dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }*/

        //Setting database credentials
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        ArrayList<String> datasourceInit = new ArrayList<String>();
        datasourceInit.add("SET SCHEMA 'LittleMonster';");
        dataSource.setConnectionInitSqls(datasourceInit);
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxActive(20);
    }

    public static Connection getConnection(){
        try{
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
         throw new RuntimeException(e.getMessage());
        }
    }
    private ConnectionManager(){}
}
