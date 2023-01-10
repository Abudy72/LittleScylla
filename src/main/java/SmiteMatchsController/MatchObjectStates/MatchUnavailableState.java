package SmiteMatchsController.MatchObjectStates;

import Exceptions.MatchUnavailableException;

public class MatchUnavailableState implements MatchState{
    private final String publicDate;

    public MatchUnavailableState(String publicDate) {
        this.publicDate = publicDate;
    }

    @Override
    public void saveMatchToDB() {
        throw new MatchUnavailableException("Match is still hidden. Match is scheduled to be processed shortly 7 days after" + publicDate);
    }
}
