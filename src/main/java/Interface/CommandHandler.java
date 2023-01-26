package Interface;

import Interface.CommandsModule.AcceptCommand;
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
    static final String VERIFY = "verify";
    static final String REGISTER = "register";
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch (event.getName()) {
            case ACCEPT:
                System.out.println(event.getName());
                new AcceptCommand().handleCommand(event);
                break;
        }
    }
}
