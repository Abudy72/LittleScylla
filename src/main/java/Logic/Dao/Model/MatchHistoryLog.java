package Logic.Dao.Model;

import java.sql.Date;
import java.sql.Timestamp;

public class MatchHistoryLog {
    private final long matchId;
    private final long savedBy;
    private Date dateSaved = null;
    private Timestamp publicDate = null;
    private final String division;
    private boolean saved;

    public MatchHistoryLog(long matchId, long savedBy, Date dateSaved, Timestamp publicDate, String division, boolean saved) {
        this.matchId = matchId;
        this.savedBy = savedBy;
        this.dateSaved = dateSaved;
        this.publicDate = publicDate;
        this.division = division;
        this.saved = saved;
    }

    public MatchHistoryLog(long matchId, long savedBy, String division, boolean saved) {
        this.matchId = matchId;
        this.savedBy = savedBy;
        this.division = division;
        this.saved = saved;
    }

    public long getMatchId() {
        return matchId;
    }

    public long getSavedBy() {
        return savedBy;
    }

    public Date getDateSaved() {
        return dateSaved;
    }

    public Timestamp getPublicDate() {
        return publicDate;
    }

    public String getDivision() {
        return division;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setPublicDate(Timestamp publicDate) {
        this.publicDate = publicDate;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
