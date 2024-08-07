package cn.infinitumstudios.infinitumEss.utility;

import org.bukkit.ChatColor;

public class MessageColor {
    public static String text(String text, ChatColor color){
        return color + text + ChatColor.RESET;
    }
}
