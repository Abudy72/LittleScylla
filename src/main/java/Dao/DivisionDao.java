package Dao;

import ConnectionPooling.ConnectionManager;
import Dao.Model.Division;
import Dao.Model.MatchHistoryLog;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DivisionDao implements MainDao<Division> {
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
        return false;
    }
}
