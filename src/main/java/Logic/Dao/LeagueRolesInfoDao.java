package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Dao.Model.LeagueRolesInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LeagueRolesInfoDao implements Dao<LeagueRolesInfo> {
    @Override
    public Optional<LeagueRolesInfo> get(long id) {
        String statement = "SELECT * FROM league where guild_id = ?";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement p = connection.prepareStatement(statement);
            p.setLong(1,id);
            ResultSet resultSet = p.executeQuery();

            if(resultSet.next()){
                LeagueRolesInfo leagueRolesInfo = new LeagueRolesInfo();
                leagueRolesInfo.setGuildId(resultSet.getLong("guild_id"));
                leagueRolesInfo.setBronze_uid(resultSet.getLong("bronze_uid"));
                leagueRolesInfo.setSilver_uid(resultSet.getLong("silver_uid"));
                leagueRolesInfo.setGold_uid(resultSet.getLong("gold_uid"));
                leagueRolesInfo.setPlatinum_uid(resultSet.getLong("platinum_uid"));
                leagueRolesInfo.setDiamond_uid(resultSet.getLong("diamond_uid"));
                leagueRolesInfo.setMasters_uid(resultSet.getLong("masters_uid"));
                leagueRolesInfo.setGrandMasters_uid(resultSet.getLong("grandmaster_uid"));

                leagueRolesInfo.setVerificationChannel_uid(resultSet.getLong("verification_Channel"));
                leagueRolesInfo.setStaffRole_uid(resultSet.getLong("staff_role"));
                leagueRolesInfo.setVerifiedRole_uid(resultSet.getLong("verified_role"));
                leagueRolesInfo.setNotVerifiedRole_uid(resultSet.getLong("not_verified_role"));
                leagueRolesInfo.setStats_role(resultSet.getLong("stats_role"));

                leagueRolesInfo.setPsn_uid(resultSet.getLong("psn_uid"));
                leagueRolesInfo.setXbox_uid(resultSet.getLong("xbox_uid"));
                leagueRolesInfo.setPc_uid(resultSet.getLong("pc_uid"));

                return Optional.of(leagueRolesInfo);
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            ConnectionManager.releaseConnection(connection);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(LeagueRolesInfo leagueRolesInfo) {
        return false;
    }

    @Override
    public List<LeagueRolesInfo> getAll() {
        return null;
    }

    @Override
    public boolean save(LeagueRolesInfo leagueRolesInfo) {
        return false;
    }
}
