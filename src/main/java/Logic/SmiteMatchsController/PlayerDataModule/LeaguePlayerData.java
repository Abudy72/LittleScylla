package Logic.SmiteMatchsController.PlayerDataModule;

import com.google.gson.annotations.SerializedName;

public class LeaguePlayerData {
    private String playerName;
    private String division;
    private String team;
    private int wins;
    private int losses;
    private long guild_id;
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
    @SerializedName("Kills_First_Blood")
    private int fb_Kills;

    //Empty constructor for Gson lib.
    public LeaguePlayerData() {
    }

    // Constructor for LeagueMatch
    public LeaguePlayerData(MatchData matchData,int kills, int deaths, int assists, int playerDamage, int damageMitigated, int damageTaken,
                            int goldEarned, int wardsPlaced, int totalMatchDuration, int fb_Kills, long guild_id) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.playerDamage = playerDamage;
        this.damageMitigated = damageMitigated;
        this.damageTaken = damageTaken;
        this.goldEarned = goldEarned;
        this.wardsPlaced = wardsPlaced;
        this.totalMatchDuration = totalMatchDuration;
        this.fb_Kills = fb_Kills;
        this.guild_id = guild_id;
        resolvePlayerName(matchData);
        resolveWinStatus(matchData);
    }

    // Constructor for Database
    public LeaguePlayerData(String playerName, String division, String team, int wins, int losses, int kills, int deaths,
                            int assists, int playerDamage, int damageMitigated, int damageTaken, int goldEarned, int wardsPlaced,
                            int totalMatchDuration, int fb_Kills, long guild_id) {
        this.playerName = playerName;
        this.division = division;
        this.team = team;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.playerDamage = playerDamage;
        this.damageMitigated = damageMitigated;
        this.damageTaken = damageTaken;
        this.goldEarned = goldEarned;
        this.wardsPlaced = wardsPlaced;
        this.totalMatchDuration = totalMatchDuration;
        this.fb_Kills = fb_Kills;
        this.guild_id = guild_id;
    }
    public LeaguePlayerData(PlayerDataBuilder builder) {
        this.wins = builder.wins;
        this.losses = builder.losses;
        this.kills = builder.kills;
        this.deaths = builder.deaths;
        this.assists = builder.assists;
        this.playerDamage = builder.playerDamage;
        this.damageMitigated = builder.damageMitigated;
        this.damageTaken = builder.damageTaken;
        this.goldEarned = builder.goldEarned;
        this.wardsPlaced = builder.wardsPlaced;
        this.totalMatchDuration = builder.totalMatchDuration;
        this.fb_Kills = builder.fb_Kills;
        this.division = builder.division;
    }

    private void resolvePlayerName(MatchData matchData){
        if(matchData.getPcIGN() == null && matchData.getConsoleIGN() == null){
            playerName = "HiddenProfile";
        }else if(matchData.getConsoleIGN() == null && matchData.getPcIGN() != null){
            playerName = matchData.getPcIGN();
        }else {
            playerName = matchData.getConsoleIGN();
        }
        if(playerName.isEmpty()){
            playerName = "HiddenProfile";
        }
    }
    private void resolveWinStatus(MatchData matchData){
        if(matchData.getWinnerStatus().equals("Loser")){
            wins = 0;
            losses = 1;
        }else {
            wins = 1;
            losses = 0;
        }
    }

    public static class PlayerDataBuilder{
        private String playerName;
        private String division;
        private String team;
        private long guild_id;
        private int wins;
        private int losses;
        private int kills;
        private int deaths;
        private int assists;
        private int playerDamage;
        private int damageMitigated;
        private int damageTaken;
        private int goldEarned;
        private int wardsPlaced;
        private int totalMatchDuration;
        private int fb_Kills;

        public PlayerDataBuilder(String playerName,String division, String team, long guild_id){
            this.playerName = playerName;
            this.division = division;
            this.team = team;
            this.guild_id = guild_id;
        }

        public PlayerDataBuilder setWins(int var1){
            this.wins = var1;
            return this;
        }
        public PlayerDataBuilder setLosses(int var1){
            this.losses = var1;
            return this;
        }
        public PlayerDataBuilder setKills(int var1){
            this.kills = var1;
            return this;
        }
        public PlayerDataBuilder setDeaths(int var1){
            this.deaths = var1;
            return this;
        }
        public PlayerDataBuilder setAssists(int var1){
            this.assists = var1;
            return this;
        }
        public PlayerDataBuilder setPlayerDamage(int var1){
            this.playerDamage = var1;
            return this;
        }
        public PlayerDataBuilder setDamageMitigated(int var1){
            this.damageMitigated = var1;
            return this;
        }
        public PlayerDataBuilder setDamageTaken(int var1){
            this.damageTaken = var1;
            return this;
        }
        public PlayerDataBuilder setGoldEarned(int var1){
            this.goldEarned = var1;
            return this;
        }
        public PlayerDataBuilder setWardsPlaced(int var1){
            this.wardsPlaced = var1;
            return this;
        }
        public PlayerDataBuilder setTotalMatchDuration(int var1){
            this.totalMatchDuration = var1;
            return this;
        }
        public PlayerDataBuilder setFbKills(int var1){
            this.fb_Kills= var1;
            return this;
        }
        public LeaguePlayerData build(){
            return new LeaguePlayerData(this);
        }
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

    public int getFb_Kills() {
        return fb_Kills;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDivision() {
        return division;
    }

    public String getTeam() {
        return team;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
