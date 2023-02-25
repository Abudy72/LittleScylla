package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Exceptions.UnableToSaveMatchException;
import Logic.PlayerVerificationModule.VerifiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class VerifiedPlayerDao implements Dao<VerifiedPlayer> {
    private final long guild_id;
    public VerifiedPlayerDao(long guild_id) {
        this.guild_id = guild_id;
    }

    @Override
    public Optional<VerifiedPlayer> get(long id) {
        String statement = "SELECT * FROM verified_player where discord_id = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                VerifiedPlayer obj = new VerifiedPlayer(
                        resultSet.getLong("discord_id"),
                        resultSet.getString("ign"),
                        resultSet.getString("platform"),
                        resultSet.getString("account_date"),
                        resultSet.getTimestamp("date_verified"),
                        resultSet.getInt("highest_mmr"),
                        resultSet.getInt("total_hours"),
                        resultSet.getString("verified_by")
                );
                return Optional.of(obj);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(VerifiedPlayer verifiedPlayer) {
        String statement = "UPDATE verified_player set (ign,platform,highest_mmr,total_hours) = (?,?,?,?) where discord_id = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,verifiedPlayer.getIgn());
            preparedStatement.setString(2,verifiedPlayer.getPlatform().toString());
            preparedStatement.setInt(3,verifiedPlayer.getVerificationMMR());
            preparedStatement.setInt(4,verifiedPlayer.getHours());
            preparedStatement.setLong(5,verifiedPlayer.getDiscordId());

            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException e){
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException();
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
    }

    @Override
    public List<VerifiedPlayer> getAll() {
        String statement = "SELECT * FROM verified_player";
        LinkedList<VerifiedPlayer> resultList = new LinkedList<>();
        Connection connection = ConnectionManager.getConnection();
        try{
            ResultSet resultSet = connection.prepareStatement(statement).executeQuery();
            while(resultSet.next()){
                VerifiedPlayer verifiedPlayer = new VerifiedPlayer(
                        resultSet.getLong("discord_id"),
                        resultSet.getString("ign"),
                        resultSet.getString("platform"),
                        resultSet.getString("account_date"),
                        resultSet.getTimestamp("date_verified"),
                        resultSet.getInt("highest_mmr"),
                        resultSet.getInt("total_hours"),
                        resultSet.getString("verified_by")
                );
                resultList.add(verifiedPlayer);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return resultList;
    }

    @Override
    public boolean save(VerifiedPlayer verifiedPlayer) {
        String statement = "INSERT INTO verified_player (discord_id, ign, platform, account_date, highest_mmr, total_hours, verified_by)" +
                "VALUES (?,?,?,?,?,?,?)";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,verifiedPlayer.getDiscordId());
            preparedStatement.setString(2,verifiedPlayer.getIgn());
            preparedStatement.setString(3,verifiedPlayer.getPlatform().toString());
            preparedStatement.setString(4,verifiedPlayer.getAccountDateSQL());
            preparedStatement.setInt(5,verifiedPlayer.getVerificationMMR());
            preparedStatement.setInt(6,verifiedPlayer.getHours());
            preparedStatement.setString(7,verifiedPlayer.getVerifiedBy());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException("Database error");
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
    }

    public long getDiscordIdByIGN(String ign){
        String statement = "SELECT discord_id FROM verified_player where ign = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,ign);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getLong("discord_id");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return 0;
    }
}
