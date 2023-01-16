package Logic.SessionManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SessionObj {
    String ret_msg;
    String session_id;
    String timestamp;
    private static final TypeToken<SessionObj> token = new TypeToken<SessionObj>(){};
    protected SessionObj(String ret_msg, String session_id, String timestamp) {
        this.ret_msg = ret_msg;
        this.session_id = session_id;
        this.timestamp = timestamp;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public static SessionObj createSession(String apiResponse){
        Gson gson = new Gson();
        return gson.fromJson(apiResponse,token);
    }

    @Override
    public String toString() {
        return "Session_ID: " + this.session_id +
                " Current time: " + this.timestamp;
    }
}
