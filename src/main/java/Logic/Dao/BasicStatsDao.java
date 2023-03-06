package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Stats.BasicStats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BasicStatsDao{
    private final static Logger logger = LoggerFactory.getLogger(BasicStatsDao.class);
    public List<BasicStats> getStatsById(long id, String division){
        String statement = "SELECT * from league_player_stats where discord_id = ? AND (division_name) = (?)";
        Connection connection = ConnectionManager.getConnection();

        List<BasicStats> result = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,id);
            executeQuery(division, result, preparedStatement);
        }catch (SQLException e){
            logger.error(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return result;
    }

    public List<BasicStats> getStatsByIGN(String ign, String division){
        String statement = "SELECT * from league_player_stats where LOWER(ign) = LOWER(?) AND LOWER(division_name) = LOWER(?)";
        Connection connection = ConnectionManager.getConnection();

        List<BasicStats> result = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,ign);
            executeQuery(division, result, preparedStatement);
        }catch (SQLException e){
            logger.error(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return result;
    }

    private void executeQuery(String division, List<BasicStats> result, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(2,division);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int win = 0;
            int loss = 0;
            if(resultSet.getBoolean("winner")){
                win = 1;
            }else{
                loss=1;
            }
            result.add(
                    new BasicStats(
                            resultSet.getInt("kills"),
                            resultSet.getInt("deaths"),
                            resultSet.getInt("assists"),
                            resultSet.getInt("damage_taken"),
                            resultSet.getInt("damage_mitigated"),
                            resultSet.getInt("player_damage"),
                            win,
                            loss,
                            resultSet.getInt("gold_earned"),
                            resultSet.getInt("wards_placed"),
                            resultSet.getInt("total_match_duration"),
                            resultSet.getInt("first_blood_kills"),
                            resultSet.getInt("self_heal")
                    )
            );
        }
    }
}
