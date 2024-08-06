package cn.infinitumstudios.infinitumEss.commands;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class InfinitumEssCommand implements CommandExecutor, TabCompleter {
    FileConfiguration config;
    InfinitumEss ie;

    public InfinitumEssCommand(InfinitumEss ie){
        this.ie = ie;
        this.config = ie.getConfig();

    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0)
        {
            ie.getLogger().info("Usage: /infinitumess <args>, know more command please use /infinitumess ? or /infinitumess help");
            return true;
        }
        else
        {
            switch (args[0]) {
                case "?", "help" -> {
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage(ChatColor.BLUE + "Command list:");
                        commandSender.sendMessage(ChatColor.BLUE + "help - command list");
                        commandSender.sendMessage(ChatColor.BLUE + "/gc - run Garbage Collect");
                        commandSender.sendMessage(ChatColor.BLUE + "/cleanitems - clean dropped items");

                    }
                    ie.getLogger().info("Command list:");
                    ie.getLogger().info("/help - command list");
                    ie.getLogger().info("/gc - run Garbage Collect");
                    ie.getLogger().info("/cleanitems - clean dropped items");

                    return true;
                }
                case "reload" -> {
                    ie.reloadPlugin(commandSender);
                    return true;
                }
                case "gc" -> {
                    System.gc();
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage(ChatColor.BLUE + config.getString("AutoGC.GCMessage"));
                    }
                    ie.getLogger().info("Garbage Collect is triggered");
                    return true;
                }
                case "cleanitems" -> {
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
                default -> {
                    return false;
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if(args.length == 1)
        {
            List<String> suggestions = new ArrayList<>();
            suggestions.add("gc");
            suggestions.add("cleanitems");
            suggestions.add("help");
            suggestions.add("reload");
            return suggestions;
        }
        else
        {
            return null;
        }
    }

    public void reloadCommand(FileConfiguration config){
        this.config = config;

    }
}