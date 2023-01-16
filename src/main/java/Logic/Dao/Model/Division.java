package Logic.Dao.Model;

public class Division {
    private final String divisionName;
    private final long guild_id;

    public Division(String divisionName, long guild_id) {
        this.divisionName = divisionName;
        this.guild_id = guild_id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public long getGuild_id() {
        return guild_id;
    }
}
