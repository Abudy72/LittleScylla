package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Dao.Model.LeagueIDs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LeagueIDsDao implements Dao<LeagueIDs> {
    @Override
    public Optional<LeagueIDs> get(long id) {
        String statement = "SELECT * FROM League where guild_id =";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement p = connection.prepareStatement(statement);
            p.setLong(1,id);
            ResultSet resultSet = p.executeQuery();

            if(resultSet.next()){
                LeagueIDs leagueIDs = new LeagueIDs();
                leagueIDs.setGuildId(resultSet.getLong("guild_id"));
                leagueIDs.setBronze_uid(resultSet.getLong("bronze_uid"));
                leagueIDs.setSilver_uid(resultSet.getLong("silver_uid"));
                leagueIDs.setGold_uid(resultSet.getLong("gold_uid"));
                leagueIDs.setPlatinum_uid(resultSet.getLong("platinum_uid"));
                leagueIDs.setDiamond_uid(resultSet.getLong("diamond_uid"));
                leagueIDs.setMasters_uid(resultSet.getLong("masters_uid"));
                leagueIDs.setGrandMasters_uid(resultSet.getLong("grandmaster_uid"));

                leagueIDs.setVerificationChannel_uid(resultSet.getLong("verification_Channel"));
                leagueIDs.setStaffRole_uid(resultSet.getLong("staff_role"));
                leagueIDs.setVerifiedRole_uid(resultSet.getLong("verified_role"));
                leagueIDs.setNotVerifiedRole_uid(resultSet.getLong("not_verified_role"));

                leagueIDs.setPsn_uid(resultSet.getLong("psn_uid"));
                leagueIDs.setXbox_uid(resultSet.getLong("xbox_uid"));
                leagueIDs.setPc_uid(resultSet.getLong("pc_uid"));

                return Optional.of(leagueIDs);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(LeagueIDs leagueIDs) {
        return false;
    }

    @Override
    public List<LeagueIDs> getAll() {
        return null;
    }

    @Override
    public boolean save(LeagueIDs leagueIDs) {
        return false;
    }
}
