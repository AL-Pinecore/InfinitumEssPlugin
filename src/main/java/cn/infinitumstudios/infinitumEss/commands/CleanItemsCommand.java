package cn.infinitumstudios.infinitumEss.commands;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class CleanItemsCommand implements CommandExecutor {
    InfinitumEss ie;
    FileConfiguration config;
    int cleanCountdown;
    String cleanCountdownMessage;
    public CleanItemsCommand (InfinitumEss ie){
        this.ie = ie;
        this.config = ie.getConfig();

        cleanCountdown = this.config.getInt("CleanPeriod");
        cleanCountdownMessage = config.getString("CleanCountdownMessage");
    }

    public void reloadCommand(FileConfiguration config){
        this.config = config;
        cleanCountdown = this.config.getInt("CleanPeriod");
        cleanCountdownMessage = this.config.getString("CleanCountdownMessage");
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        int removedDropItems = 0;

        World world = getServer().getWorld("world");
        assert world != null;
        List<Entity> entityListWorld = world.getEntities();

        for(Entity current : entityListWorld){
            if (current instanceof Item){
                current.remove();
                removedDropItems ++;
            }
        }

        World nether = getServer().getWorld("world_nether");
        assert nether != null;
        List<Entity> entityListNether = nether.getEntities();

        for(Entity current : entityListNether){
            if (current instanceof Item){
                current.remove();
                removedDropItems ++;
            }
        }

        World theend = getServer().getWorld("world_the_end");
        assert theend != null;
        List<Entity> entityListTheEnd = theend.getEntities();

        for(Entity current : entityListTheEnd){
            if (current instanceof Item){
                current.remove();
                removedDropItems ++;
            }
        }

        int finalRemovedDropItems = removedDropItems;
        getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(Objects.requireNonNull(config.getString("CleanMessage")), finalRemovedDropItems)));

        ie.getLogger().info("All dropped items have been cleared");

        return true;

    }
    public void cleanItemTimedTask (){
        new BukkitRunnable(){
            @Override
            public void run() {
                if (cleanCountdown == 20){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 10){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 5){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 4){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 3){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 2){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 1){
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(cleanCountdownMessage, cleanCountdown)));
                }
                if (cleanCountdown == 0){

                    int removedDropItems = 0;

                    World world = getServer().getWorld("world");
                    assert world != null;
                    List<Entity> entityListWorld = world.getEntities();

                    for(Entity current : entityListWorld){
                        if (current instanceof Item){
                            current.remove();
                            removedDropItems++;
                        }
                    }

                    World nether = getServer().getWorld("world_nether");
                    assert nether != null;
                    List<Entity> entityListNether = nether.getEntities();

                    for(Entity current : entityListNether){
                        if (current instanceof Item){
                            current.remove();
                            removedDropItems++;
                        }
                    }

                    World theend = getServer().getWorld("world_the_end");
                    assert theend != null;
                    List<Entity> entityListTheEnd = theend.getEntities();

                    for(Entity current : entityListTheEnd){
                        if (current instanceof Item){
                            current.remove();
                            removedDropItems++;
                        }
                    }

                    int finalRemovedDropItems = removedDropItems;
                    getServer().getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.AQUA + String.format(Objects.requireNonNull(config.getString("CleanMessage")), finalRemovedDropItems)));
                    ie.getLogger().info("All dropped items have been cleared");

                    cleanCountdown = config.getInt("CleanPeriod");
                }
                cleanCountdown--;
            }
        }.runTaskTimer(ie,0L,20L);
    }
}
