package Discord.Interface.CommandsModule;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;


public abstract class CustomCommandListener {
    private final String authorizationError = "https://static.thenounproject.com/png/4373971-200.png";
    public EmbedBuilder generateUnAuthorizedResponse(Guild guild){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.RED);
        builder.setDescription("**You are not authorized to use this command!**\n");
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.appendDescription("`If you think this is a mistake, please contact an admin`");

        builder.setThumbnail(authorizationError);
        return builder;
    }


    public abstract void handleCommand(SlashCommandInteractionEvent event);

    protected void applyRole(Guild guild,Role role, Member member, int delay){
        guild.addRoleToMember(member,role).queueAfter(delay, TimeUnit.SECONDS);
    }

    protected void removeRole(Guild guild,Role role, Member member, int delay){
        guild.removeRoleFromMember(member,role).queueAfter(delay, TimeUnit.SECONDS);
    }

    public static boolean hasRole(long roleId, Member member){
        return member.getRoles().stream().anyMatch(role -> role.getIdLong() == roleId);
    }

}
