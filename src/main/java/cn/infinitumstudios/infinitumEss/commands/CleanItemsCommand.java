package cn.infinitumstudios.infinitumEss.commands;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class CleanItemsCommand extends InfinitumCommand implements CommandExecutor {
    int cleanCountdown;
    String cleanCountdownMessage;
    public CleanItemsCommand (InfinitumEss ie){
        super(ie);

        cleanCountdown = this.config.getInt("CleanPeriod");
        cleanCountdownMessage = config.getString("CleanCountdownMessage");
    }

    @Override
    public void reloadCommand(InfinitumEss plugin){
        super.reloadCommand(plugin);

        cleanCountdown = this.config.getInt("CleanPeriod");
        cleanCountdownMessage = this.config.getString("CleanCountdownMessage");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        clearItems();
        return true;
    }

    public void cleanItemTimedTask (){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (cleanCountdown == 20) {
                    InfinitumEss.broadcastMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown));
                } else if (cleanCountdown == 10) {
                    InfinitumEss.broadcastMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown));
                } else if (cleanCountdown <= 5 && cleanCountdown > 0) {
                    InfinitumEss.broadcastMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown));
                } else if (cleanCountdown == 0) {
                    clearItems();
                    cleanCountdown = config.getInt("CleanPeriod");
                }

                cleanCountdown--;
            }
        }.runTaskTimer(plugin,0L,20L);
    }

    public void clearItems(){
        int removedDropItems = 0;

        World world = getServer().getWorld("world"), nether = getServer().getWorld("world_nether"), theend = getServer().getWorld("world_the_end");

        if(world != null) {
            List<Entity> entityListWorld = world.getEntities();

            for (Entity current : entityListWorld) {
                if (current instanceof Item) {
                    if (current.getTicksLived() > 6000) {
                        ItemStack itemStack = ((Item) current).getItemStack();
                        if (itemStack.getType() != Material.SHULKER_BOX &&
                                itemStack.getType() != Material.BLACK_SHULKER_BOX &&
                                itemStack.getType() != Material.BLUE_SHULKER_BOX &&
                                itemStack.getType() != Material.BROWN_SHULKER_BOX &&
                                itemStack.getType() != Material.CYAN_SHULKER_BOX &&
                                itemStack.getType() != Material.GRAY_SHULKER_BOX &&
                                itemStack.getType() != Material.GREEN_SHULKER_BOX &&
                                itemStack.getType() != Material.LIGHT_BLUE_SHULKER_BOX &&
                                itemStack.getType() != Material.LIGHT_GRAY_SHULKER_BOX &&
                                itemStack.getType() != Material.LIME_SHULKER_BOX &&
                                itemStack.getType() != Material.MAGENTA_SHULKER_BOX &&
                                itemStack.getType() != Material.ORANGE_SHULKER_BOX &&
                                itemStack.getType() != Material.PINK_SHULKER_BOX &&
                                itemStack.getType() != Material.PURPLE_SHULKER_BOX &&
                                itemStack.getType() != Material.RED_SHULKER_BOX &&
                                itemStack.getType() != Material.WHITE_SHULKER_BOX &&
                                itemStack.getType() != Material.YELLOW_SHULKER_BOX) {
                            current.remove();
                            removedDropItems++;
                        }
                    }
                }
            }
        }

        if(nether != null) {
            List<Entity> entityListNether = nether.getEntities();

            for (Entity current : entityListNether) {
                if (current instanceof Item) {
                    current.remove();
                    removedDropItems++;
                }
            }
        }

        if(theend != null) {
            List<Entity> entityListTheEnd = theend.getEntities();

            for (Entity current : entityListTheEnd) {
                if (current instanceof Item) {
                    current.remove();
                    removedDropItems++;
                }
            }
        }

        int finalRemovedDropItems = removedDropItems;
        InfinitumEss.broadcastMessage(ChatColor.AQUA + String.format(Objects.requireNonNull(config.getString("CleanMessage")), finalRemovedDropItems));

        plugin.getLogger().info("All dropped items have been cleared");
    }
}
