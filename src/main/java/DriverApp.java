import ConnectionPooling.Flyway.FlywayMigration;
import Exceptions.DivisionOwnershipException;
import Exceptions.MatchSavedException;
import SmiteMatchsController.MatchObject;

public class DriverApp {
    public static void main(String[] args) throws MatchSavedException, DivisionOwnershipException {
        FlywayMigration.migrate();
        MatchObject matchObject = new MatchObject(1283859302);

        matchObject.saveMatchToDB(699872278581608548L,1283859302,699872278581608548L,"Challenger1");

        // TODO CHECKPOINT DIVISION DAO
    }
}
