package Logic.PlayerVerificationModule;

public class ManualVerifiedPlatform extends Platform{
    @Override
    SupportedPlatforms getPlatform() {
        return SupportedPlatforms.NoPlatform;
    }

    @Override
    public String toString() {
        return "No platform";
    }
}
