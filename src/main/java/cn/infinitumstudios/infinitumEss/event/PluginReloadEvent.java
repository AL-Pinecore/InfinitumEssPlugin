package cn.infinitumstudios.infinitumEss.event;

import cn.infinitumstudios.infinitumEss.InfinitumEss;
import cn.infinitumstudios.infinitumEss.event.utility.Event;
import cn.infinitumstudios.infinitumEss.event.utility.EventFactory;

public interface PluginReloadEvent {
    void onCallback(InfinitumEss plugin);

    Event<PluginReloadEvent> EVENT = EventFactory.createArrayBacked(PluginReloadEvent.class, (listeners) -> (plugin) -> {
        for (PluginReloadEvent event : listeners) {
            event.onCallback(plugin);
        }
    });
}
