package Logic.ConnectionPooling.Flyway;


import org.flywaydb.core.Flyway;

public class FlywayMigration {

    public static void migrate() {
        String username = System.getenv("Database_USERNAME");
        String password = System.getenv("Database_PASSWORD");
        String url = System.getenv("Database_URL");

        Flyway flyway = Flyway.configure().dataSource(url,username,password).schemas("little_monster").load();
        flyway.migrate();
    }

    private FlywayMigration(){}
}
