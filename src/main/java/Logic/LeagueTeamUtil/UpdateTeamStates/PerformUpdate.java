package Logic.LeagueTeamUtil.UpdateTeamStates;

import Logic.Dao.LeagueTeamDao;
import Logic.Dao.Model.LeagueTeam;
import Logic.Exceptions.MaxTeamSizeException;

public interface PerformUpdate {
    boolean updateTeamDetails(LeagueTeamDao dao, LeagueTeam oldTeam, long newParameter) throws MaxTeamSizeException;
}
