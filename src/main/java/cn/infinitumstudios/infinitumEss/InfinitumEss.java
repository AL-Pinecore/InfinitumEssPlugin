package cn.infinitumstudios.infinitumEss;

import cn.infinitumstudios.infinitumEss.commands.CleanItemsCommand;
import cn.infinitumstudios.infinitumEss.commands.GarbageCollectCommand;
import cn.infinitumstudios.infinitumEss.commands.InfinitumEssCommand;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class InfinitumEss extends JavaPlugin implements CommandExecutor, TabCompleter, Listener {

    GarbageCollectCommand gcc;
    CleanItemsCommand cic;
    InfinitumEssCommand iec;
    MemoryAutoChecker mac;
    FileConfiguration config;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        config = getConfig();

        gcc = new GarbageCollectCommand(this);
        cic = new CleanItemsCommand(this);
        iec = new InfinitumEssCommand(this);
        mac = new MemoryAutoChecker(this);

        Objects.requireNonNull(getCommand("garbagecollect")).setExecutor(gcc);
        Objects.requireNonNull(getCommand("cleanitems")).setExecutor(cic);
        Objects.requireNonNull(getCommand("infinitumess")).setExecutor(iec);

        cic.cleanItemTimedTask();
        if (Objects.equals(config.getString("AutoGC.EnableGC"), "true")) {
            gcc.GCTimedTask();
        } else {
            this.getLogger().info("AutoGC is disabled");
            this.getLogger().info("If you want to enable AutoGC, please change EnableGC in config to true!");
        }
        if (Objects.equals(config.getString("AutoRestart.EnableAutoRestart"), "true")) {
            mac.Start();
        } else {
            this.getLogger().info("AutoRestart is disabled");
            this.getLogger().info("If you want to enable AutoRestart, please change EnableAutoRestart in config to true!");
        }

        this.getLogger().info("InfinitumEssential plugin enabled!");

    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Goodbye!");
    }

    public void reloadPlugin(CommandSender commandSender){

        this.reloadConfig();
        gcc.reloadCommand(getConfig());
        cic.reloadCommand(getConfig());
        iec.reloadCommand(getConfig());

        this.getLogger().info("InfinitumEssential plugin config file reloaded!");
        if (commandSender instanceof Player) {
            commandSender.sendMessage(ChatColor.BLUE + "[IE] 插件重载成功!");
        }
    }
}
