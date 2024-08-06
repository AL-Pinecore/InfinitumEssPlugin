package cn.infinitumstudios.infinitumEss.commands;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class GarbageCollectCommand implements CommandExecutor {
    InfinitumEss ie;
    FileConfiguration config;
    Runtime runtime;
    public GarbageCollectCommand (InfinitumEss ie){
        this.ie = ie;
        this.config = ie.getConfig();
    }

    public void reloadCommand(FileConfiguration config){
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        System.gc();
        if(commandSender instanceof Player)
        {
            commandSender.sendMessage(ChatColor.BLUE + config.getString("AutoGC.GCMessage"));
        }
        ie.getLogger().info("Garbage Collect is triggered");
        return true;
    }

    public void GCTimedTask(){
        new BukkitRunnable(){
            @Override
            public void run() {
                System.gc();
                ie.getLogger().info("Garbage Collect Done");
            }
        }.runTaskTimer(ie, 0L, config.getInt("AutoGC.Period") * 20L);
    }
}
