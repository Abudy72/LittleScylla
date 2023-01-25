package Logic.Dao.Model;

public class ServerVerifiedPlayer {
    private final long discordId;
    private final long verifiedInGuild;

    public ServerVerifiedPlayer(long discordId, long verifiedInGuild) {
        this.discordId = discordId;
        this.verifiedInGuild = verifiedInGuild;
    }

    public long getDiscordId() {
        return discordId;
    }

    public long getVerifiedInGuild() {
        return verifiedInGuild;
    }
}
