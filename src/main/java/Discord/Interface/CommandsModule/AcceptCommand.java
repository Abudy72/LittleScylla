package Discord.Interface.CommandsModule;

import Discord.Interface.CommandsModule.AcceptCommandStrategies.ExistingUser;
import Discord.Interface.CommandsModule.AcceptCommandStrategies.NewUser;
import Discord.Interface.CommandsModule.AcceptCommandStrategies.VerificationStrategies;
import Logic.Dao.Dao;
import Logic.Dao.LeagueIDsDao;
import Logic.Dao.VerifiedPlayerDao;
import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.PlayerNotFoundException;
import Logic.Exceptions.PlayerVerifiedException;
import Logic.Exceptions.UnknownPlatformId;
import Logic.PlayerVerificationModule.VerifiedPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Optional;

import static Discord.Interface.CommandsModule.VerificationUtil.notifyVerificationChannel;
import static Discord.Interface.CommandsModule.VerificationUtil.sendPlayerDataToVerificationChannel;


public class AcceptCommand extends CustomCommandListener {
    public final static String IGN = "ign";
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
            Optional<VerifiedPlayer> existingPlayer = dao.get(memberId);
            try{
                String platform = this.parsePlatform(platformId);
                VerifiedPlayer newPlayerData = new VerifiedPlayer(memberId,ign,platform);
                VerificationStrategies strategies = new NewUser(newPlayerData,event.getGuild().getIdLong());
                if(existingPlayer.isPresent()){
                    if(!ign.equals(existingPlayer.get().getIgn())){
                        smurfAlert(memberId,existingPlayer.get(),event.getGuild(), newPlayerData);
                    }
                    strategies = new ExistingUser(event.getGuild().getIdLong(),newPlayerData);
                }
                strategies.verifyPlayer();
                sendPlayerDataToVerificationChannel(event.getGuild(),newPlayerData,this,event.getMember());
                event.getHook().sendMessageEmbeds(prettyMessage(event.getGuild(),ign)).queue();
            }catch (PlayerNotFoundException | PlayerVerifiedException | UnknownPlatformId e){
                e.printStackTrace();
                event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(),e).build()).queue();
            }catch (RuntimeException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
                event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(),new RuntimeException("Unknown error occurred")).build()).queue();
            }
        }
    }

    private String parsePlatform(int platformId) throws UnknownPlatformId {
        if(platformId == 1){
            return PC;
        }else if(platformId == 2){
            return PSN;
        }else if(platformId == 3){
            return Xbox;
        }
        throw new UnknownPlatformId("Unknown platform, please choose 1 for PC, 2 for PSN, 3 for Xbox");
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
}
