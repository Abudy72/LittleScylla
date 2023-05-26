package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StarterDao {
    public static int getVerifiedPlayersCount(){
        String statement = "SELECT COUNT(*) as total from verified_player";
        return getStarterInfo(statement);
    }

    public static int getActiveServers(){
        String statement = "select count(distinct(verified_in)) from server_verification";
        return getStarterInfo(statement);
    }

    private static int getStarterInfo(String statement) {
        Connection connection = ConnectionManager.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("total");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return 0;
    }
}
