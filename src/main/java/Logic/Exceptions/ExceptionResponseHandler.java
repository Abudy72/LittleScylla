package Logic.Exceptions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;

public class ExceptionResponseHandler {
    private static String errorImage = "https://static.thenounproject.com/png/3688947-200.png";
    public static EmbedBuilder handle(Guild guild, Exception e){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle(":octagonal_sign: Unsuccessful Operation :octagonal_sign:");
        builder.setThumbnail(errorImage);
        builder.setDescription("**" + e.getMessage() + "**");
        builder.setFooter(guild.getName(),guild.getIconUrl());
        return builder;
    }
}
