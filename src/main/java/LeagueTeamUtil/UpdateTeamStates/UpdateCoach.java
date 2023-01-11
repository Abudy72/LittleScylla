package LeagueTeamUtil.UpdateTeamStates;

import Dao.LeagueTeamDao;
import Dao.Model.LeagueTeam;

public class UpdateCoach implements PerformUpdate{
    @Override
    public boolean updateTeamDetails(LeagueTeamDao dao, LeagueTeam oldTeam, long newParameter) {
        oldTeam.setCoach(newParameter);
        return dao.update(oldTeam);
    }
}
