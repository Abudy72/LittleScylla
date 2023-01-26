package Logic.PlayerVerificationModule;

import Logic.APIController.APIController;
import Logic.APIController.IController;
import Logic.Exceptions.PlayerNotFoundException;
import Logic.Exceptions.SmiteAPIUnavailableException;
import Logic.PlayerVerificationModule.MemberDetailsGenerator.MemberRankedDetails;
import Logic.PlayerVerificationModule.MemberDetailsGenerator.MemberDetailsAPIController;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SmitePlayer {
    @SerializedName("hz_player_name")
    private String pc_IGN;
    @SerializedName("Created_Datetime")
    private String accountDate;
    @SerializedName("hz_gamer_tag")
    private String console_IGN;
    @SerializedName("HoursPlayed")
    private int hoursPlayed;
    @SerializedName("ActivePlayerId")
    private int playerId;
    @SerializedName("Last_Login_Datetime")
    private String last_login;
    @SerializedName("Region")
    private String region;
    @SerializedName("RankedConquest")
    private MemberRankedDetails rankedConquest;
    @SerializedName("RankedConquestController")
    private MemberRankedDetails rankedConquestController;
    @SerializedName("RankedDuel")
    private MemberRankedDetails rankedDuel;
    @SerializedName("RankedDuelController")
    private MemberRankedDetails rankedDuelController;
    @SerializedName("RankedJoust")
    private MemberRankedDetails rankedJoust;
    @SerializedName("RankedJoustController")
    private MemberRankedDetails rankedJoustController;
    private static final TypeToken<List<SmitePlayer>> token = new TypeToken<List<SmitePlayer>>(){};
    private static List<SmitePlayer> parseResponseIntoPlayerDetails(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
    }
    protected static SmitePlayer generatePlayerDetails(String playerName, String portalID) throws PlayerNotFoundException {
        MemberDetailsAPIController p = new MemberDetailsAPIController(playerName, portalID);
        IController controller = new APIController();
        HttpResponse response = controller.sendRequest(p);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String jsonResponse = "";
            try {
                jsonResponse = EntityUtils.toString(response.getEntity());
                List<SmitePlayer> resultSet = parseResponseIntoPlayerDetails(jsonResponse);
                if(resultSet.isEmpty()) throw new PlayerNotFoundException("Unable to find player, either the IGN/Platform selected is wrong or profile is hidden.");
               return resultSet.get(0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Unknown Error");
            }
        }else {
            throw new SmiteAPIUnavailableException("Status code: " + statusCode + ", Hi-rez servers unavailable");
        }
    }
    protected SmitePlayer(){}
    /*protected SmitePlayer(String pc_IGN, String console_IGN, int hoursPlayed, int playerId,
                       String last_login, String region, String accountDate, MemberRankedDetails rankedConquest,
                       MemberRankedDetails rankedConquestController, MemberRankedDetails rankedDuel,
                       MemberRankedDetails rankedDuelController, MemberRankedDetails rankedJoust, MemberRankedDetails rankedJoustController) {
        this.pc_IGN = pc_IGN;
        this.accountDate = accountDate;
        this.console_IGN = console_IGN;
        this.hoursPlayed = hoursPlayed;
        this.playerId = playerId;
        this.last_login = last_login;
        this.region = region;
        this.rankedConquest = rankedConquest;
        this.rankedConquestController = rankedConquestController;
        this.rankedDuel = rankedDuel;
        this.rankedDuelController = rankedDuelController;
        this.rankedJoust = rankedJoust;
        this.rankedJoustController = rankedJoustController;
    }*/

    public String getPc_IGN() {
        return pc_IGN;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setPc_IGN(String pc_IGN) {
        this.pc_IGN = pc_IGN;
    }

    public String getConsole_IGN() {
        return console_IGN;
    }

    public void setConsole_IGN(String console_IGN) {
        this.console_IGN = console_IGN;
    }

    public int getHoursPlayed() {
        return hoursPlayed;
    }

    public void setHoursPlayed(int hoursPlayed) {
        this.hoursPlayed = hoursPlayed;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public MemberRankedDetails getRankedConquest() {
        return rankedConquest;
    }

    public void setRankedConquest(MemberRankedDetails rankedConquest) {
        this.rankedConquest = rankedConquest;
    }

    public MemberRankedDetails getRankedConquestController() {
        return rankedConquestController;
    }

    public void setRankedConquestController(MemberRankedDetails rankedConquestController) {
        this.rankedConquestController = rankedConquestController;
    }

    public MemberRankedDetails getRankedDuel() {
        return rankedDuel;
    }

    public void setRankedDuel(MemberRankedDetails rankedDuel) {
        this.rankedDuel = rankedDuel;
    }

    public MemberRankedDetails getRankedDuelController() {
        return rankedDuelController;
    }

    public void setRankedDuelController(MemberRankedDetails rankedDuelController) {
        this.rankedDuelController = rankedDuelController;
    }

    public MemberRankedDetails getRankedJoust() {
        return rankedJoust;
    }

    public void setRankedJoust(MemberRankedDetails rankedJoust) {
        this.rankedJoust = rankedJoust;
    }

    public MemberRankedDetails getRankedJoustController() {
        return rankedJoustController;
    }

    public void setRankedJoustController(MemberRankedDetails rankedJoustController) {
        this.rankedJoustController = rankedJoustController;
    }

    protected int getHighestMMR(ArrayList<MemberRankedDetails> list){
        int result = 0;

        for(MemberRankedDetails m: list){
            if(m.getHi_rezMMR() > result){
                result = (int) m.getHi_rezMMR();
            }
        }
        return result;
    }

    public ArrayList<MemberRankedDetails> getRankedDetails(){
        ArrayList<MemberRankedDetails> result = new ArrayList<>();

        result.add(this.rankedConquest);
        result.add(this.rankedConquestController);
        result.add(this.rankedJoust);
        result.add(this.rankedJoustController);
        result.add(this.rankedDuel);
        result.add(this.rankedDuelController);

        result.removeIf(Objects::isNull);
        return result;

    }

    public String pcRankedDetailsPrettyPrint(){
        return "Conq.: " + this.getRankedConquest().getHi_rezMMR() + ": " + this.getRankedConquest().getActualRank()
                + "\nJoust: " + this.getRankedJoust().getHi_rezMMR() + ": " +this.getRankedJoust().getActualRank()
                + "\nDuel: " + this.getRankedDuel().getHi_rezMMR() + ": " +this.getRankedDuel().getActualRank();
    }
    public String consoleRankedDetailsPrettyPrint(){
        return "Conq.: " + this.getRankedConquestController().getHi_rezMMR() + ": " + this.getRankedConquestController().getActualRank()
                + "\nJoust: " + this.getRankedJoustController().getHi_rezMMR() + ": " + this.getRankedJoustController().getActualRank()
                + "\nDuel: " + this.getRankedDuelController().getHi_rezMMR() + ": " + this.getRankedDuelController().getActualRank();
    }

    @Override
    public String toString() {
        return "\nAccountDate: `" + this.accountDate
                + "`\nHours Played: " + this.hoursPlayed
                + "\nConq MMR: " + this.rankedConquest.getHi_rezMMR()
                + "\nDuel MMR: " + this.rankedDuel.getHi_rezMMR()
                + "\nJoust MMR: " + this.rankedJoust.getHi_rezMMR()
                + "\nConq (Controller) MMR: " + this.rankedConquestController.getHi_rezMMR()
                + "\nDuel (Controller) MMR: " + this.rankedDuelController.getHi_rezMMR()
                + "\nJoust (Controller) MMR: " + this.rankedJoustController.getHi_rezMMR();
    }
}
