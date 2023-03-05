import Discord.Interface.CommandsLoader.ServiceStarter;

import javax.security.auth.login.LoginException;

public class DriverApp {
    public static void main(String[] args) throws LoginException, InterruptedException {
        ServiceStarter.start();
    }
}
