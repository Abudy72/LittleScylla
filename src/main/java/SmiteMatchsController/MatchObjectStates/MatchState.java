package SmiteMatchsController.MatchObjectStates;

import Exceptions.MatchSavedException;
import com.sun.istack.internal.Nullable;

public interface MatchState {
    void saveMatchToDB(long guild_id,long matchId, long savedBy, String division) throws MatchSavedException;
}
