package Discord.Interface.CommandsLoader;

import Discord.Interface.CommandHandler;
import Discord.Interface.CommandsLoader.CommandsLoader;
import Discord.Interface.GuildLeaveListener;
import Logic.ConnectionPooling.Flyway.FlywayMigration;
import Logic.MatchSaverScheduler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceStarter extends ListenerAdapter {
    private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public static void start() throws LoginException, InterruptedException {
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
            builder.addEventListeners(new CommandHandler(), new GuildLeaveListener());
            JDA jda = builder.build().awaitReady();
            jda.updateCommands().addCommands(CommandsLoader.loadCommands()).queue();
        }else{
            System.out.println("Unable to find token");
        }
        //new MatchSaverScheduler().run();
        scheduler.scheduleAtFixedRate(new MatchSaverScheduler(),0L, 24L, TimeUnit.HOURS);
    }
}
