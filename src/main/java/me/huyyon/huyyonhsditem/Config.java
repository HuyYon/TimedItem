package me.huyyon.huyyonhsditem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Config {
    public static String prefix,expire,expires_at,useful_life,
            time_s,time_m,time_h,time_d,invalid_usage,config_reload,time_set,no_item;
    public static boolean on_click,Sounds,reset_player_health_if_armor;
    static {
        loadConfig();
    }
    public static void loadConfig(){
        prefix = getConfigString("Messages.prefix");
        expire = getConfigString("Messages.expire");
        no_item = getConfigString("Messages.no_item");
        expires_at = getConfigString("DateFormat.expires_at");
        useful_life = getConfigString("DateFormat.useful_life");
        time_s = getConfigString("DateFormat.s");
        time_m = getConfigString("DateFormat.m");
        time_h = getConfigString("DateFormat.h");
        time_d = getConfigString("DateFormat.d");
        invalid_usage = getConfigString("Messages.invalid_usage");
        config_reload = getConfigString("Messages.config_reload");
        time_set = getConfigString("Messages.time_set");
        on_click = Main.getInstance()
                .getConfig().getBoolean("Settings.start_date_calculate_on_click");
        Sounds = Main.getInstance()
                .getConfig().getBoolean("Settings.Sounds");
        reset_player_health_if_armor = Main.getInstance()
                .getConfig().getBoolean("Settings.reset_player_health_if_armor");
    }

    public static String getConfigString(String key){
        String result = null;
        try {
            result = Main.getInstance().getConfig().getString(key);
        } catch (NullPointerException e){
            Bukkit.getLogger().warning("[TimedItem] Config corrupted! key = "+key);
        }
        return ChatColor.translateAlternateColorCodes('&',result);
    }
}