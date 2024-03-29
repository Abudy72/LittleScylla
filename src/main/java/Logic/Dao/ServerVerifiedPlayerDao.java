package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Dao.Model.ServerVerifiedPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ServerVerifiedPlayerDao implements Dao<ServerVerifiedPlayer> {
    private final long guildId;

    public ServerVerifiedPlayerDao(long guildId) {
        this.guildId = guildId;
    }

    @Override
    public Optional<ServerVerifiedPlayer> get(long id) {
        String statement = "SELECT * FROM server_verification where verified_in = ? AND discord_id = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,guildId);
            preparedStatement.setLong(2,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return Optional.of(
                        new ServerVerifiedPlayer(resultSet.getLong("discord_id"),resultSet.getLong("verified_in"))
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(ServerVerifiedPlayer serverVerifiedPlayer) {
        return false;
    }

    @Override
    public List<ServerVerifiedPlayer> getAll() {
        return null;
    }

    @Override
    public boolean save(ServerVerifiedPlayer serverVerifiedPlayer) {
        String statement = "INSERT INTO server_verification (discord_id,verified_in) VALUES (?,?)";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,serverVerifiedPlayer.getDiscordId());
            preparedStatement.setLong(2,serverVerifiedPlayer.getVerifiedInGuild());
            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
    }

    public boolean delete(long memberId){
        String statement = "DELETE from server_verification where discord_id = ? AND verified_in = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,memberId);
            preparedStatement.setLong(2,this.guildId);
            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
    }
}
