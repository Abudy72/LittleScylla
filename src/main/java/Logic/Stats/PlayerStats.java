package Logic.Stats;

import Logic.Exceptions.PlayerNotFoundException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;

import java.awt.*;

public class PlayerStats{
    private long discordId;
    private String ign;
    private final BasicStats stats;
    private final String division;
    public PlayerStats(long discordId, String division) throws PlayerNotFoundException {
        this.discordId = discordId;
        this.division = division;
        stats = StatsUtil.fetchPlayerData(discordId,null,division);
    }
    public PlayerStats(String ign, String division) throws PlayerNotFoundException {
        this.division = division;
        stats = StatsUtil.fetchPlayerData(0,ign,division);
        this.ign = ign;
    }


    public MessageEmbed messageBuilder(Guild guild){
        EmbedBuilder builder = new EmbedBuilder();
        Member member = guild.getMemberById(discordId);
        try{builder.setThumbnail(member.getEffectiveAvatarUrl());}catch (NullPointerException ignored){
            builder.setThumbnail(StatsUtil.stats_logo);
        }
        builder.setColor(Color.GREEN);
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle(ign + "'s stats!");
        builder.setDescription("Division: " + division);
        Field totalStats = new Field(
                "Overall division stats",
                StatsUtil.statsProcessor(stats) + "\n**Wins: " + stats.getWins() +
                        "\nlosses: " + stats.getLosses() + "**",
                true
        );

        Field statsPerGame = new Field(
                "Stats/game",
                StatsUtil.statsProcessor(stats),
                true
        );

        Field statsPerMin = new Field(
                "Stats/minute",
                StatsUtil.statsProcessor(stats),
                true
        );
        builder.addField(totalStats);
        builder.addField(statsPerGame);
        builder.addField(statsPerMin);

        return builder.build();
    }
}
