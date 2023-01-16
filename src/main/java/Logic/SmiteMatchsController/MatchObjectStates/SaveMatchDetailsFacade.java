package Logic.SmiteMatchsController.MatchObjectStates;

import Logic.Dao.LeaguePlayerDao;
import Logic.Dao.MatchHistoryDao;
import Logic.Dao.Model.MatchHistoryLog;
import Logic.Exceptions.MatchSavedException;
import Logic.SmiteMatchsController.MatchObject;
import Logic.SmiteMatchsController.PlayerDataModule.LeaguePlayerData;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class SaveMatchDetailsFacade {
    private final String division;

    private void saveMatchIdToDB(MatchObject matchObject, long savedBy) throws MatchSavedException {
        MatchHistoryLog log = isNewMatch(matchObject.getMatchId());
        MatchHistoryDao dao = new MatchHistoryDao();
        if( log != null){
            //Get current match state
            if(log.isSaved()) {
                throw new MatchSavedException(
                        "Match was previously saved by <@" + log.getSavedBy() + ">"
                );
            }else{
                log.setSaved(true);
                log.setPublicDate(null);
                dao.update(log);
            }
        }else {
            MatchHistoryLog newLog = new MatchHistoryLog(
                    matchObject.getMatchId(),
                    savedBy,
                    new Date(System.currentTimeMillis()),
                    null,
                    division,
                    true);
            dao.save(newLog);
        }
    }
    private MatchHistoryLog isNewMatch(long matchId){
        MatchHistoryDao dao = new MatchHistoryDao();
        Optional<MatchHistoryLog> log = dao.get(matchId);
        return log.orElse(null);
    }

    public void saveMatchToDB(long guild_id,long matchId, long savedBy) throws MatchSavedException {
        MatchObject matchObject = new MatchObject(matchId);
        /*
        Saves match id to database, if the match was saved before, then exception is thrown.
        If the match is still hidde, the (saved) value is set to false, otherwise true
         */
        this.saveMatchIdToDB(matchObject,savedBy);
        List<LeaguePlayerData> playerDataList = matchObject.getPlayerDataList();
        LeaguePlayerDao dao = new LeaguePlayerDao(guild_id, division);
        for(LeaguePlayerData data: playerDataList){
            dao.save(data);
        }
    }

    protected SaveMatchDetailsFacade(String division){
        this.division = division;
    }
}
