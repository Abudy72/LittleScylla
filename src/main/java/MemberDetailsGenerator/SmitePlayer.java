package MemberDetailsGenerator;

import APIController.APIController;
import APIController.IController;
import Exceptions.PlayerNotFoundException;
import Exceptions.SmiteAPIUnavailableException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class SmitePlayer {
    @SerializedName("hz_player_name")
    private String pc_IGN;
    @SerializedName("hz_gamer_name")
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
    private MemberRankedDetails rankedController;
    private static final TypeToken<List<SmitePlayer>> token = new TypeToken<List<SmitePlayer>>(){};
    private static List<SmitePlayer> parseResponseIntoPlayerDetails(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
    }
    public static SmitePlayer getPlayerDetails(String playerName, String portalID) {
        MemberDetailsAPIController p = new MemberDetailsAPIController(playerName, portalID);
        IController controller = new APIController();
        HttpResponse response = controller.sendRequest(p);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            String jsonResponse = "";
            try {
                jsonResponse = EntityUtils.toString(response.getEntity());
                List<SmitePlayer> resultSet = parseResponseIntoPlayerDetails(jsonResponse);
                if(!resultSet.isEmpty()) return resultSet.get(0);
                throw new PlayerNotFoundException("Unable to find player, either the IGN/Platform selected was wrong or profile is hidden.");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Unknown Error");
            }
        }else {
            throw new SmiteAPIUnavailableException("Status code: " + statusCode + ", Hi-rez servers unavailable");
        }
    }
    public SmitePlayer(String pc_IGN, String console_IGN, int hoursPlayed, int playerId,
                       String last_login, String region, MemberRankedDetails rankedConquest,
                       MemberRankedDetails rankedConquestController, MemberRankedDetails rankedDuel,
                       MemberRankedDetails rankedDuelController, MemberRankedDetails rankedJoust, MemberRankedDetails rankedController) {
        this.pc_IGN = pc_IGN;
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
        this.rankedController = rankedController;
    }


    public String getPc_IGN() {
        return pc_IGN;
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

    public MemberRankedDetails getRankedController() {
        return rankedController;
    }

    public void setRankedController(MemberRankedDetails rankedController) {
        this.rankedController = rankedController;
    }
}
