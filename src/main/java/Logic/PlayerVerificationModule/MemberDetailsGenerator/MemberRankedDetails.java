package Logic.PlayerVerificationModule.MemberDetailsGenerator;

import com.google.gson.annotations.SerializedName;

public class MemberRankedDetails {
    @SerializedName("Name")
    private String platForm;
    @SerializedName("Rank_Stat")
    private double hi_rezMMR;
    @SerializedName("Tier")
    private int tier;

    protected MemberRankedDetails(String platForm, double hi_rezMMR, int tier) {
        this.platForm = platForm;
        this.hi_rezMMR = hi_rezMMR;
        this.tier = tier;
    }

    public String getPlatForm() {
        return platForm;
    }

    public double getHi_rezMMR() {
        return hi_rezMMR;
    }

    public int getTier() {
        return tier;
    }

    public String getActualRank(){
        double doubleTier = tier;
        if(doubleTier / 5 <= 0 && doubleTier <= 5){
            return "Bronze " + ((5 % doubleTier) + 1);
        }else if(doubleTier / 10 <= 0 && doubleTier <= 10) {
            return "Silver " + ((10 % doubleTier) + 1);
        }else if(doubleTier / 15 <= 0 && doubleTier <= 15) {
            return "Gold " + ((15 % doubleTier) + 1);
        }else if(doubleTier / 20 <= 0 && doubleTier <= 20) {
            return "Platinum " + ((20 % doubleTier) + 1);
        }else if(doubleTier / 25 <= 0 && doubleTier <= 25) {
            return "Diamond " + ((25 % doubleTier) + 1);
        }else if(this.tier == 26) {
            return "Masters";
        }else if(this.tier == 27) {
            return "Grand Master";
        }else return "";
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    public void setHi_rezMMR(double hi_rezMMR) {
        this.hi_rezMMR = hi_rezMMR;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }
}
