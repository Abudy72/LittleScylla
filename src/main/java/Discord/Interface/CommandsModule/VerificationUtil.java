package Discord.Interface.CommandsModule;

import Logic.Dao.LeagueRolesInfoDao;
import Logic.Dao.Model.LeagueRolesInfo;
import Logic.PlayerVerificationModule.SmitePlayer;
import Logic.PlayerVerificationModule.VerifiedPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class VerificationUtil {
    public static long getPlatformRoleId(VerifiedPlayer player, LeagueRolesInfo entities){
        switch (player.getPlatform().toString()){
            case "PC":
                return entities.getPc_uid();
            case "Xbox":
                return entities.getXbox_uid();
            case "PSN":
                return entities.getPsn_uid();
            default:
                return 0;
        }
    }
    public static long getRankRoleId(LeagueRolesInfo entities, VerifiedPlayer player){
        switch (player.getCurrentRank()){
            case GrandMaster:
                return entities.getGrandMasters_uid();
            case Masters:
                return entities.getMasters_uid();
            case Diamond:
                return entities.getDiamond_uid();
            case Platinum:
                return entities.getPlatinum_uid();
            case Gold:
                return entities.getGold_uid();
            case Silver:
                return entities.getSilver_uid();
            case Bronze:
                return entities.getBronze_uid();
            default:
                return 0;
        }
    }
    public static void sendPlayerDataToVerificationChannel(Guild guild,VerifiedPlayer player, CustomCommandListener commandListener, Member member){
        SmitePlayer p =player.getSmiteAccount();
        EmbedBuilder builder = new EmbedBuilder();
        LeagueRolesInfoDao dao = new LeagueRolesInfoDao();
        builder.setColor(Color.GREEN);
        if(p != null){
            MessageEmbed.Field pcRankedDetails = new MessageEmbed.Field("PC Ranked Details",p.pcRankedDetailsPrettyPrint(),true);
            MessageEmbed.Field consoleRankedDetails = new MessageEmbed.Field("Controller Ranked Details",p.consoleRankedDetailsPrettyPrint(),true);
            builder.addField(pcRankedDetails);
            builder.addField(consoleRankedDetails);
        }
        builder.setDescription(player.toString());
        builder.setTitle("In-game name: " + player.getIgn());

        builder.setFooter(guild.getName(),guild.getIconUrl());

        dao.get(guild.getIdLong()).ifPresent(leagueInfo -> {
            long rankRole = getRankRoleId(leagueInfo,player);
            long platformRole = getPlatformRoleId(player,leagueInfo);
            try{commandListener.applyRole(guild,guild.getRoleById(rankRole), member, 0);}catch (Exception ignored) {}
            try{commandListener.applyRole(guild,guild.getRoleById(platformRole), member, 2);}catch (Exception ignored) {}
            try{commandListener.applyRole(guild,guild.getRoleById(leagueInfo.getVerifiedRole_uid()), member, 4);}catch (Exception ignored) {}
            try{commandListener.removeRole(guild,guild.getRoleById(leagueInfo.getNotVerifiedRole_uid()), member, 6);}catch (Exception ignored) {}
            notifyVerificationChannel(guild, builder, leagueInfo);
        });
    }

    public static void notifyVerificationChannel(Guild guild, EmbedBuilder builder, LeagueRolesInfo leagueInfo) {
        long verificationChannel = leagueInfo.getVerificationChannel_uid();
        TextChannel channel = guild.getTextChannelById(verificationChannel);
        if(channel != null){
            channel.sendMessageEmbeds(builder.build()).queue();
        }else{
            System.out.println("Unable to send messages to verification channel, No channel is set.");
        }
    }
}
