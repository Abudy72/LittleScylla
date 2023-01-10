package Dao;

import ConnectionPooling.ConnectionManager;
import SmiteMatchsController.PlayerDataModule.LeaguePlayerData;
import SmiteMatchsController.PlayerDataModule.LeaguePlayerData.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class LeaguePlayerDao implements MainDao<LeaguePlayerData> {
    private final long guildId;
    private final String division;

    public LeaguePlayerDao(long guildId, String division ) {
        this.guildId = guildId;
        this.division = division;
    }

    @Override
    public List<LeaguePlayerData> getAll() {
        String statement = "SELECT * FROM league_player_stats where guild_id = ?";
        LinkedList<LeaguePlayerData> resultList = new LinkedList<>();
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,this.guildId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                PlayerDataBuilder builder = new PlayerDataBuilder(
                        resultSet.getString("ign"),
                        resultSet.getString("division"),
                        resultSet.getString("team"),
                        resultSet.getLong("guild_id"));
                LeaguePlayerData p= builder.setWins(resultSet.getInt("wins"))
                        .setDamageTaken(resultSet.getInt("damage_taken"))
                        .setDamageMitigated(resultSet.getInt("damage_mitigated"))
                        .setPlayerDamage(resultSet.getInt("player_damage"))
                        .setLosses(resultSet.getInt("losses"))
                        .setWins(resultSet.getInt("wins"))
                        .setKills(resultSet.getInt("kills"))
                        .setDeaths(resultSet.getInt("deaths"))
                        .setAssists(resultSet.getInt("assists"))
                        .setGoldEarned(resultSet.getInt("gold_earned"))
                        .setWardsPlaced(resultSet.getInt("wards_placed"))
                        .setTotalMatchDuration(resultSet.getInt("total_match_duration"))
                        .setFbKills(resultSet.getInt("first_blood_kills")).build();
                resultList.add(p);
            }
        }catch (SQLException e){
            e.getMessage();
            e.printStackTrace();
            throw new RuntimeException();
        }
        return resultList;
    }

    @Override
    public boolean save(LeaguePlayerData leaguePlayerData) {
        String updateStatement = "insert into league_player_stats (ign, kills, deaths, assists, wins, losses, player_damage, damage_mitigated, gold_earned, wards_placed, first_blood_kills," +
                "division_name, total_match_duration, damage_taken, team_name,guild_id)" +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) on conflict (ign,division_name) do " +
                "update set (kills, deaths, assists, wins, losses, player_damage, damage_mitigated, gold_earned, wards_placed, first_blood_kills," +
                "total_match_duration, damage_taken) =" +
                "(league_player_stats.kills + ?," +
                "league_player_stats.deaths + ?," +
                "league_player_stats.assists + ?," +
                "league_player_stats.wins + ?," +
                "league_player_stats.losses + ?, " +
                "league_player_stats.player_damage + ?," +
                "league_player_stats.damage_mitigated + ?," +
                "league_player_stats.gold_earned + ?," +
                "league_player_stats.wards_placed + ?," +
                "league_player_stats.first_blood_kills + ?," +
                "league_player_stats.total_match_duration + ?," +
                "league_player_stats.damage_taken + ?)" ;

        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateStatement);
            int j = 1;
            statement.setString(j++, leaguePlayerData.getPlayerName());
            j = AssignJ(leaguePlayerData, statement, j);
            statement.setString(j++, this.division);
            statement.setInt(j++, leaguePlayerData.getTotalMatchDuration());
            statement.setInt(j++, leaguePlayerData.getDamageTaken());
            statement.setString(j++,leaguePlayerData.getTeam());
            statement.setLong(j++,this.guildId);
            //update case.
            j = AssignJ(leaguePlayerData, statement, j);
            statement.setInt(j++, leaguePlayerData.getTotalMatchDuration());
            statement.setInt(j++, leaguePlayerData.getDamageTaken());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private int AssignJ(LeaguePlayerData leaguePlayerData, PreparedStatement statement, int j) throws SQLException {
        statement.setInt(j++, leaguePlayerData.getKills());
        statement.setInt(j++, leaguePlayerData.getDeaths());
        statement.setInt(j++, leaguePlayerData.getAssists());
        statement.setInt(j++, leaguePlayerData.getWins());
        statement.setInt(j++, leaguePlayerData.getLosses());
        statement.setInt(j++, leaguePlayerData.getPlayerDamage());
        statement.setInt(j++, leaguePlayerData.getDamageMitigated());
        statement.setInt(j++, leaguePlayerData.getGoldEarned());
        statement.setInt(j++, leaguePlayerData.getWardsPlaced());
        statement.setInt(j++, leaguePlayerData.getFb_Kills());
        return j;
    }
}
