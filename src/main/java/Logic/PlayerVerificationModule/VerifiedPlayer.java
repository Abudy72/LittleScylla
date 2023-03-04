package Logic.PlayerVerificationModule;

import Logic.Exceptions.PlayerNotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.regex.Pattern;

public class VerifiedPlayer{
    private long discordId;
    SmitePlayer smiteAccount;
    private String ign;
    private Platform platform;
    private String accountDate;
    private Timestamp date_verified;
    private int verificationMMR;
    private int hours;
    private String verifiedBy = "System";
    private Platform.Rank currentRank;

    public VerifiedPlayer(long discordId, String ign, String platform, String accountDate, Timestamp date_verified,
                          int verificationMMR, int hours, String verifiedBy) {
        this.discordId = discordId;
        this.ign = ign;
        this.platform = resolvePlatformType(platform);
        this.accountDate = accountDate;
        this.date_verified = date_verified;
        this.verificationMMR = verificationMMR;
        this.hours = hours;
        this.verifiedBy = verifiedBy;
        this.currentRank = this.platform.getRank(verificationMMR);
    }
    public VerifiedPlayer(long discordId, String playerName, String platform) throws PlayerNotFoundException {
        this.discordId = discordId;
        smiteAccount = SmitePlayer.generatePlayerDetails(playerName,Platform.getPortalID(platform));
        this.platform = resolvePlatformType(platform);
        this.ign = resolvePlayerName();
        this.accountDate = smiteAccount.getAccountDate();
        this.hours = smiteAccount.getHoursPlayed();
        setHighestMMR(smiteAccount);
        this.currentRank = this.platform.getRank(verificationMMR);
    }

    public VerifiedPlayer(long discordId, String ign, Platform platform, Platform.Rank rank, String verifiedBy) {
        this.discordId = discordId;
        this.ign = ign;
        this.platform = platform;
        this.currentRank = rank;
        this.accountDate = Timestamp.from(Instant.now()).toString();
        verificationMMR = Platform.getMMR(rank);
        this.verifiedBy = verifiedBy;
    }

    private String resolvePlayerName() throws PlayerNotFoundException {
       if(smiteAccount.getPc_IGN() == null){
           //Check Xbox
           if(smiteAccount.getConsole_IGN().isEmpty()){
               throw new PlayerNotFoundException("Hidden Profile, unable to verify your account");
           }else{
               return smiteAccount.getConsole_IGN();
           }
       }else {
           //Check PC
           if(smiteAccount.getPc_IGN().isEmpty()){
               throw new PlayerNotFoundException("Hidden Profile, unable to verify your account");
           }else{
               return smiteAccount.getPc_IGN();
           }
       }
    }

    private void setHighestMMR(SmitePlayer smitePlayer){
        smitePlayer.getRankedDetails().sort((t2,t1) -> (int) (t1.getHi_rezMMR() - t2.getHi_rezMMR()));
        this.verificationMMR = (int) smitePlayer.getRankedDetails().get(0).getHi_rezMMR();
    }

    private Platform resolvePlatformType(String userInput){
        Pattern patternPC = Pattern.compile("PC", Pattern.CASE_INSENSITIVE);
        Pattern patternXbox = Pattern.compile("Xbox", Pattern.CASE_INSENSITIVE);
        Pattern patternPSN = Pattern.compile("PSN", Pattern.CASE_INSENSITIVE);

        if(patternPC.matcher(userInput).find()){
            return new PlatformPC();
        }else if(patternPSN.matcher(userInput).find()) {
            return new PlatformPSN();
        }else if(patternXbox.matcher(userInput).find()) {
            return new PlatformXbox();
        } else if (userInput.equals("No platform")) {
            return new ManualVerifiedPlatform();
        }
        throw new IllegalStateException("Unknown Platform");
    }


    public long getDiscordId() {
        return discordId;
    }

    public String getIgn() {
        return ign;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getAccountDateSQL() {
        return accountDate;
    }

    public Timestamp getDate_verified() {
        return date_verified;
    }

    public int getVerificationMMR() {
        return verificationMMR;
    }

    public int getHours() {
        return hours;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public Platform.Rank getCurrentRank() {
        return currentRank;
    }

    public SmitePlayer getSmiteAccount() {
        return smiteAccount;
    }

    @Override
    public String toString() {
        return "<@" + this.discordId + ">'s account info:\nAccountDate: `" + this.getAccountDateSQL()
                + "`\nPlatform: " + this.getPlatform()
                + "\n`VerifiedBy: <@" + this.verifiedBy + ">`"
                + "\nMMR Verification: " + this.verificationMMR
                + "\nHours Played: " + this.hours
                + "\nLeague Assistant Rank: " + this.getCurrentRank().toString()
                + "\nIGN: " + this.ign;
    }
}
