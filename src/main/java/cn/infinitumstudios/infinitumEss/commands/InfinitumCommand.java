package cn.infinitumstudios.infinitumEss.commands;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import cn.infinitumstudios.infinitumEss.event.PluginReloadEvent;
import org.bukkit.configuration.file.FileConfiguration;

public class InfinitumCommand {
    protected InfinitumEss plugin;
    protected FileConfiguration config;

    public InfinitumCommand(final InfinitumEss plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();

        PluginReloadEvent.EVENT.register(this::reloadCommand);
    }

    public void reloadCommand(InfinitumEss plugin){
        this.plugin = plugin;
        config = plugin.getConfig();
    }
}
