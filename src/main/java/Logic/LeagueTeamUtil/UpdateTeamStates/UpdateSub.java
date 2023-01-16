package Logic.LeagueTeamUtil.UpdateTeamStates;

import Logic.Dao.LeagueTeamDao;
import Logic.Dao.Model.LeagueTeam;

public class UpdateSub implements PerformUpdate{
    @Override
    public boolean updateTeamDetails(LeagueTeamDao dao, LeagueTeam oldTeam, long newParameter) {
        oldTeam.setSub(newParameter);
        return dao.update(oldTeam);
    }
}
