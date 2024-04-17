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
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.event.player.*;
import org.bukkit.event.block.Action;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;


public class DamagePointScoreboard implements CommandExecutor, Listener {




    DamagePoints plugin;
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

                    if (player.isOp()) {


                        // Original code for the main scoreboard
                        ScoreboardManager manager = Bukkit.getScoreboardManager();
                        Scoreboard scoreboard = manager.getNewScoreboard();


                        Objective objective = scoreboard.registerNewObjective("Title", "dummy");
                        objective.setDisplayName(ChatColor.BLUE + "Scoreboard");
                        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            Score score = objective.getScore(ChatColor.GOLD + "Total Points:" + ChatColor.GREEN + player1.getTotalExperience());
                            score.setScore(1);
                            player1.setScoreboard(scoreboard);


                        }


                    }
                }
                return true;
            }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();




        if (action == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();




            if (clickedBlock.getType() == Material.BIRCH_BUTTON && player.isOp()) {

            } else if (clickedBlock.getType() == Material.ACACIA_BUTTON && player.isOp()) {
                // Acacia button clicked by an op player, reset points leaderboard
            }
            else if (clickedBlock.getType() == Material.OAK_BUTTON && player.isOp()) {
                // Acacia button clicked by an op player, reset points leaderboard
                resetLeaderboardMaster();
            }


        }
    }

    private void resetLeaderboardMaster() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setLevel(0);
            player.setTotalExperience(0);
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getNewScoreboard();


            Objective objective = scoreboard.registerNewObjective("Title", "dummy");
            objective.setDisplayName(ChatColor.BLUE + "Scoreboard");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            for (Player player1 : Bukkit.getOnlinePlayers()) {
                Score score = objective.getScore(ChatColor.GOLD + "Total Points:" + ChatColor.GREEN + player.getTotalExperience());
                score.setScore(1);
                player1.setScoreboard(scoreboard);


            }
        }
    }



}
