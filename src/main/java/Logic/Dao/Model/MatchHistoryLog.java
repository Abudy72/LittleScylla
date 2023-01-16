package Logic.Dao.Model;

import java.sql.Date;

public class MatchHistoryLog {
    private final long matchId;
    private final long savedBy;
    private Date dateSaved = null;
    private Date publicDate = null;
    private final String division;
    private boolean saved;

    public MatchHistoryLog(long matchId, long savedBy, Date dateSaved, Date publicDate, String division, boolean saved) {
        this.matchId = matchId;
        this.savedBy = savedBy;
        this.dateSaved = dateSaved;
        this.publicDate = publicDate;
        this.division = division;
        this.saved = saved;
    }

    public MatchHistoryLog(long matchId, long savedBy, Date publicDate, String division, boolean saved) {
        this.matchId = matchId;
        this.savedBy = savedBy;
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

    public Date getPublicDate() {
        return publicDate;
    }

    public String getDivision() {
        return division;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
