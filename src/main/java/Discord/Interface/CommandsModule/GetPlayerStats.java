package Discord.Interface.CommandsModule;

import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.PlayerNotFoundException;
import Logic.Stats.PlayerStats;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class GetPlayerStats extends CustomCommandListener{
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
       try{
           String ign = event.getOption("ign").getAsString();
           String division = event.getOption("division").getAsString();
           PlayerStats playerStats = new PlayerStats(ign,division);
           event.replyEmbeds(playerStats.messageBuilder(guild)).queue();
       }catch (NullPointerException e){
           e.printStackTrace();
       } catch (PlayerNotFoundException e) {
           event.replyEmbeds(ExceptionResponseHandler.handle(guild, e).build()).queue();
       }
    }
}
