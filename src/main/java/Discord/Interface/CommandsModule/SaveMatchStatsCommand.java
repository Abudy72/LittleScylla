package Discord.Interface.CommandsModule;

import Logic.Exceptions.DivisionOwnershipException;
import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.MatchSavedException;
import Logic.Exceptions.MatchUnavailableException;
import Logic.SmiteMatchsController.MatchObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SaveMatchStatsCommand extends CustomCommandListener {
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String division = event.getOption("division").getAsString();
        long matchId = event.getOption("id").getAsLong();
        MatchObject matchObject = new MatchObject(matchId);
        try{
            matchObject.saveMatchToDB(event.getGuild().getIdLong(),event.getMember().getIdLong(),division);
            event.getHook().sendMessage("Match saved!").queue();
        }catch (MatchSavedException | DivisionOwnershipException | MatchUnavailableException e){
            event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(),e).build()).queue();
        }
    }
}
