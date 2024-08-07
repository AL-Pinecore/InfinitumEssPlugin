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

public class GarbageCollectCommand extends InfinitumCommand implements CommandExecutor {
    public GarbageCollectCommand (InfinitumEss plugin){
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        System.gc();
        if(commandSender instanceof Player)
        {
            commandSender.sendMessage(ChatColor.BLUE + config.getString("AutoGC.GCMessage"));
        }
        plugin.getLogger().info("Garbage Collect is triggered");
        return true;
    }

    public void GCTimedTask(){
        new BukkitRunnable(){
            @Override
            public void run() {
                System.gc();
                plugin.getLogger().info("Garbage Collect Done");
            }
        }.runTaskTimer(plugin, 0L, config.getInt("AutoGC.Period") * 20L);
    }
}
