package SmiteMatchsController;

import APIController.APIController;
import APIController.IController;
import Exceptions.MatchUnavailableException;
import Exceptions.SmiteAPIUnavailableException;
import SmiteMatchsController.MatchObjectStates.MatchAvailableState;
import SmiteMatchsController.MatchObjectStates.MatchState;
import SmiteMatchsController.MatchObjectStates.MatchUnavailableState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class MatchObject {
    private final int matchId;
    private final List<PlayerData> playerDataList;
    private MatchState state;
    private static final TypeToken<List<PlayerData>> token = new TypeToken<List<PlayerData>>(){};
    public MatchObject(int matchId) {
        this.matchId = matchId;
        playerDataList = parsePlayerData(loadMatchDetails());
        analyzeState();
    }

    private static List<PlayerData> parsePlayerData(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
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
            return apiResponse;
        }else{
            throw new SmiteAPIUnavailableException("Hirez responded with " + response.getStatusLine().getStatusCode() + " status code, Service is unavailable.");
        }
    }
    private void analyzeState(){
        if(playerDataList.size() > 1){
            this.state = new MatchAvailableState(this);
        }else if(playerDataList.size() == 1) {
            String publicDate = playerDataList.get(0).getPublicDate().split("after")[1];
            this.state = new MatchUnavailableState(publicDate);
        }else{
            throw new MatchUnavailableException("Match is no longer available.");
        }
    }

    public void saveMatchToDB(){
        this.state.saveMatchToDB();
    }
}
