package SessionManager;

import APIController.APIController;
import APIController.EndPointBuilder;
import APIController.IController;
import Exceptions.SmiteAPIUnavailableException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SessionManager extends EndPointBuilder {
    public final static String DEV_KEY = System.getenv("DEV_KEY");
    private final static String AUTH_KEY = System.getenv("AUTH_KEY");
    public final static String SMITE_API = "https://api.smitegame.com/smiteapi.svc";
    private static SessionManager instance;
    private SessionObj currentSession;

    public String getTimeStamp() {
        LocalDateTime datetime = LocalDateTime.now(ZoneId.of("UTC"));
        return datetime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public String getSignature(String method) {
        String timestamp = getTimeStamp();
        String signature = DEV_KEY + method + AUTH_KEY + timestamp;
        byte[] msg = signature.getBytes();
        byte[] hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            hash = md.digest(msg);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : hash) {
            strBuilder.append(String.format("%02x", b));
        }
        return strBuilder.toString();
    }

    public void updateSessionId() { //This method is used to get a valid sessionId
        if (!validateSession()) {
            IController controller = new APIController();
            buildEndPoint();
            HttpResponse response = controller.sendRequest(this);
            String jsonResponse = "";
            try{
                jsonResponse = EntityUtils.toString(response.getEntity());
            }catch (IOException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            currentSession = SessionObj.createSession(jsonResponse);
        }
        System.out.println(currentSession);
    }

    private boolean validateSession() {
        if(currentSession == null){
            return false;
        }else{
            //verify current sessionId
            IController controller = new APIController();
            HttpResponse response = controller.sendRequest(this);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200){
                String jsonResponse = "";
                try{
                    jsonResponse = EntityUtils.toString(response.getEntity());
                }catch (IOException e){
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
                return !jsonResponse.contains("Invalid");
            }else {
                throw new SmiteAPIUnavailableException("Status code: " + statusCode + ", Hi-rez servers unavailable");
            }
        }
    }
    @Override
    protected void buildEndPoint() {
        String timeStamp = getTimeStamp();
        String hashedKey = getSignature("createsession");
        String method = "createsessionjson";
        this.setEndPoint(String.format("%s/%s/%s/%s/%s", SMITE_API, method, DEV_KEY, hashedKey, timeStamp));
    }


    private SessionManager() {

    }

    // Singleton
    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    public SessionObj getCurrentSession() {
        this.updateSessionId();
        return currentSession;
    }
}
