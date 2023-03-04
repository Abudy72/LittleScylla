package Discord.Interface.CommandsModule.AcceptCommandStrategies;

import Logic.Dao.Dao;
import Logic.Dao.Model.ServerVerifiedPlayer;
import Logic.Dao.ServerVerifiedPlayerDao;
import Logic.Dao.VerifiedPlayerDao;
import Logic.Exceptions.PlayerVerifiedException;
import Logic.PlayerVerificationModule.VerifiedPlayer;

import java.util.concurrent.atomic.AtomicBoolean;

public class ExistingUser implements VerificationStrategies{

    long guild_id;
    VerifiedPlayer player; //This player will reference the new IGN

    public ExistingUser(long guild_id, VerifiedPlayer player) {
        this.guild_id = guild_id;
        this.player = player;
    }

    @Override
    public void verifyPlayer() throws PlayerVerifiedException {
        Dao<VerifiedPlayer> verifiedPlayerDao = new VerifiedPlayerDao(guild_id);
        Dao<ServerVerifiedPlayer> serverVerifiedPlayerDao = new ServerVerifiedPlayerDao(guild_id);
        AtomicBoolean flag = new AtomicBoolean(false);

        verifiedPlayerDao.get(player.getDiscordId()).ifPresent(verifiedPlayer -> {
            if(verifiedPlayer.getIgn().equals(player.getIgn())){
                if(player.getVerificationMMR() > verifiedPlayer.getVerificationMMR()){
                    verifiedPlayerDao.update(player);
                }
            }
            if(!serverVerifiedPlayerDao.get(player.getDiscordId()).isPresent()){
                serverVerifiedPlayerDao.save(new ServerVerifiedPlayer(player.getDiscordId(),guild_id));
            }else{
                flag.set(true);
            }
        });
        if(flag.get()){
            throw new PlayerVerifiedException("You are already verified!\nIf you need to update your rank or player name, please contact an admin.");
        }
    }
}
