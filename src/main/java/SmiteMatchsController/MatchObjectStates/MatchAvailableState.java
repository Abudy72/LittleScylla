package SmiteMatchsController.MatchObjectStates;

import SmiteMatchsController.MatchObject;

public class MatchAvailableState implements MatchState{
    private final MatchObject matchObject;

    public MatchAvailableState(MatchObject matchObject) {
        this.matchObject = matchObject;
    }

    @Override
    public void saveMatchToDB() {
        //Code to save match details;
    }
}
