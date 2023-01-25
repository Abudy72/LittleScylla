package Logic.PlayerVerificationModule;

import Logic.Exceptions.PlayerNotFoundException;

import java.sql.Timestamp;
import java.util.regex.Pattern;

public class VerifiedPlayer{
    private long discordId;
    SmitePlayer p;
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
        p = SmitePlayer.generatePlayerDetails(playerName,Platform.getPortalID(platform));
        this.platform = resolvePlatformType(platform);
        this.currentRank = this.platform.getRank(verificationMMR);
        this.ign = resolvePlayerName();
        this.accountDate = p.getAccountDate();
        setHighestMMR(p);
    }
    private String resolvePlayerName() throws PlayerNotFoundException {
       if(p.getPc_IGN() == null){
           //Check Xbox
           if(p.getConsole_IGN().isEmpty()){
               throw new PlayerNotFoundException("Hidden Profile, unable to verify your account");
           }else{
               return p.getConsole_IGN();
           }
       }else {
           //Check PC
           if(p.getPc_IGN().isEmpty()){
               throw new PlayerNotFoundException("Hidden Profile, unable to verify your account");
           }else{
               return p.getPc_IGN();
           }
       }
    }

    private void setHighestMMR(SmitePlayer smitePlayer){
        System.out.println();
        smitePlayer.getRankedDetails().sort((t1,t2) -> (int) (t1.getHi_rezMMR() - t2.getHi_rezMMR()));
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
        System.out.println(accountDate);
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
}
