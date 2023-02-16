package Logic.ConnectionPooling.Flyway;


import org.flywaydb.core.Flyway;

import java.net.URI;
import java.net.URISyntaxException;

public class FlywayMigration {

    public static void migrate() {
        URI dbUri;
        try {
            dbUri = new URI(System.getenv("DATABASE_URL"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        /*String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();*/
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres","zeref","postgres").schemas("LittleMonster").load();
        flyway.migrate();
    }

    private FlywayMigration(){}
}
