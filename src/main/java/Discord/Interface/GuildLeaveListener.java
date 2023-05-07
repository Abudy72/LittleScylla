package Discord.Interface;

import Logic.Dao.LeagueRolesInfoDao;
import Logic.Dao.Model.LeagueRolesInfo;
import Logic.Dao.ServerVerifiedPlayerDao;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildLeaveListener extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        ServerVerifiedPlayerDao dao = new ServerVerifiedPlayerDao(event.getGuild().getIdLong());

            new LeagueRolesInfoDao().get(event.getGuild().getIdLong()).ifPresent(
                    info -> {
                        TextChannel channel = event.getGuild().getTextChannelById(info.getVerificationChannel_uid());
                        if (dao.delete(event.getMember().getIdLong())) {
                            channel.sendMessage(event.getUser().getAsTag() + " left the server and was dropped successfully").queue();
                        }else{
                            channel.sendMessage(event.getUser().getAsTag() + " left the server and was **not** dropped, ping a bot admin for member to be dropped manually.").queue();
                        }
                    }
            );
        }
}
