package Discord.Interface;

import Discord.Interface.CommandsModule.AcceptCommand;
import Discord.Interface.CommandsModule.ManualVerificationCommand;
import Discord.Interface.CommandsModule.SaveMatchStatsCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CommandHandler extends ListenerAdapter {
    static final String PLAYER = "player";
    static final String ACCEPT = "accept";
    static final String SAVE = "save";
    static final String MATCHES = "matches";
    static final String CHAMP = "gods";
    static final String DROP_PLAYER = "drop_player";
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
                new ManualVerificationCommand().handleCommand(event);
                break;
            case SAVE:
                new SaveMatchStatsCommand().handleCommand(event);
        }
    }
}
