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
