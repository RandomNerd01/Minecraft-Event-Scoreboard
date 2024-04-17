package jumppads.damagepoints.command;




import jumppads.damagepoints.DamagePoints;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.event.player.*;
import org.bukkit.event.block.Action;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class DamagePointScoreboard implements CommandExecutor, Listener {




    DamagePoints plugin;
    private int time = 0; // Define time as instance variable
    private BukkitRunnable timerTask;
    boolean timerRunning = false;
    ArrayList<Player> playerlist=  new ArrayList<Player>();
    double pointsTrueMaster;
    Scoreboard mainScoreboard; // Added a field to store the main scoreboard




    public DamagePointScoreboard(DamagePoints plugin) {
        this.plugin = plugin;
        this.mainScoreboard = Bukkit.getScoreboardManager().getNewScoreboard(); // Initialize the main scoreboard
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location block = player.getLocation();
            block.setY(block.getY() - 1);

            if (player.isOp() && !timerRunning) {
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    player1.setLevel(0);
                }

                // Original code for the main scoreboard
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();


                Objective objective = scoreboard.registerNewObjective("Title", "dummy");
                objective.setDisplayName(ChatColor.BLUE + "Game Scoreboard");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                for (Player player1 : Bukkit.getOnlinePlayers()) {
                    Score score = objective.getScore(ChatColor.GOLD + "Game Points:" + ChatColor.GREEN + player1.getLevel());
                    score.setScore(1);
                    player1.setScoreboard(scoreboard);
                }

                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    // Code to execute after 15 minutes
                    player.sendMessage(ChatColor.GREEN + "Game has Ended!");
                    timerRunning = false; // Reset the timer flag
                    updateTopExp(player);
                }, 20 * 60 * 15); // 20 ticks per second, 60 seconds per minute, 15 minutes
                timerRunning = true; // Set the timer flag
                player.sendMessage(ChatColor.GREEN + "15-minute timer started.");
            } else {
                player.sendMessage(ChatColor.RED + "Timer is already running or you do not have permission.");
            }
        }
        return true;
    }





    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective("Title");

        if (objective != null) {
            // Update the score based on the player's new experience level
            int newExpLevel = player.getLevel();
            scoreboard.resetScores(ChatColor.GOLD + "Game Points:" + ChatColor.GREEN + (newExpLevel - 1)); // Remove previous score
            Score newScore = objective.getScore(ChatColor.GOLD + "Game Points:" + ChatColor.GREEN + newExpLevel); // Add new score
            newScore.setScore(1);
        }
    }

    public void updateTopExp(Player player) {
        List<Player> sortedPlayers = Bukkit.getOnlinePlayers().stream()
                .sorted(Comparator.comparingInt(Player::getLevel).reversed())
                .collect(Collectors.toList());

        player.sendMessage(ChatColor.YELLOW + "Top 5 Players with Highest Experience Levels:");

        for (int i = 0; i < 5 && i < sortedPlayers.size(); i++) {
            Player p = sortedPlayers.get(i);
            Bukkit.broadcastMessage((i + 1) + ") " + p.getName() + ": " + p.getLevel());
        }
    }



}
