package Logic.Dao;

import Logic.ConnectionPooling.ConnectionManager;
import Logic.Dao.Model.Division;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DivisionDao implements MiniDao<Division> {
    @Override
    public List<Division> getAll() {
        String statement = "SELECT * FROM division";
        LinkedList<Division> resultList = new LinkedList<>();
        try{
            Connection connection = ConnectionManager.getConnection();
            ResultSet resultSet = connection.prepareStatement(statement).executeQuery();
            while(resultSet.next()){
                Division server = new Division(
                        resultSet.getString("division_name"),
                        resultSet.getLong("guild_id")
                );
                resultList.add(server);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public boolean save(Division division) {
        String statement = "INSERT INTO division (division_name,guild_id) values (?,?)";
        Connection connection = ConnectionManager.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setLong(2,division.getGuild_id());
            preparedStatement.setString(1,division.getDivisionName());

            return preparedStatement.executeUpdate() == 1;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }
}
