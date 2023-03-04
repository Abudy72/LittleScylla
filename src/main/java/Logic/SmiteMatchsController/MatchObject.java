package Logic.SmiteMatchsController;

import Logic.APIController.APIController;
import Logic.APIController.IController;
import Logic.Dao.DivisionDao;
import Logic.Dao.MiniDao;
import Logic.Dao.Model.Division;
import Logic.Exceptions.DivisionOwnershipException;
import Logic.Exceptions.MatchSavedException;
import Logic.Exceptions.MatchUnavailableException;
import Logic.Exceptions.SmiteAPIUnavailableException;
import Logic.SmiteMatchsController.MatchObjectStates.MatchAvailableState;
import Logic.SmiteMatchsController.MatchObjectStates.MatchState;
import Logic.SmiteMatchsController.MatchObjectStates.MatchUnavailableState;
import Logic.SmiteMatchsController.PlayerDataModule.MatchPlayerData;
import Logic.SmiteMatchsController.PlayerDataModule.MatchPublicDateParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class MatchObject {
    private final long matchId;
    private final List<MatchPlayerData> playerDataList;
    private MatchState state;
    private MatchPublicDateParser matchPublicDateParser;
    private static final TypeToken<List<MatchPlayerData>> token = new TypeToken<List<MatchPlayerData>>(){};
    private static final TypeToken<List<MatchPublicDateParser>> publicDateToken = new TypeToken<List<MatchPublicDateParser>>(){};

    public MatchObject(long matchId) {
        this.matchId = matchId;
        String apiResponse = loadMatchDetails();
        this.playerDataList = parsePlayerData(apiResponse);
        this.matchPublicDateParser = matchPublicDateParser(apiResponse);
        for(MatchPlayerData d: playerDataList){
            d.resolvePlayerName();
            d.resolveWinStatus();
        }
        analyzeState();
    }

    private static List<MatchPlayerData> parsePlayerData(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
    }
    private static MatchPublicDateParser matchPublicDateParser(String json){
        Gson gson = new Gson();
        if(gson.fromJson(json,publicDateToken).isEmpty()){
            throw new MatchUnavailableException("Match seems to be unavailable for unknown reason.");
        }else {
            return gson.fromJson(json,publicDateToken).get(0);
        }
    }

    private String loadMatchDetails(){
        MatchObjController matchObjController = new MatchObjController(matchId);
        System.out.println(matchObjController.getEndPoint());
        IController controller = new APIController();
        HttpResponse response = controller.sendRequest(matchObjController);
        if(response.getStatusLine().getStatusCode() == 200){
            String apiResponse = null;
            try{
                apiResponse = EntityUtils.toString(response.getEntity());
            }catch (IOException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
            return apiResponse;
        }else{
            throw new SmiteAPIUnavailableException("Hirez responded with " + response.getStatusLine().getStatusCode() + " status code, Service is unavailable.");
        }
    }
    private void analyzeState(){
        if(playerDataList.size() > 1){
            this.state = new MatchAvailableState(this);
        }else if(playerDataList.size() == 1) {
            this.state = new MatchUnavailableState(this);
        }else{
            throw new MatchUnavailableException("Match is no longer available.");
        }
    }

    public void saveMatchToDB(long guild_id, long savedBy, String division) throws MatchSavedException, DivisionOwnershipException {
        MiniDao<Division> dao = new DivisionDao();
        String finalDivision = division;
        Stream<Division> divisionVerification = dao.getAll().stream().filter(d -> d.getDivisionName().equals(finalDivision) && guild_id == d.getGuild_id());
        if(divisionVerification.findAny().isPresent()){
            this.state.saveMatchToDB(guild_id,matchId,savedBy,division);
        }else throw new DivisionOwnershipException("This server does not have a division with name: " + finalDivision);
    }

    public long getMatchId() {
        return matchId;
    }

    public List<MatchPlayerData> getPlayerDataList() {
        return playerDataList;
    }

    public MatchState getState() {
        return state;
    }

    public MatchPublicDateParser getMatchPublicDateParser() {
        return matchPublicDateParser;
    }

    public static void main(String[] args) {
        MatchObject m = new MatchObject(1291409417);
    }
}
