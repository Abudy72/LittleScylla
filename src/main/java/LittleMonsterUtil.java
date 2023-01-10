import Exceptions.MatchSavedException;
import SmiteMatchsController.PlayerData;

import java.util.List;

public class LittleMonsterUtil {
    public static void saveMatchToDB(long matchId,List<PlayerData> playerDataList){
        if(!isNewMatch(matchId)){
            //Get current match state
            throw new MatchSavedException(
                    "Match was previously saved by XXX"
            );
        }
    }

    private static boolean isNewMatch(long matchId){
        //Check if match is already saved
        return false;
    }
}
