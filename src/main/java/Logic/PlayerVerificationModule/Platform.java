package Logic.PlayerVerificationModule;

import java.util.regex.Pattern;

public abstract class Platform {
    public static String PC_PORTAL_ID = "1";
    public static String XBOX_PORTAL_ID = "10";
    public static String PSN_PORTAL_ID = "9";
    public static String getPortalID(String platform){
        Pattern patternPC = Pattern.compile("PC", Pattern.CASE_INSENSITIVE);
        Pattern patternXbox = Pattern.compile("Xbox", Pattern.CASE_INSENSITIVE);
        Pattern patternPSN = Pattern.compile("PSN", Pattern.CASE_INSENSITIVE);

        if(patternPC.matcher(platform).find()){
            return PC_PORTAL_ID;
        }else if(patternPSN.matcher(platform).find()) {
            return PSN_PORTAL_ID;
        }else if(patternXbox.matcher(platform).find()) {
            return XBOX_PORTAL_ID;
        }else {
            throw new IllegalStateException("Unknown Platform");
        }
    }
    protected enum SupportedPlatforms {
        PC,
        Xbox,
        PSN;
    }
    protected enum Rank {
        Bronze,
        Silver,
        Gold,
        Platinum,
        Diamond,
        Masters,
        GrandMaster,
        Unranked
    }
    abstract SupportedPlatforms getPlatform();

    final Rank getRank(int MMR){
        if(MMR >= 2900){
            return Rank.GrandMaster;
        }else if(MMR >= 2525){
            return Rank.Masters;
        }else if(MMR >= 2000){
            return Rank.Diamond;
        }else if(MMR >= 1725){
            return Rank.Platinum;
        }else if (MMR >= 1350){
            return Rank.Gold;
        }else if (MMR >= 550){
            return Rank.Silver;
        }else if (MMR >= 0){
            return Rank.Bronze;
        }
        return Rank.Unranked;
    }
}
