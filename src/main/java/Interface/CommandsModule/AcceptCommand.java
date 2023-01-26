package Interface.CommandsModule;

import Interface.CommandsModule.AcceptCommandStrategies.ExistingUser;
import Interface.CommandsModule.AcceptCommandStrategies.NewUser;
import Interface.CommandsModule.AcceptCommandStrategies.VerificationStrategies;
import Logic.Dao.Dao;
import Logic.Dao.LeagueIDsDao;
import Logic.Dao.Model.LeagueIDs;
import Logic.Dao.VerifiedPlayerDao;
import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.PlayerNotFoundException;
import Logic.PlayerVerificationModule.SmitePlayer;
import Logic.PlayerVerificationModule.VerifiedPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Optional;


public class AcceptCommand extends CustomCommandListener {
    private final static String IGN = "ign";
    public final static String PC = "pc";
    public final static String PSN = "PSN";
    public final static String Xbox = "Xbox";
    private static final String PLATFORM_ID = "platform";

    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();
        Dao<VerifiedPlayer> dao = new VerifiedPlayerDao(event.getGuild().getIdLong());
        if(event.getOption(IGN) != null){
            String ign = event.getOption(IGN).getAsString();
            long memberId = event.getMember().getIdLong();
            int platformId = event.getOption(PLATFORM_ID).getAsInt();
            String platform = this.parsePlatform(platformId);
            Optional<VerifiedPlayer> existingPlayer = dao.get(memberId);
            try{
                VerifiedPlayer newPlayerData = new VerifiedPlayer(memberId,ign,platform);
                VerificationStrategies strategies = new NewUser(newPlayerData,event.getGuild().getIdLong());
                if(existingPlayer.isPresent()){
                    if(!ign.equals(existingPlayer.get().getIgn())){
                        // TODO SEND MESSAGE TO COMPLETED VERIFICATION CHANNEL
                        smurfAlert(memberId,existingPlayer.get(),event.getGuild(), newPlayerData);
                    }
                    strategies = new ExistingUser(event.getGuild().getIdLong(),newPlayerData);
                }
                strategies.verifyPlayer();
                sendPlayerDataToVerificationChannel(event.getGuild(),newPlayerData);
                event.getHook().sendMessageEmbeds(prettyMessage(event.getGuild(),ign)).queue();
            }catch (PlayerNotFoundException | RuntimeException e){
                e.printStackTrace();
                event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(),e).build()).queue();
            }
        }
    }

    private String parsePlatform(int platformId){
        if(platformId == 1){
            return PC;
        }else if(platformId == 2){
            return PSN;
        }else if(platformId == 3){
            return Xbox;
        }
        throw new RuntimeException("Unknown platform, please choose 1 for PC, 2 for PSN, 3 for Xbox");
    }
    private MessageEmbed prettyMessage(Guild guild, String ign){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.GREEN);
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle("Welcome: " + ign + ", have fun in " + guild.getName());

        return builder.build();
    }
    private void smurfAlert(long discordId,VerifiedPlayer v1, Guild guild, VerifiedPlayer v2){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("\uD83D\uDEA8 SMURF ALERT \uD83D\uDEA8");
        builder.setDescription("<@" + discordId + "> have verified with (`ign:" + v1.getIgn() +
                "` in another server and is trying to verify with `(ign:" + v2.getIgn() + ")`");
        Field field1 = new Field(v1.getIgn() + " Info:",v1.toString(),true);
        Field field2 = new Field(v2.getIgn() + " Info:", v2.getSmiteAccount().toString(), true);

        builder.setColor(Color.RED);
        builder.addField(field1);
        builder.addField(field2);

        LeagueIDsDao dao = new LeagueIDsDao();
        dao.get(guild.getIdLong()).ifPresent(leagueInfo -> {
            notifyVerificationChannel(guild, builder, leagueInfo);
        });
    }

    private static void notifyVerificationChannel(Guild guild, EmbedBuilder builder, LeagueIDs leagueInfo) {
        long verificationChannel = leagueInfo.getVerificationChannel_uid();
        TextChannel channel = guild.getTextChannelById(verificationChannel);
        if(channel != null){
            channel.sendMessageEmbeds(builder.build()).queue();
        }else{
            System.out.println("Unable to send messages to verification channel, No channel is set.");
        }
    }

    private void sendPlayerDataToVerificationChannel(Guild guild,VerifiedPlayer player){
        SmitePlayer p =player.getSmiteAccount();
        EmbedBuilder builder = new EmbedBuilder();
        LeagueIDsDao dao = new LeagueIDsDao();
        builder.setColor(Color.GREEN);
        Field pcRankedDetails = new Field("PC Ranked Details",p.pcRankedDetailsPrettyPrint(),true);
        Field consoleRankedDetails = new Field("Controller Ranked Details",p.consoleRankedDetailsPrettyPrint(),true);
        builder.addField(pcRankedDetails);
        builder.addField(consoleRankedDetails);
        builder.setDescription(player.toString());
        builder.setTitle("In-game name: " + player.getIgn());

        builder.setFooter(guild.getName(),guild.getIconUrl());

        dao.get(guild.getIdLong()).ifPresent(leagueInfo -> {
            notifyVerificationChannel(guild, builder, leagueInfo);
        });
    }
}
