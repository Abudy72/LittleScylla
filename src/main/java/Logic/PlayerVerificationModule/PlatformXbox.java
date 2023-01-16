package Logic.PlayerVerificationModule;

public class PlatformXbox extends Platform {
    @Override
    SupportedPlatforms getPlatform() {
        return SupportedPlatforms.Xbox;
    }
    @Override
    public String toString() {
        return SupportedPlatforms.Xbox.toString();
    }
}
