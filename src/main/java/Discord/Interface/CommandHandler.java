package Discord.Interface;

import Discord.Interface.CommandsModule.*;
import Logic.Dao.LeagueRolesInfoDao;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CommandHandler extends ListenerAdapter {
    private LeagueRolesInfoDao dao = new LeagueRolesInfoDao();
    static final String PLAYER_STATS = "player_stats";
    static final String ACCEPT = "accept";
    static final String SAVE = "save";
    static final String MATCHES = "matches";
    static final String CHAMP = "gods";
    static final String DROP_PLAYER = "drop";
    static final String PLAYERS_INFO="players_info";
    static final String CSV_COMMAND = "match_stats_csv";
    static final String VERIFY = "manual_verification";
    static final String REGISTER = "register";
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case ACCEPT:
                new AcceptCommand().handleCommand(event);
                break;
            case VERIFY:
                dao.get(event.getGuild().getIdLong()).ifPresent(leagueInfo -> {
                    if(CustomCommandListener.hasRole(leagueInfo.getStaffRole_uid(),event.getMember())){
                        new ManualVerificationCommand().handleCommand(event);
                    }else{
                        EmbedBuilder builder = new ManualVerificationCommand().generateUnAuthorizedResponse(event.getGuild());
                        event.replyEmbeds(builder.build()).queue();
                    }
                });
                break;
            case SAVE:
                dao.get(event.getGuild().getIdLong()).ifPresent(leagueInfo -> {
                    if(CustomCommandListener.hasRole(leagueInfo.getStats_role(),event.getMember())
                    || CustomCommandListener.hasRole(leagueInfo.getStaffRole_uid(),event.getMember())){
                        new SaveMatchStatsCommand().handleCommand(event);
                    }else{
                        EmbedBuilder builder = new ManualVerificationCommand().generateUnAuthorizedResponse(event.getGuild());
                        event.replyEmbeds(builder.build()).queue();
                    }
                });
                break;
            case PLAYER_STATS:
                new GetPlayerStats().handleCommand(event);
            case DROP_PLAYER:
                dao.get(event.getGuild().getIdLong()).ifPresent(leagueInfo -> {
                    if(CustomCommandListener.hasRole(leagueInfo.getStaffRole_uid(),event.getMember())){
                        new DropPlayerCommand().handleCommand(event);
                    }else{
                        EmbedBuilder builder = new ManualVerificationCommand().generateUnAuthorizedResponse(event.getGuild());
                        event.replyEmbeds(builder.build()).queue();
                    }
                });
                break;
            case CSV_COMMAND:
                new StatsResultGenerator().handleCommand(event);
        }
    }
}
