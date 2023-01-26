import Interface.CommandHandler;
import Interface.CommandsLoader.CommandsLoader;
import Logic.ConnectionPooling.Flyway.FlywayMigration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class DriverApp extends ListenerAdapter {
    public static void main(String[] args) throws LoginException, InterruptedException {
        FlywayMigration.migrate();
        String token = System.getenv("DISCORD_BOT_TOKEN");
        if (token != null) {
            JDABuilder builder = JDABuilder.createDefault(token);
            builder.setMemberCachePolicy(MemberCachePolicy.ALL);
            builder.setChunkingFilter(ChunkingFilter.ALL);
            builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            // Enable the bulk delete event
            builder.setBulkDeleteSplittingEnabled(false);
            // Disable compression (not recommended)
            builder.setCompression(Compression.NONE);
            //builder.setActivity(Activity.playing());
            builder.addEventListeners(new CommandHandler());
            JDA jda = builder.build().awaitReady();
            jda.updateCommands().addCommands(CommandsLoader.loadCommands()).queue();
        }else{
            System.out.println("Unable to find token");
        }
    }
}
