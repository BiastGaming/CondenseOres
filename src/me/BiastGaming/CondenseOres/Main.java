package me.BiastGaming.CondenseOres;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println( ChatColor.GREEN + "Condense Ores has enabked successfully");
        createConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String alable, String[] args) {
        if(cmd.getName().equalsIgnoreCase("condense")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("console-cant-use")));
                return true;
            }
            if(sender.hasPermission("condenseores.use")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission-error")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("not-enough-args-error")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("usage")));
                return true;
            }
            if(args.length == 1) {
                if (isInt(args[0]) == false) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("not-int-error")));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("usage")));
                    return true;
                }
                if (isInt(args[0]) == true) {
                    Player p = (Player) sender;
                    int i = Integer.parseInt(args[0]);
                    ItemStack toCheck = new ItemStack(Material.IRON_INGOT, 64 * i);
                    if (p.getInventory().contains(toCheck)) {
                        p.getInventory().remove(toCheck);
                        ItemStack ironBlocks = new ItemStack(Material.IRON_BLOCK, i / 64);
                        p.getInventory().addItem(ironBlocks);
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("unhandled-error")));
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("usage")));
                    }

                }

            }
        }


        return true;

    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public boolean isInt(String check) {
        try {
            Integer.parseInt(check);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("A User Just Incorrectly Used and Interger Value. Contact BiastGaming to Remove this error");
        }
        return false;
    }

}
