package SmiteMatchsController;

import APIController.APIController;
import APIController.IController;
import Dao.DivisionDao;
import Dao.MainDao;
import Dao.Model.Division;
import Exceptions.DivisionOwnershipException;
import Exceptions.MatchSavedException;
import Exceptions.MatchUnavailableException;
import Exceptions.SmiteAPIUnavailableException;
import SmiteMatchsController.MatchObjectStates.MatchAvailableState;
import SmiteMatchsController.MatchObjectStates.MatchState;
import SmiteMatchsController.MatchObjectStates.MatchUnavailableState;
import SmiteMatchsController.PlayerDataModule.LeaguePlayerData;
import SmiteMatchsController.PlayerDataModule.MatchData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MatchObject {
    private final long matchId;
    private final List<LeaguePlayerData> playerDataList;
    private MatchState state;
    private List<MatchData> matchData;
    private static final TypeToken<List<LeaguePlayerData>> token = new TypeToken<List<LeaguePlayerData>>(){};
    private static final TypeToken<List<MatchData> > matchToken = new TypeToken<List<MatchData>>(){};

    public MatchObject(long matchId) {
        this.matchId = matchId;
        playerDataList = parsePlayerData(loadMatchDetails());
        analyzeState();
    }

    private static List<LeaguePlayerData> parsePlayerData(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
    }
    private static  List<MatchData> parseMatchData(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,matchToken);
    }

    private String loadMatchDetails(){
        MatchObjController matchObjController = new MatchObjController(matchId);
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
            matchData = parseMatchData(apiResponse); // PARSING MATCH DATA WITH SAME REQUEST
            return apiResponse;
        }else{
            throw new SmiteAPIUnavailableException("Hirez responded with " + response.getStatusLine().getStatusCode() + " status code, Service is unavailable.");
        }
    }
    private void analyzeState(){
        if(playerDataList.size() > 1){
            this.state = new MatchAvailableState(this);
        }else if(playerDataList.size() == 1) {
            String publicDate = matchData.get(0).getPublicDate().split("after")[1];
            this.state = new MatchUnavailableState(this);
        }else{
            throw new MatchUnavailableException("Match is no longer available.");
        }
    }

    public void saveMatchToDB(long guild_id,long matchId, long savedBy, String division) throws MatchSavedException, DivisionOwnershipException {
        MainDao<Division> dao = new DivisionDao();
        String finalDivision = division;
        Stream<Division> divisionVerification = dao.getAll().stream().filter(d -> d.getDivisionName().equals(finalDivision) && guild_id == d.getGuild_id());
        if(divisionVerification.findAny().isPresent()){
            this.state.saveMatchToDB(guild_id,matchId,savedBy,division);
        }else throw new DivisionOwnershipException("This server does not have a division with the name: " + finalDivision);
    }

    public long getMatchId() {
        return matchId;
    }

    public List<LeaguePlayerData> getPlayerDataList() {
        return playerDataList;
    }

    public MatchState getState() {
        return state;
    }

    public MatchData getMatchData() {
        return matchData.get(0);
    }
}
