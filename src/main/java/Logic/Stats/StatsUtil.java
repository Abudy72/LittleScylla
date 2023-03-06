package Logic.Stats;

import Logic.Dao.BasicStatsDao;
import Logic.Exceptions.PlayerNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class StatsUtil {
    protected static final String stats_logo = "https://w7.pngwing.com/pngs/59/134/png-transparent-chart-computer-icons-statistics-report-elevator-repair-miscellaneous-angle-text.png";
    protected static BasicStats fetchPlayerData(long discordId, String ign, String division) throws PlayerNotFoundException {
        BasicStatsDao dao = new BasicStatsDao();
        List<BasicStats> statsSummary = new ArrayList<>();
        BasicStats result = new BasicStats();

        if(discordId > 0){
            statsSummary = dao.getStatsById(discordId,division);
        }else{
            if (ign != null) {
                statsSummary = dao.getStatsByIGN(ign,division);
            }
        }

        if(!statsSummary.isEmpty()){
            for(BasicStats s: statsSummary){
                result.setKills(result.getKills() + s.getKills());
                result.setDeaths(result.getDeaths()+ s.getDeaths()) ;
                result.setAssists(result.getAssists()+ s.getAssists());
                result.setDamage_taken(result.getDamage_taken()+ s.getDamage_taken());
                result.setDamage_mitigated(result.getDamage_mitigated()+ s.getDamage_mitigated());
                result.setPlayer_damage(result.getPlayer_damage() + s.getPlayer_damage());
                result.setGold_earned(result.getGold_earned()+ s.getGold_earned());
                result.setWards_placed(result.getWards_placed() + s.getWards_placed());
                result.setFirstBloodKills(result.getFirstBloodKills() + s.getFirstBloodKills());
                result.setSelfHeal(result.getSelfHeal() + s.getSelfHeal());
                result.setWins(result.getWins() + s.getWins());
                result.setLosses(result.getLosses() + s.getLosses());
            }
        }else {
            throw new PlayerNotFoundException("No stats are available for this player");
        }
        return result;
    }

    protected static String statsProcessor(BasicStats stats){
        return "**Kills: " + stats.getKills() +
                "\nDeaths: " + stats.getDeaths() +
                "\nAssists: " + stats.getAssists() +
                "\nPlayer Damage: " + stats.getPlayer_damage() +
                "\nDamage Mitigated: " + stats.getDamage_mitigated() +
                "\nFirst Blood Kills:" + stats.getFirstBloodKills() +
                "\nGold Earned: " + stats.getGold_earned() +
                "\nWards placed:" + stats.getWards_placed() + "**";
    }
}
