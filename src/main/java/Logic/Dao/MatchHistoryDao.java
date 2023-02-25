package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Dao.Model.MatchHistoryLog;
import Logic.Exceptions.UnableToSaveMatchException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class MatchHistoryDao implements Dao<MatchHistoryLog>  {
    @Override
    public Optional<MatchHistoryLog> get(long id) {
        String statement = "SELECT * FROM match_history where matchid = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                MatchHistoryLog obj = new MatchHistoryLog(
                        resultSet.getLong("matchid"),
                        resultSet.getLong("saved_by"),
                        resultSet.getDate("date_saved"),
                        resultSet.getTimestamp("publicdate"),
                        resultSet.getString("division"),
                        resultSet.getBoolean("status")
                );
                return Optional.of(obj);
            }
        }catch (SQLException e){
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(MatchHistoryLog matchObject) {
        String statement = "UPDATE match_history " +
                " SET publicdate = ?, status = ?" +
                " WHERE matchid = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement p = connection.prepareStatement(statement);
            p.setTimestamp(1,matchObject.getPublicDate());
            p.setBoolean(2,matchObject.isSaved());
            p.setLong(3, matchObject.getMatchId());
            return p.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchHistoryLog> getAll() {
        String statement = "SELECT * FROM match_history";
        LinkedList<MatchHistoryLog> resultList = new LinkedList<>();
        try{
            Connection connection = ConnectionManager.getConnection();
            ResultSet resultSet = connection.prepareStatement(statement).executeQuery();
            while(resultSet.next()){
                MatchHistoryLog log = new MatchHistoryLog(
                        resultSet.getLong("matchid"),
                        resultSet.getLong("saved_by"),
                        resultSet.getDate("date_saved"),
                        resultSet.getTimestamp("publicdate"),
                        resultSet.getString("division"),
                        resultSet.getBoolean("status")
                );
                resultList.add(log);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public boolean save(MatchHistoryLog matchObject) {
        String statement = "INSERT INTO match_history (matchid,saved_by,publicdate,division,status)" +
                "VALUES (?,?,?,?,?)";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement p = connection.prepareStatement(statement);
            p.setLong(1,matchObject.getMatchId());
            p.setLong(2,matchObject.getSavedBy());
            p.setTimestamp(3,matchObject.getPublicDate());
            p.setString(4,matchObject.getDivision());
            p.setBoolean(5, matchObject.isSaved());
            return p.executeUpdate() == 1;
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new UnableToSaveMatchException("Unable to save match id, please make sure the division name is entered correctly");
        }
    }
}
