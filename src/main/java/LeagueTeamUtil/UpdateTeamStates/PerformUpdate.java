package LeagueTeamUtil.UpdateTeamStates;

import Dao.LeagueTeamDao;
import Dao.Model.LeagueTeam;
import Exceptions.MaxTeamSizeException;

public interface PerformUpdate {
    boolean updateTeamDetails(LeagueTeamDao dao,LeagueTeam oldTeam, long newParameter) throws MaxTeamSizeException;
}
