package jumppads.damagepoints;
import jumppads.damagepoints.command.DamagePointScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DamagePoints extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
    getCommand("gamestart").setExecutor(new DamagePointScoreboard(this));
        Bukkit.getPluginManager().registerEvents(new DamagePointScoreboard(this), this);
        //Config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
