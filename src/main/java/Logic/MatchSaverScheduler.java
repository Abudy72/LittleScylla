package Logic;

import Logic.Dao.DivisionDao;
import Logic.Dao.MatchHistoryDao;
import Logic.Dao.Model.Division;
import Logic.Dao.Model.MatchHistoryLog;
import Logic.Exceptions.DivisionOwnershipException;
import Logic.Exceptions.MatchSavedException;
import Logic.SmiteMatchsController.MatchObject;
import net.dv8tion.jda.api.JDA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static Discord.Interface.CommandsLoader.ServiceStarter.updateBotStatus;

public class MatchSaverScheduler implements Runnable{
    JDA jda;
    public MatchSaverScheduler(JDA jda){
        this.jda = jda;
    }
    private static final Logger logger = LoggerFactory.getLogger(MatchSaverScheduler.class);
    @Override
    public void run() {
        AtomicInteger matchesSaved = new AtomicInteger();
        logger.info("Auto match-save service started.");
        getAllMatches().stream().filter(m -> !m.isSaved()).forEach(match -> {

            if(isMatchSavable(match)){
                MatchObject obj = new MatchObject(match.getMatchId());
                DivisionDao divisionDao = new DivisionDao();
                Optional<Division> division = divisionDao.getAll().stream().filter(div -> div.getDivisionName().equals(match.getDivision())).findAny();
                if(division.isPresent()){
                    Division div = division.get();
                    try {
                        obj.saveMatchToDB(div.getGuild_id(),match.getSavedBy(),match.getDivision());
                        matchesSaved.getAndIncrement();
                        logger.info("Saving match details for id: " + match.getMatchId() + "\tDivision: " + match.getDivision());
                    } catch (MatchSavedException | DivisionOwnershipException e) {
                        logger.error(e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
        updateBotStatus(jda);
        logger.info("Service completed: " + matchesSaved + " matches have been saved.");
    }

    private List<MatchHistoryLog> getAllMatches(){
        MatchHistoryDao dao = new MatchHistoryDao();
        return dao.getAll();
    }

    private boolean isMatchSavable(MatchHistoryLog matchHistoryLog){
        return Timestamp.from(Instant.now().plusSeconds(18000)).after(matchHistoryLog.getPublicDate());
    }
}
