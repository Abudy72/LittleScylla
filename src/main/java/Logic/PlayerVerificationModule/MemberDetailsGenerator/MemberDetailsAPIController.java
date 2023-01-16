package Logic.PlayerVerificationModule.MemberDetailsGenerator;

import Logic.APIController.EndPointBuilder;
import Logic.SessionManager.SessionManager;
import Logic.SessionManager.SessionObj;

public class MemberDetailsAPIController extends EndPointBuilder {
    private final String playerName;
    private final String portalID;
    public MemberDetailsAPIController(String playerName, String portalID) {
        this.playerName = playerName;
        this.portalID = portalID;
        buildEndPoint();
    }

    @Override
    protected void buildEndPoint() {
        SessionManager sm = SessionManager.getInstance();
        String method = "getplayer";
        String signature = sm.getSignature(method);
        String timestamp = sm.getTimeStamp();
        SessionObj obj = sm.getCurrentSession();
        String buildPath =
                String.format("%s/%sjson/%s/%s/%s/%s/%s/%s",SessionManager.SMITE_API,method,
                        SessionManager.DEV_KEY,signature,obj.getSession_id(),timestamp, playerName, portalID); // Player and Platform
        this.setEndPoint(buildPath);
    }
}
