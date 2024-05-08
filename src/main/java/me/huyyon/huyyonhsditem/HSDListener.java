package me.huyyon.huyyonhsditem;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Date;


public class HSDListener implements Listener {
    BukkitScheduler server = Bukkit.getServer().getScheduler();
    @EventHandler (ignoreCancelled = true)
    public void onClick(InventoryClickEvent e){
        if (e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR){
            return;
        }
        ItemStack is = e.getCurrentItem();
        Player p = (Player)e.getWhoClicked();
        checkItem(is,p,Config.on_click,false);


    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            return;
        }
        if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
            return;
        }
        ItemStack is = e.getItem();
        Player p = e.getPlayer();
        checkItem(is, p, true,false);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        server.scheduleSyncDelayedTask(Main.getInstance(),() -> {
            PlayerInventory inv = e.getPlayer().getInventory();
            for (int i=0;i<inv.getSize();i++){
                ItemStack is = inv.getItem(i);
                if (is !=null && is.getType() != Material.AIR){
                    checkItem(is,e.getPlayer(),true,true);
                }

            }

        });



    }

    @EventHandler (ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e){
        if (!(e.getEntity() instanceof Player)){
            return;
        }
        Player p = (Player) e.getEntity();
        if (p.getGameMode().equals(GameMode.CREATIVE)){
            return;
        }
        PlayerInventory inv = p.getInventory();
        ItemStack[] armors = inv.getArmorContents();
        for (ItemStack is : armors){
            if (is != null && is.getType() != Material.AIR){
                checkItem(is,p,true,true);
            }

        }


    }
    public void checkItem(ItemStack is, Player p,Boolean b,Boolean armor ){
        if (!Utils.isTIitem(is)){
            return;
        }
        String type = Utils.getTItype(is);
        switch (type) {
            case "expire":
                Date date = new Date();
                if (Utils.getDate(is).before(date)) {
                    playSound(p);
                    is.setAmount(0);
                    if (armor && Config.reset_player_health_if_armor){
                        p.setMaxHealth(20);
                    }

                    p.sendMessage(Config.prefix + Config.expire);
                }
                return;
            case "change":
                if (!b){
                    return;
                }
                Utils.changeItem(is);
        }

    }
    public void playSound(Player p){
        if (Config.Sounds){
            p.playSound(p.getLocation(),Sounds.BLAZE_DEATH.bukkitSound(),10,1.2F);
        }

    }



}