package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.SmiteMatchsController.PlayerDataModule.MatchPlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class LeaguePlayerDao implements MiniDao<MatchPlayerData> {
    private final long guildId;
    private final long matchId;

    public LeaguePlayerDao(long guildId, long matchId) {
        this.guildId = guildId;
        this.matchId = matchId;
    }

    @Override
    public List<MatchPlayerData> getAll() {
        String statement = "SELECT * FROM league_player_stats where guild_id = ?";
        LinkedList<MatchPlayerData> resultList = new LinkedList<>();
        /*try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,this.guildId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                MatchPlayerData.PlayerDataBuilder builder = new MatchPlayerData.PlayerDataBuilder(
                        resultSet.getString("ign"),
                        resultSet.getString("division"),
                        resultSet.getString("team"),
                        resultSet.getLong("guild_id"));
                MatchPlayerData p= builder.setWins(resultSet.getInt("wins"))
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
        }*/
        return resultList;
    }

    @Override
    public boolean save(MatchPlayerData matchPlayerData) {
       String insertStatement = "INSERT INTO league_player_stats" +
               "(ign, division_name, team_name, discord_id, damage_taken, damage_mitigated, player_damage, loser, winner, kills, deaths, assists, gold_earned," +
               " wards_placed, total_match_duration, first_blood_kills, self_heal, damage_physical_dealt, damage_magical_dealt, item1, item2, item3, item4, item5, item6, active1, active2, match_id)" +
               "VALUES(?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection connection = ConnectionManager.getConnection();
        try{
           PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
           preparedStatement.setString(1, matchPlayerData.getPlayerName());
           preparedStatement.setString(2, matchPlayerData.getDivision());
           preparedStatement.setString(3, matchPlayerData.getTeam());
           preparedStatement.setLong(4, matchPlayerData.getDiscordId()); //DISCORD ID
           preparedStatement.setInt(5, matchPlayerData.getDamageTaken());
           preparedStatement.setInt(6, matchPlayerData.getDamageMitigated());
           preparedStatement.setInt(7, matchPlayerData.getPlayerDamage());
           preparedStatement.setBoolean(8, matchPlayerData.getLoser());
           preparedStatement.setBoolean(9, matchPlayerData.getWinner());
           preparedStatement.setInt(10, matchPlayerData.getKills());
           preparedStatement.setInt(11, matchPlayerData.getDeaths());
           preparedStatement.setInt(12, matchPlayerData.getAssists());
           preparedStatement.setInt(13, matchPlayerData.getGoldEarned());
           preparedStatement.setInt(14, matchPlayerData.getWardsPlaced());
           preparedStatement.setInt(15, matchPlayerData.getTotalMatchDuration());
           preparedStatement.setInt(16, matchPlayerData.getFb_Kills());
           preparedStatement.setInt(17, matchPlayerData.getSelfHeal());
           preparedStatement.setInt(18, matchPlayerData.getDamagePhysicalDone());
           preparedStatement.setInt(19, matchPlayerData.getDamageMagicalDone());
           preparedStatement.setString(20, matchPlayerData.getItemPurchase1());
           preparedStatement.setString(21, matchPlayerData.getItemPurchase2());
           preparedStatement.setString(22, matchPlayerData.getItemPurchase3());
           preparedStatement.setString(23, matchPlayerData.getItemPurchase4());
           preparedStatement.setString(24, matchPlayerData.getItemPurchase5());
           preparedStatement.setString(25, matchPlayerData.getItemPurchase6());
           preparedStatement.setString(26, matchPlayerData.getActive1());
           preparedStatement.setString(27, matchPlayerData.getActive2());
           preparedStatement.setLong(28,matchId);

           return preparedStatement.executeUpdate() == 1;
       }catch (SQLException e){
           System.out.println(e.getMessage());
           e.printStackTrace();
       }finally {
           ConnectionManager.releaseConnection(connection);
       }
       return false;
    }
}
