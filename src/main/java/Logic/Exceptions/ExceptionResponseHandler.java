package Logic.Exceptions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;

public class ExceptionResponseHandler {
    public static EmbedBuilder handle(Guild guild, Exception e){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setDescription(e.getMessage());
        builder.setFooter(guild.getName());
        builder.setImage(guild.getIconUrl());
        return builder;
    }
}
