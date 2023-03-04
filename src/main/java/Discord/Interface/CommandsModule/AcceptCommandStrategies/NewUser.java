package Discord.Interface.CommandsModule.AcceptCommandStrategies;

import Logic.Dao.Dao;
import Logic.Dao.Model.ServerVerifiedPlayer;
import Logic.Dao.ServerVerifiedPlayerDao;
import Logic.Dao.VerifiedPlayerDao;
import Logic.PlayerVerificationModule.VerifiedPlayer;

public class NewUser implements VerificationStrategies {

    VerifiedPlayer player;
    long guild_id;

    public NewUser(VerifiedPlayer player, long guild_id) {
        this.player = player;
        this.guild_id = guild_id;
    }

    @Override
    public void verifyPlayer() {
        Dao<VerifiedPlayer> verifiedPlayerDao = new VerifiedPlayerDao(guild_id);
        Dao<ServerVerifiedPlayer> serverVerifiedPlayerDao = new ServerVerifiedPlayerDao(guild_id);
        verifiedPlayerDao.save(player);
        serverVerifiedPlayerDao.save(new ServerVerifiedPlayer(player.getDiscordId(),guild_id));
    }
}
