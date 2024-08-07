package cn.infinitumstudios.infinitumEss;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class MemoryAutoChecker {
    InfinitumEss ie;
    FileConfiguration config;
    Runtime runtime;
    public MemoryAutoChecker(InfinitumEss ie){
        this.ie = ie;
        this.config = ie.getConfig();
        runtime = Runtime.getRuntime();
    }
    public void Start(){
        new BukkitRunnable(){
            long usedMemory;
            long usedMemoryPercentages;
            @Override
            public void run() {
                usedMemory = runtime.totalMemory() - runtime.freeMemory();
                usedMemoryPercentages = usedMemory / runtime.maxMemory();
                if (usedMemoryPercentages > config.getLong("AutoRestart.RestartThreshold")){
                    RestartServer();
                }
            }
        }.runTaskTimer(ie, 0L, 20L);
    }

    private void RestartServer(){
        ie.getLogger().info("服务器内存占用已达设置上限，现即将自动重启!");
        InfinitumEss.broadcastMessage(ChatColor.AQUA + Objects.requireNonNull(config.getString("AutoRestart.RestartStartCountdownMessage1")));
        InfinitumEss.broadcastMessage(ChatColor.AQUA + Objects.requireNonNull(config.getString("AutoRestart.RestartStartCountdownMessage2")));
        new BukkitRunnable(){
            int countdown = 20;
            @Override
            public void run() {
                if (countdown == 20 || countdown == 10 || (countdown <= 5 && countdown > 0)){
                    InfinitumEss.broadcastMessage(ChatColor.AQUA + String.format(Objects.requireNonNull(config.getString("AutoRestart.RestartCountdownMessage")), countdown));
                } else if (countdown == 0){
                    InfinitumEss.broadcastMessage(ChatColor.AQUA + Objects.requireNonNull(config.getString("AutoRestart.RestartMessage")));
                    getServer().dispatchCommand(getServer().getConsoleSender(), "restart");
                }

                countdown--;
            }
        }.runTaskTimer(ie, 0L, 20L);

    }
}
