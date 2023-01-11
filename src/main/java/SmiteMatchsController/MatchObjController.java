package SmiteMatchsController;

import APIController.EndPointBuilder;
import SessionManager.*;

public class MatchObjController extends EndPointBuilder {
    private final long matchId;

    protected MatchObjController(long matchId) {
        this.matchId = matchId;
        buildEndPoint();
    }

    @Override
    protected void buildEndPoint() {
        SessionManager sm = SessionManager.getInstance();
        String method = "getmatchdetails";
        String signature = sm.getSignature(method);
        String timestamp = sm.getTimeStamp();
        SessionObj obj = sm.getCurrentSession();
        String buildPath =
                String.format("%s/%sjson/%s/%s/%s/%s/%s",SessionManager.SMITE_API,method,
                        SessionManager.DEV_KEY,signature,obj.getSession_id(),timestamp, matchId);
        this.setEndPoint(buildPath);
    }
}
