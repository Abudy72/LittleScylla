package Logic.Stats;

import org.jetbrains.annotations.NotNull;

public class AverageStatsCalculator{
    public static BasicStats calculateStatsPerGame(BasicStats stats){
        int totalGames = stats.getWins() + stats.getLosses();

        return AverageCalculator(stats, totalGames);
    }

    public static BasicStats calculateStatsPerMin(BasicStats stats){
        int totalTime = stats.getTotal_match_duration() / 60;

        return AverageCalculator(stats, totalTime);
    }

    @NotNull
    private static BasicStats AverageCalculator(BasicStats stats, int totalTime) {
        BasicStats statsPerGame = new BasicStats();

        if(totalTime > 0){
            statsPerGame.setKills(stats.getKills() / totalTime);
            statsPerGame.setDeaths(stats.getDeaths() / totalTime);
            statsPerGame.setAssists(stats.getAssists() / totalTime);
            statsPerGame.setDamage_taken(stats.getDamage_taken() / totalTime);
            statsPerGame.setDamage_mitigated(stats.getDamage_mitigated() / totalTime);
            statsPerGame.setPlayer_damage(stats.getPlayer_damage() / totalTime);
            statsPerGame.setGold_earned(stats.getGold_earned() / totalTime);
            statsPerGame.setWards_placed(stats.getWards_placed() / totalTime);
            statsPerGame.setFirstBloodKills(stats.getFirstBloodKills() / totalTime);
            statsPerGame.setSelfHeal(stats.getSelfHeal() / totalTime);
        }
        return statsPerGame;
    }
}
