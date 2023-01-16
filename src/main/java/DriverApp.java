import Logic.ConnectionPooling.Flyway.FlywayMigration;

public class DriverApp {
    public static void main(String[] args){
        FlywayMigration.migrate();
    }
}
