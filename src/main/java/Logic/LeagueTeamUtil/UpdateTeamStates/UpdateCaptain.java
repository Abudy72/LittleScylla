package Logic.LeagueTeamUtil.UpdateTeamStates;

import Logic.Dao.LeagueTeamDao;
import Logic.Dao.Model.LeagueTeam;

public class UpdateCaptain implements PerformUpdate {

    @Override
    public boolean updateTeamDetails(LeagueTeamDao dao, LeagueTeam oldTeam, long newParameter) {
        oldTeam.setCaptain(newParameter);
        return dao.update(oldTeam);
    }
}
