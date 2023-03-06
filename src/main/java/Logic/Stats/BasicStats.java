package Logic.Stats;

public class BasicStats {
    private long id;
    private int kills, deaths, assists;
    private int damage_taken, damage_mitigated, player_damage;
    private int wins, losses;
    private int gold_earned;
    private int wards_placed;
    private int total_match_duration;
    private int firstBloodKills;
    private int selfHeal;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDamage_taken() {
        return damage_taken;
    }

    public void setDamage_taken(int damage_taken) {
        this.damage_taken = damage_taken;
    }

    public int getDamage_mitigated() {
        return damage_mitigated;
    }

    public void setDamage_mitigated(int damage_mitigated) {
        this.damage_mitigated = damage_mitigated;
    }

    public int getPlayer_damage() {
        return player_damage;
    }

    public void setPlayer_damage(int player_damage) {
        this.player_damage = player_damage;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGold_earned() {
        return gold_earned;
    }

    public void setGold_earned(int gold_earned) {
        this.gold_earned = gold_earned;
    }

    public int getWards_placed() {
        return wards_placed;
    }

    public void setWards_placed(int wards_placed) {
        this.wards_placed = wards_placed;
    }

    public int getTotal_match_duration() {
        return total_match_duration;
    }

    public void setTotal_match_duration(int total_match_duration) {
        this.total_match_duration = total_match_duration;
    }

    public int getFirstBloodKills() {
        return firstBloodKills;
    }

    public void setFirstBloodKills(int firstBloodKills) {
        this.firstBloodKills = firstBloodKills;
    }

    public int getSelfHeal() {
        return selfHeal;
    }

    public void setSelfHeal(int selfHeal) {
        this.selfHeal = selfHeal;
    }

    public BasicStats(int kills, int deaths, int assists, int damage_taken, int damage_mitigated,
                      int player_damage, int wins, int losses, int gold_earned, int wards_placed,
                      int total_match_duration, int firstBloodKills, int selfHeal) {
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.damage_taken = damage_taken;
        this.damage_mitigated = damage_mitigated;
        this.player_damage = player_damage;
        this.wins = wins;
        this.losses = losses;
        this.gold_earned = gold_earned;
        this.wards_placed = wards_placed;
        this.total_match_duration = total_match_duration;
        this.firstBloodKills = firstBloodKills;
        this.selfHeal = selfHeal;
    }

    public BasicStats() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
