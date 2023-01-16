package Logic.SmiteMatchsController.MatchObjectStates;

import Logic.Exceptions.MatchSavedException;

public interface MatchState {
    void saveMatchToDB(long guild_id,long matchId, long savedBy, String division) throws MatchSavedException;
}
