package Logic.LeagueTeamUtil;

import Logic.Dao.DivisionDao;
import Logic.Dao.LeagueTeamDao;
import Logic.Dao.Model.Division;
import Logic.Dao.Model.LeagueTeam;
import Logic.Exceptions.DivisionOwnershipException;
import Logic.Exceptions.ExceptionResponseHandler;
import Logic.Exceptions.MaxTeamSizeException;
import Logic.Exceptions.TeamNotFoundException;
import LeagueTeamUtil.UpdateTeamStates.*;
import Logic.LeagueTeamUtil.UpdateTeamStates.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class LeagueTeamUtil {
    public static EmbedBuilder createNewTeam(Guild guild, long captain, String team_name, String division) throws DivisionOwnershipException {
        EmbedBuilder builder = new EmbedBuilder();
        if(verifyDivisionOwnership(division,guild.getIdLong())){
            LeagueTeamDao dao = new LeagueTeamDao(division);
            Optional<LeagueTeam> oldTeam = dao.get(captain);
            boolean isNewTeam = oldTeam.isPresent();
            if(!isNewTeam){
                LeagueTeam newTeam = new LeagueTeam(
                        team_name, captain, division
                );
                if(dao.save(newTeam)){
                    builder.setTitle("Unable to create team");
                    builder.setColor(Color.RED);
                    builder.setDescription("Unable to create a new team in " + division + " division. " +
                            team_name + " is already taken in " + division);
                    builder.appendDescription("Perhaps you meant update team?");
                }else throw new RuntimeException("UnknownException");
            }else{
                builder.setTitle(team_name + " was created successfully!");
                builder.setColor(Color.GREEN);
                builder.setDescription("A new team was created under the division: " + division + " with captain: " + captain);
            }
            builder.setFooter(guild.getName());
            builder.setImage(guild.getIconUrl());
            return builder;
        }else throw new DivisionOwnershipException("This division is not found OR is not associated to this server\nDivisionName: " + division);
    }
    private static boolean verifyDivisionOwnership(String division, long guild_id){
        DivisionDao dao = new DivisionDao();

        Stream<Division> daoResult =
                dao.getAll().stream().filter(d -> d.getDivisionName().equals(division) && d.getGuild_id() == guild_id);
        return daoResult.findFirst().isPresent();
    }
    public static EmbedBuilder updateTeamInfo(Guild guild, long captain, String team_name, String division, PerformUpdate updateType, ArrayList<Long> additionalTeamMembers)
            throws DivisionOwnershipException {
        if(verifyDivisionOwnership(division,guild.getIdLong())){
            EmbedBuilder builder = new EmbedBuilder();
            LeagueTeamDao dao = new LeagueTeamDao(division);
            Optional<LeagueTeam> newTeamInfoOpt = dao.getAll().stream().filter(teams -> teams.getTeam_name().equals(team_name) && teams.getDivision().equals(division)).findAny();
            PerformUpdate updateLogic = null;
            LeagueTeam newTeamInfo = newTeamInfoOpt.orElse(null);

            try{
                if(newTeamInfo == null) throw new TeamNotFoundException("Team not found");
                if(updateType instanceof UpdateCaptain){
                    updateLogic = new UpdateCaptain();
                }else if(updateType instanceof UpdateCoach){
                    updateLogic = new UpdateCoach();
                } else if (updateType instanceof UpdateSub) {
                    updateLogic = new UpdateSub();
                }else if(updateType instanceof UpdateTeamMembers){
                    updateLogic = new UpdateTeamMembers(additionalTeamMembers);
                }

                assert updateLogic != null;
                try {
                    if (updateLogic.updateTeamDetails(dao, newTeamInfo, captain)) {
                        builder.setTitle(team_name + " was created updated!");
                        builder.setColor(Color.GREEN);
                        builder.setDescription("New captain: " + captain);
                    }
                }catch (MaxTeamSizeException s){
                    return ExceptionResponseHandler.handle(guild,s);
                }
            }catch (TeamNotFoundException s){
                ExceptionResponseHandler.handle(guild,s);
            }
            builder.setFooter(guild.getName());
            builder.setImage(guild.getIconUrl());
            return builder;

        }else throw new DivisionOwnershipException("This division is not found OR is not associated to this server\nDivisionName: " + division);
    }
}
