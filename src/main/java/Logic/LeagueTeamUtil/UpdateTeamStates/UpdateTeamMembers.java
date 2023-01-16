package Logic.LeagueTeamUtil.UpdateTeamStates;

import Logic.Dao.LeagueTeamDao;
import Logic.Dao.Model.LeagueTeam;
import Logic.Exceptions.MaxTeamSizeException;

import java.util.ArrayList;

public class UpdateTeamMembers implements PerformUpdate{
    private final ArrayList<Long> additionalTeamMembers;
    public UpdateTeamMembers(ArrayList<Long> additionalTeamMembers) {
        this.additionalTeamMembers = additionalTeamMembers;
    }

    @Override
    public boolean updateTeamDetails(LeagueTeamDao dao, LeagueTeam oldTeam, long newParameter) throws MaxTeamSizeException {
        if(!isTeamFull(oldTeam)){
            if(additionalTeamMembers.size() + oldTeam.getTeam_Members().size() <= LeagueTeam.MAX_TEAM_SIZE){
                oldTeam.getTeam_Members().addAll(additionalTeamMembers);
                dao.update(oldTeam);
            }
        }
        throw new MaxTeamSizeException("Current team size: " + oldTeam.getTeam_Members().size() + ", unable to add more members, consider making a trade or dropping a player first!");
    }

    private boolean isTeamFull(LeagueTeam oldTeam){
        return oldTeam.getTeam_Members().size() >= LeagueTeam.MAX_TEAM_SIZE;
    }
}
