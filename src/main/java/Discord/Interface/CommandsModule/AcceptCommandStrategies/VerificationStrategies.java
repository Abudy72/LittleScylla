package Discord.Interface.CommandsModule.AcceptCommandStrategies;

import Logic.Exceptions.PlayerVerifiedException;

public interface VerificationStrategies {
    void verifyPlayer() throws PlayerVerifiedException;
}
