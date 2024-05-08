package me.huyyon.huyyonhsditem;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    CommandSender cs = Bukkit.getConsoleSender();
    private static Main instance;
    @Override
    public void onEnable(){
        getLogger().info("§bPlugins load completed!");
        Bukkit.getServer().getPluginManager().registerEvents(new HSDListener(),this);
        getCommand("hansudung").setExecutor(new HSDCommands());
        new MetricsLite(this);
        saveDefaultConfig();
        instance = this;
    }

    public static Main getInstance(){
        return instance;
    }

}