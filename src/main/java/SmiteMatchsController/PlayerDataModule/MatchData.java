package SmiteMatchsController.PlayerDataModule;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class MatchData {
    @SerializedName("hz_player_name")
    private String pcIGN;
    @SerializedName("hz_gamer_tag")
    private String consoleIGN;
    @SerializedName("Reference_Name")
    private String godPick;
    @SerializedName("WinnerStatus")
    private String winnerStatus;
    @SerializedName("ret_msg")
    private String publicDate;

    public MatchData(String pcIGN, String consoleIGN, String godPick, String winnerStatus, String publicDate) {
        this.pcIGN = pcIGN;
        this.consoleIGN = consoleIGN;
        this.godPick = godPick;
        this.winnerStatus = winnerStatus;
        this.publicDate = publicDate;
    }

    public String getPcIGN() {
        return pcIGN;
    }

    public String getConsoleIGN() {
        return consoleIGN;
    }

    public String getGodPick() {
        return godPick;
    }

    public String getWinnerStatus() {
        return winnerStatus;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public Date getDate() {
        java.util.Date actualPublicDate = null;
        try {
            actualPublicDate = new SimpleDateFormat(" dd/MM/yyyy").parse(this.getPublicDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Date(actualPublicDate.getTime());
    }
}
