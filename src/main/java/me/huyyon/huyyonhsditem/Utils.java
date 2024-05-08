package me.huyyon.huyyonhsditem;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static String s,m,h,d;
    public static String[] time = {"s","m","h","d"};
    static {
        s = Config.time_s;
        m = Config.time_m;
        h = Config.time_h;
        d = Config.time_d;
    }
    public static Boolean isTIitem(ItemStack is){
        ItemMeta meta = is.getItemMeta();
        if (!meta.hasLore()){
            return false;
        }
        List<String> lore = meta.getLore();
        for (String str : lore){
            if (str.startsWith(Config.expires_at) ||
                    str.startsWith(Config.useful_life)){
                return true;
            }
        }
        return false;
    }

    public static String getTItype(ItemStack is){
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        for (String str : lore){
            if (str.startsWith(Config.expires_at)){
                return "expire";
            }
            if (str.startsWith(Config.useful_life)){
                return "change";
            }
        }
        return null;

    }
    public static void removeTime(ItemStack is){
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        List<String> loreToAdd = new ArrayList<>();
        for (String str : lore) {
            if (!str.startsWith(Config.useful_life) && !str.startsWith(Config.expires_at)) {
                loreToAdd.add(str);
            }
        }
        meta.setLore(loreToAdd);
        is.setItemMeta(meta);
    }
    public static void addTime(ItemStack is,int t,String time){
        ItemMeta meta = is.getItemMeta();
        if (!meta.hasLore()){
            List<String> loreToAdd = new ArrayList<>();
            loreToAdd.add(Config.useful_life+"§s"+t+"§s"+Config.getConfigString("DateFormat."+time));
            meta.setLore(loreToAdd);
            is.setItemMeta(meta);
            return;
        }
        List<String> lore = meta.getLore();
        lore.add(Config.useful_life+"§s"+t+"§s"+Config.getConfigString("DateFormat."+time));
        meta.setLore(lore);
        is.setItemMeta(meta);
    }
    public static String parseTime(ItemStack is){
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        String date = null;
        int time =0;
        for (String str : lore) {
            if (str.startsWith(Config.useful_life)) {
                date = str;
            }
        }
        String dateString = date.split("§s")[2];
        if (dateString.equalsIgnoreCase(s)){
            int value = Integer.parseInt(date.split("§s")[1]);
            time += value;
        }
        if (dateString.equalsIgnoreCase(m)){
            int value = Integer.parseInt(date.split("§s")[1]);
            time += value*60;
        }
        if (dateString.equalsIgnoreCase(h)){
            int value = Integer.parseInt(date.split("§s")[1]);
            time += value*60*60;
        }
        if (dateString.equalsIgnoreCase(d)){
            int value = Integer.parseInt(date.split("§s")[1]);
            time += value*60*60*24;
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        now.setSeconds(now.getSeconds()+time);
        String result = format.format(now);
        return result;

    }
    public static Date getDate(ItemStack is){
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        String date = null;
        for (String str : lore) {
            if (str.startsWith(Config.expires_at)) {
                date = str;
            }
        }
        String dateString = date.split("§s")[1];
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date result = format.parse(dateString);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void changeItem(ItemStack is){
        String time = Utils.parseTime(is);
        ItemMeta meta = is.getItemMeta();
        List<String> lore = meta.getLore();
        List<String> loreToAdd = new ArrayList<>();
        for (String str : lore){
            if (!str.startsWith(Config.useful_life)){
                loreToAdd.add(str);
            }
        }
        loreToAdd.add(Config.expires_at+"§s"+time);
        meta.setLore(loreToAdd);
        is.setItemMeta(meta);

    }

    public static boolean isNumeric(String str) {
        try{
            Integer.parseInt(str);
            return true;
        }
        catch(NumberFormatException e) {
            return false;
        }
    }
}
