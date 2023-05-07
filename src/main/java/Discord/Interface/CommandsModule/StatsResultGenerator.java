package Discord.Interface.CommandsModule;

import Logic.SmiteMatchsController.MatchObject;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.File;

public class StatsResultGenerator extends CustomCommandListener{
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        long matchId = event.getOption("match_id").getAsLong();
        MatchObject matchObject = new MatchObject(matchId);
        File file = writeMatchDetailsIntoCSV(matchObject);
        event.replyFile(file).queue();
        file.delete();
    }

    public File writeMatchDetailsIntoCSV(MatchObject obj) {
        File file = new File("stats_"+obj.getMatchId()+".csv");
        try{
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            obj.writeMatchDetailsAsCSV(file);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return file;
    }
}
