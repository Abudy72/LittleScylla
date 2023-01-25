package Interface.CommandsModule.AcceptCommandStrategies;

import Logic.Dao.Dao;
import Logic.Dao.Model.ServerVerifiedPlayer;
import Logic.Dao.ServerVerifiedPlayerDao;
import Logic.Dao.VerifiedPlayerDao;
import Logic.PlayerVerificationModule.VerifiedPlayer;

public class ExistingUser implements VerificationStrategies{

    long guild_id;
    VerifiedPlayer player; //This player will reference the new IGN

    public ExistingUser(long guild_id, VerifiedPlayer player) {
        this.guild_id = guild_id;
        this.player = player;
    }

    @Override
    public void verifyPlayer() {
        Dao<VerifiedPlayer> verifiedPlayerDao = new VerifiedPlayerDao(guild_id);
        Dao<ServerVerifiedPlayer> serverVerifiedPlayerDao = new ServerVerifiedPlayerDao(guild_id);

        verifiedPlayerDao.get(player.getDiscordId()).ifPresent(verifiedPlayer -> {
            if(verifiedPlayer.getIgn().equals(player.getIgn())){
                if(player.getVerificationMMR() > verifiedPlayer.getVerificationMMR()){
                    verifiedPlayerDao.update(player);
                }
                serverVerifiedPlayerDao.save(new ServerVerifiedPlayer(player.getDiscordId(),guild_id));
            }
        });
    }
}
