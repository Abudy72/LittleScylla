package Dao.Model;

public class Division {
    private final String divisionName;
    private final long guiild_id;

    public Division(String divisionName, long guiild_id) {
        this.divisionName = divisionName;
        this.guiild_id = guiild_id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public long getGuiild_id() {
        return guiild_id;
    }
}
