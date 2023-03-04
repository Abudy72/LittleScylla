package Logic.SmiteMatchsController.PlayerDataModule;

import com.google.gson.annotations.SerializedName;

public class MatchPlayerData {
    private long discordId;
    private String division;
    private String team;
    private boolean winner;
    private boolean loser;
    private long guild_id;
    @SerializedName("playerName")
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
    @SerializedName("Kills_First_Blood")
    private int fb_Kills;
    @SerializedName("Healing_Player_Self")
    private int selfHeal;
    @SerializedName("Item_Active_1")
    private String active1;
    @SerializedName("Item_Active_2")
    private String active2;
    @SerializedName("Item_Purch_1")
    private String itemPurchase1;
    @SerializedName("Item_Purch_2")
    private String itemPurchase2;
    @SerializedName("Item_Purch_3")
    private String itemPurchase3;
    @SerializedName("Item_Purch_4")
    private String itemPurchase4;
    @SerializedName("Item_Purch_5")
    private String itemPurchase5;
    @SerializedName("Item_Purch_6")
    private String itemPurchase6;
    @SerializedName("Damage_Done_Physical")
    private int damagePhysicalDone;
    @SerializedName("Damage_Done_Magical")
    private int damageMagicalDone;
    @SerializedName("Reference_Name")
    private String godPick;
    @SerializedName("Win_Status")
    private String winStatus;

    //Empty constructor for Gson lib.
    public MatchPlayerData() {
    }

    public void resolvePlayerName(){
       if(playerName != null){
           if(playerName.isEmpty()){
               playerName = godPick;
           }else{
               if(this.playerName.startsWith("[")){
                   this.playerName = playerName.split("\\[.*]")[1];
               }
           }
       }
    }
    public void resolveWinStatus(){
        if(this.winStatus != null){
            if(this.winStatus.equals("Loser")){
                this.winner = false;
                this.loser = true;
            }else {
                this.winner = true;
                this.loser = false;
            }
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

    public boolean getWinner() {
        return winner;
    }

    public boolean getLoser() {
        return loser;
    }

    public long getGuild_id() {
        return guild_id;
    }

    public int getSelfHeal() {
        return selfHeal;
    }

    public String getActive1() {
        return active1;
    }

    public String getActive2() {
        return active2;
    }

    public String getItemPurchase1() {
        return itemPurchase1;
    }

    public String getItemPurchase2() {
        return itemPurchase2;
    }

    public String getItemPurchase3() {
        return itemPurchase3;
    }

    public String getItemPurchase4() {
        return itemPurchase4;
    }

    public String getItemPurchase5() {
        return itemPurchase5;
    }

    public String getItemPurchase6() {
        return itemPurchase6;
    }

    public int getDamagePhysicalDone() {
        return damagePhysicalDone;
    }

    public int getDamageMagicalDone() {
        return damageMagicalDone;
    }

    public void setDiscordId(long discordId) {
        this.discordId = discordId;
    }

    public long getDiscordId() {
        return discordId;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
