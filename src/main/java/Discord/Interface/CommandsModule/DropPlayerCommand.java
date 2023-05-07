package Discord.Interface.CommandsModule;

import Logic.Dao.ServerVerifiedPlayerDao;
import Logic.Exceptions.ExceptionResponseHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;


public class DropPlayerCommand extends CustomCommandListener{
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        try{
            Member member = event.getOption("member").getAsMember();
            User user = event.getOption("member").getAsUser();
            ServerVerifiedPlayerDao dao = new ServerVerifiedPlayerDao(event.getGuild().getIdLong());
            if(dao.delete(member.getIdLong())){
                event.getHook().sendMessageEmbeds(successfulMessage(event.getGuild(),user)).queue();
            }else {
                event.getHook().sendMessageEmbeds(unableToDropPlayer(event.getGuild(),user)).queue();
            }
        }catch (Exception e){
            event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(), new RuntimeException("Unknown Error."))
                    .build()).queue();
        }
    }

    private MessageEmbed successfulMessage(Guild guild,User user){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.GREEN);
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle(user.getAsTag() + " has been Dropped successfully.");
        return builder.build();
    }

    private MessageEmbed unableToDropPlayer(Guild guild, User user){
        EmbedBuilder builder =  new EmbedBuilder();
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setDescription(user.getAsTag() + " was **not dropped**. Player needs to be verified first.\nIf you think this is a mistake, " +
                "ping a bot admin!");
        builder.setColor(Color.RED);
        return builder.build();
    }
}
