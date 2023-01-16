package Logic.PlayerVerificationModule;

public class PlatformPC extends Platform {
    @Override
    SupportedPlatforms getPlatform() {
        return SupportedPlatforms.PC;
    }

    @Override
    public String toString() {
        return SupportedPlatforms.PC.toString();
    }
}
