package SmiteMatchsController;

import com.google.gson.annotations.SerializedName;

public class PlayerData {
    private String playerName;
    @SerializedName("Kills_Player")
    private int kills;
    @SerializedName("Deaths")
    private int deaths;
    @SerializedName("Assists")
    private int assists;
    @SerializedName("Damage_Player")
    private int playerDamage;
    @SerializedName("Damage_Mitigated")
    private int damageMitigated;
    @SerializedName("Damage_Taken")
    private int damageTaken;
    @SerializedName("Gold_Earned")
    private int goldEarned;
    @SerializedName("Wards_Placed")
    private int wardsPlaced;
    @SerializedName("Match_Duration")
    private int totalMatchDuration;
    @SerializedName("hz_player_name")
    private String pcIGN;
    @SerializedName("hz_gamer_tag")
    private String consoleIGN;
    @SerializedName("Kills_First_Blood")
    private int fb_Kills;
    @SerializedName("Reference_Name")
    private String godPick;
    @SerializedName("WinnerStatus")
    private String winnerStatus;

    @SerializedName("ret_msg")
    private String publicDate;


    public String getPlayerName() {
        return playerName;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public int getDamageMitigated() {
        return damageMitigated;
    }

    public int getDamageTaken() {
        return damageTaken;
    }

    public int getGoldEarned() {
        return goldEarned;
    }

    public int getWardsPlaced() {
        return wardsPlaced;
    }

    public int getTotalMatchDuration() {
        return totalMatchDuration;
    }

    public String getPcIGN() {
        return pcIGN;
    }

    public String getConsoleIGN() {
        return consoleIGN;
    }

    public int getFb_Kills() {
        return fb_Kills;
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
}
