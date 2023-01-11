package Dao;

import ConnectionPooling.ConnectionManager;
import Dao.Model.LeagueTeam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LeagueTeamDao implements MainDao<LeagueTeam>, Dao<LeagueTeam> {
    private final String division;

    public LeagueTeamDao(String division) {
        this.division = division;
    }

    @Override
    public List<LeagueTeam> getAll() {
        String statement = "SELECT * FROM league_team";
        LinkedList<LeagueTeam> resultList = new LinkedList<>();
        try{
            Connection connection = ConnectionManager.getConnection();
            ResultSet resultSet = connection.prepareStatement(statement).executeQuery();
            while(resultSet.next()){
                LeagueTeam team = extractTeamMembers(resultSet);
                resultList.add(team);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public boolean save(LeagueTeam leagueTeam) {
        String statement = "INSERT INTO league_team (team_name, captain, team_member1,team_member2,team_member3" +
                "team_member4, coach, sub, division_name) values (?,?,?,?,?,?,?,?)";

        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1,leagueTeam.getTeam_name());
            preparedStatement.setLong(2,leagueTeam.getCaptain());
            preparedStatement.setLong(3,leagueTeam.getTeam_Members().get(0));
            preparedStatement.setLong(4,leagueTeam.getTeam_Members().get(1));
            preparedStatement.setLong(5,leagueTeam.getTeam_Members().get(2));
            preparedStatement.setLong(6,leagueTeam.getTeam_Members().get(3));
            preparedStatement.setLong(7,leagueTeam.getCoach());
            preparedStatement.setLong(8,leagueTeam.getSub());
            preparedStatement.setString(7,leagueTeam.getDivision());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<LeagueTeam> get(long id) {
        String statement = "SELECT * FROM league_team where captain = ? AND division = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                LeagueTeam team = extractTeamMembers(resultSet);
                return Optional.of(team);
            }
            return Optional.empty();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    private LeagueTeam extractTeamMembers(ResultSet resultSet) throws SQLException {
        LeagueTeam team = new LeagueTeam(
                resultSet.getString("team_name"),
                resultSet.getLong("captain"),
                resultSet.getString("division_name")
        );
        team.addMember(resultSet.getLong("team_member1"));
        team.addMember(resultSet.getLong("team_member2"));
        team.addMember(resultSet.getLong("team_member3"));
        team.addMember(resultSet.getLong("team_member4"));

        team.setSub(resultSet.getLong("sub"));
        team.setCoach(resultSet.getLong("coach"));

        return team;
    }

    @Override
    public boolean update(LeagueTeam leagueTeam) {
        String statement = "UPDATE league_team set (captain, team_member1,team_member2,team_member3" +
                "team_member4, coach, sub, division_name) = (?,?,?,?,?,?,?,?) where division_name = ? AND team_name = ?";
        try{
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(1,leagueTeam.getCaptain());
            preparedStatement.setLong(2,leagueTeam.getTeam_Members().get(0));
            preparedStatement.setLong(3,leagueTeam.getTeam_Members().get(1));
            preparedStatement.setLong(4,leagueTeam.getTeam_Members().get(2));
            preparedStatement.setLong(5,leagueTeam.getTeam_Members().get(3));
            preparedStatement.setLong(6,leagueTeam.getCoach());
            preparedStatement.setLong(7,leagueTeam.getSub());
            preparedStatement.setString(8,leagueTeam.getDivision());

            preparedStatement.setString(9,leagueTeam.getDivision());
            preparedStatement.setString(10,leagueTeam.getTeam_name());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
