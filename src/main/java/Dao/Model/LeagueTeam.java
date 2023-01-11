package Dao.Model;

import java.util.ArrayList;

public class LeagueTeam {
    public static final int MAX_TEAM_SIZE = 4;
    private final String team_name;
    private long captain;
    private final String division;
    private ArrayList<Long> team_Members = new ArrayList<>();
    private long sub;
    private long coach;

    public LeagueTeam(String teamName, long captain, String division) {
        team_name = teamName;
        this.captain = captain;
        this.division = division;
    }

    public boolean addMember(Long newMember){
        if(this.team_Members.size() <= MAX_TEAM_SIZE && !this.team_Members.contains(newMember) && newMember != null){
            this.team_Members.add(newMember);
            return true;
        }else return false;
    }

    public boolean removeMember(Long oldMember){
        return this.team_Members.remove(oldMember);
    }

    public void setSub(long sub) {
        this.sub = sub;
    }

    public void setCoach(long coach) {
        this.coach = coach;
    }

    public String getTeam_name() {
        return team_name;
    }

    public long getCaptain() {
        return captain;
    }

    public ArrayList<Long> getTeam_Members() {
        return team_Members;
    }

    public long getSub() {
        return sub;
    }

    public long getCoach() {
        return coach;
    }

    public void setCaptain(long captain) {
        this.captain = captain;
    }

    public void setTeam_Members(ArrayList<Long> team_Members) {
        this.team_Members = team_Members;
    }

    public String getDivision() {
        return division;
    }
}
