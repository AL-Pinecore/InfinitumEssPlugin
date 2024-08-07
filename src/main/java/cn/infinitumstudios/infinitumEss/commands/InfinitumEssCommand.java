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

public class InfinitumEssCommand extends InfinitumCommand implements CommandExecutor, TabCompleter {
    public InfinitumEssCommand(InfinitumEss ie){
        super(ie);
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0)
        {
            plugin.getLogger().info("Usage: /infinitumess <args>, know more command please use /infinitumess ? or /infinitumess help");
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
                    plugin.getLogger().info("Command list:");
                    plugin.getLogger().info("/help - command list");
                    plugin.getLogger().info("/gc - run Garbage Collect");
                    plugin.getLogger().info("/cleanitems - clean dropped items");

                    return true;
                }
                case "reload" -> {
                    plugin.reloadPlugin(commandSender);
                    return true;
                }
                case "gc" -> {
                    System.gc();
                    if (commandSender instanceof Player) {
                        commandSender.sendMessage(ChatColor.BLUE + config.getString("AutoGC.GCMessage"));
                    }
                    plugin.getLogger().info("Garbage Collect is triggered");
                    return true;
                }
                case "cleanitems" -> {


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
            return new ArrayList<>();
        }
    }

    public void reloadCommand(FileConfiguration config){
        this.config = config;

    }
}