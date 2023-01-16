package Logic.SmiteMatchsController.MatchObjectStates;

import Logic.Dao.MatchHistoryDao;
import Logic.Dao.Model.MatchHistoryLog;
import Logic.Exceptions.MatchUnavailableException;
import Logic.SmiteMatchsController.MatchObject;

import java.sql.Date;

public class MatchUnavailableState implements MatchState{
    private final MatchObject matchObject;

    public MatchUnavailableState(MatchObject matchObject) {
        this.matchObject = matchObject;
    }

    @Override
    public void saveMatchToDB(long guild_id,long matchId, long savedBy, String division) {
        MatchHistoryDao dao = new MatchHistoryDao();
        MatchHistoryLog newLog = new MatchHistoryLog(
                matchObject.getMatchId(),
                savedBy,
                new Date(System.currentTimeMillis()),
                matchObject.getMatchData().getDate(),
                division,
                false);
        dao.save(newLog);
        throw new MatchUnavailableException("Match is still hidden. Match is scheduled to be processed shortly 7 days after" + matchObject.getMatchData().getDate());
    }
}
