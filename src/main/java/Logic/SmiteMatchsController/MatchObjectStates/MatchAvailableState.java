package Logic.SmiteMatchsController.MatchObjectStates;

import Logic.Exceptions.MatchSavedException;
import Logic.SmiteMatchsController.MatchObject;

public class MatchAvailableState implements MatchState{
    private final MatchObject matchObject;

    public MatchAvailableState(MatchObject matchObject) {
        this.matchObject = matchObject;
    }

    @Override
    public void saveMatchToDB(long guild_id,long matchId, long savedBy, String division) throws MatchSavedException {
        //Code to save match details;
        SaveMatchDetailsFacade facade = new SaveMatchDetailsFacade(division);
        facade.saveMatchToDB(guild_id,matchId,savedBy);
    }
}
