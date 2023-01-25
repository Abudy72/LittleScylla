package Interface.CommandsModule;

import Interface.CommandsModule.AcceptCommandStrategies.ExistingUser;
import Interface.CommandsModule.AcceptCommandStrategies.NewUser;
import Interface.CommandsModule.AcceptCommandStrategies.VerificationStrategies;
import Logic.Dao.Dao;
import Logic.Dao.LeagueIDsDao;
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
                VerificationStrategies strategies = new NewUser(newPlayerData,event.getIdLong());
                if(existingPlayer.isPresent()){
                    if(!ign.equals(existingPlayer.get().getIgn())){
                        // TODO SEND MESSAGE TO COMPLETED VERIFICATION CHANNEL
                        notifyServerOfSmurf(memberId,ign,newPlayerData,event.getGuild());
                        strategies = new ExistingUser(event.getIdLong(),newPlayerData);
                    }
                }

                strategies.verifyPlayer();
            }catch (PlayerNotFoundException | RuntimeException e){
                event.replyEmbeds(ExceptionResponseHandler.handle(event.getGuild(),e).build()).queue();
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
        builder.setTitle("Welcome: " + ign + ". Please choose a platform!");

        return builder.build();
    }
    private void notifyServerOfSmurf(long discordId,String ign, VerifiedPlayer player, Guild guild){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(ign + " Might be a smurf.");
        builder.setDescription("<@" + discordId + "> have verified with " + player.getIgn() +
                " in another server and is trying to verify with " + ign + " in " + guild.getName());
        Field field1 = new Field(ign + " stats:","VALUE",false);
        Field field2 = new Field(player.getIgn()+ " stats:","VALUE",false);

        builder.setColor(Color.RED);
        builder.addField(field1);
        builder.addField(field2);

        LeagueIDsDao dao = new LeagueIDsDao();
        dao.get(guild.getIdLong()).ifPresent(leagueInfo -> {
            long verificationChannel = leagueInfo.getVerificationChannel_uid();
            TextChannel channel = guild.getTextChannelById(verificationChannel);
            if(channel != null){
                channel.sendMessageEmbeds(builder.build()).queue();
            }else{
                System.out.println("Unable to send messages to verification channel, No channel is set.");
            }
        });
    }

    private MessageEmbed printPlayerDataIntoMessage(SmitePlayer p){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.GREEN);
        String statement = "Conquest: " + p.getRankedConquest().getHi_rezMMR() + "\nJoust: "
                + p.getRankedJoust().getHi_rezMMR() + "\nDuel: " + p.getRankedDuel().getHi_rezMMR();
        Field pcRankedDetails = new Field("PC Ranked Details",statement,true);
    }
}
