package Interface.CommandsModule;

import Logic.PlayerVerificationModule.Platform;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Interface.CommandsModule.AcceptCommand.IGN;

public class ManualVerificationCommand extends CustomCommandListener{
    private final static String RANK = "rank";
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        String ign = event.getOption(IGN).getAsString();
        String rank = event.getOption(RANK).getAsString();
        User user = event.getOption("id").getAsUser();

    }

    private String rankParser(String rank){
        rank = rank.toLowerCase();

        switch (rank) {
            case "bronze":
                return Platform.Rank.Bronze.toString();
            case "silver":
                return Platform.Rank.Silver.toString();
            case "gold":
                return Platform.Rank.Gold.toString();
            case "platinum":
                return Platform.Rank.Platinum.toString();
            case "diamond":
                return Platform.Rank.Diamond.toString();
        }
        Pattern pattern = Pattern.compile("(grand\\s*masters*|gm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(rank);
        if(matcher.find()){
            return Platform.Rank.GrandMaster.toString();
        }else{
            pattern = Pattern.compile("masters*", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(rank);
            if(matcher.find()){
                return Platform.Rank.Masters.toString();
            }else{
                return Platform.Rank.Unranked.toString();
            }
        }
    }
}
