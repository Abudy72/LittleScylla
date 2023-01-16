package Logic.PlayerVerificationModule;

public class PlatformPSN extends Platform {
    @Override
    SupportedPlatforms getPlatform() {
        return SupportedPlatforms.PSN;
    }
    @Override
    public String toString() {
        return SupportedPlatforms.PSN.toString();
    }
}
