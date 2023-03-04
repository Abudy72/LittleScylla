package Logic.SmiteMatchsController.PlayerDataModule;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MatchPublicDateParser {
    @SerializedName("ret_msg")
    private String publicDate;

    public Timestamp getTimestamp() {
        System.out.println(publicDate);
        String publicDateHolder = publicDate.split("after ")[1];
        java.util.Date actualPublicDate;
        try {
           SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           actualPublicDate = sdf.parse(publicDateHolder);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return Timestamp.from(actualPublicDate.toInstant().plusSeconds(604800));
    }
}
