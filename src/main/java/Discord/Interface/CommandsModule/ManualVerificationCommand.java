package Discord.Interface.CommandsModule;

import Logic.Dao.Dao;
import Logic.Dao.Model.ServerVerifiedPlayer;
import Logic.Dao.ServerVerifiedPlayerDao;
import Logic.Dao.VerifiedPlayerDao;
import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.PlayerVerifiedException;
import Logic.PlayerVerificationModule.ManualVerifiedPlatform;
import Logic.PlayerVerificationModule.Platform;
import Logic.PlayerVerificationModule.VerifiedPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManualVerificationCommand extends CustomCommandListener{
    private final static String RANK = "rank";
    @Override
    public void handleCommand(SlashCommandInteractionEvent event) {
        event.deferReply().setEphemeral(true).queue();
        Dao<VerifiedPlayer> dao = new VerifiedPlayerDao(event.getGuild().getIdLong());
        try{
            String ign = event.getOption(AcceptCommand.IGN).getAsString();
            String rank = event.getOption(RANK).getAsString();
            User user = event.getOption("id").getAsUser();
            Member member = event.getOption("id").getAsMember();
            Platform platform = new ManualVerifiedPlatform();
            Dao<ServerVerifiedPlayer> serverVerificationDao = new ServerVerifiedPlayerDao(event.getGuild().getIdLong());
            Optional<VerifiedPlayer> existingPlayer = dao.get(user.getIdLong());

            if(existingPlayer.isPresent()){
                VerifiedPlayer playerData = existingPlayer.get();
                if(serverVerificationDao.get(user.getIdLong()).isPresent()){
                    event.getHook().sendMessageEmbeds(existingPlayerMessage(event.getGuild(), user,playerData)).queue();
                }else{
                    serverVerificationDao.save(
                            new ServerVerifiedPlayer(user.getIdLong(), event.getGuild().getIdLong())
                    );
                    event.getHook().sendMessageEmbeds(playerNewToServer(event.getGuild(),user, playerData)).queue();
                    VerificationUtil.sendPlayerDataToVerificationChannel(event.getGuild(),existingPlayer.get(),this,member);
                }
            }else{
                VerifiedPlayer newPlayerData = new VerifiedPlayer(user.getIdLong(),ign,platform,rankParser(rank), event.getMember().getId());
                existingPlayer = Optional.of(newPlayerData);
                dao.save(newPlayerData);
                serverVerificationDao.save(
                        new ServerVerifiedPlayer(user.getIdLong(), event.getGuild().getIdLong())
                );
                event.getHook().sendMessageEmbeds(successfulMessage(event.getGuild())).queue();
                VerificationUtil.sendPlayerDataToVerificationChannel(event.getGuild(),existingPlayer.get(), this, member);
            }


        }catch (PlayerVerifiedException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(), e).build()).queue();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(), new RuntimeException("Your input is incorrect"))
                    .build()).queue();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            event.getHook().sendMessageEmbeds(ExceptionResponseHandler.handle(event.getGuild(), new RuntimeException("Unknown Error."))
                    .build()).queue();
        }

    }
    private MessageEmbed existingPlayerMessage(Guild guild, User user, VerifiedPlayer playerData){
        EmbedBuilder builder =  new EmbedBuilder();
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle(user.getAsTag() + " was previously verified");
        builder.setDescription(playerData.toString());
        builder.setColor(Color.RED);
        return builder.build();
    }
    private MessageEmbed playerNewToServer(Guild guild, User user,VerifiedPlayer playerData){
        EmbedBuilder builder =  new EmbedBuilder();
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle(user.getAsTag() + " was previously verified with LittleMonster, as a result I'm using existing Data.");
        builder.setDescription(playerData.toString());
        builder.setColor(Color.DARK_GRAY);
        return builder.build();
    }
    private Platform.Rank rankParser(String rank) throws PlayerVerifiedException {
        rank = rank.toLowerCase();

        switch (rank) {
            case "bronze":
                return Platform.Rank.Bronze;
            case "silver":
                return Platform.Rank.Silver;
            case "gold":
                return Platform.Rank.Gold;
            case "platinum":
                return Platform.Rank.Platinum;
            case "diamond":
                return Platform.Rank.Diamond;
        }
        Pattern pattern = Pattern.compile("(grand\\s*masters*|gm)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(rank);
        if(matcher.find()){
            return Platform.Rank.GrandMaster;
        }else{
            pattern = Pattern.compile("masters*", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(rank);
            if(matcher.find()){
                return Platform.Rank.Masters;
            }else{
                throw new PlayerVerifiedException("Invalid Rank");
            }
        }
    }

    private MessageEmbed successfulMessage(Guild guild){
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.GREEN);
        builder.setFooter(guild.getName(),guild.getIconUrl());
        builder.setTitle("Player has been verified successfully.");
        return builder.build();
    }


}
