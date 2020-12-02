package edu.whimc.worldchatprefixer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import edu.whimc.worldchatprefixer.commands.WCPCommand;

public class WorldChatPrefixer extends JavaPlugin {

    private static final String CFG_FORMAT = "format";
    private static final String CFG_PREFIXES = "world-prefixes";

    private String format;
    private Map<World, String> prefixes = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(false);
        reload();
        getServer().getPluginManager().registerEvents(new WCPListener(this), this);

        WCPCommand commands = new WCPCommand(this);
        getCommand("worldchatprefixer").setExecutor(commands);
        getCommand("worldchatprefixer").setTabCompleter(commands);
    }

    public Map<World, String> getPrefixes() {
        return prefixes;
    }

    public String getPrefix(World world) {
        return prefixes.getOrDefault(world, null);
    }

    public String getFormat() {
        return format;
    }

    public void setPrefix(World world, String newPrefix) {
        prefixes.put(world, newPrefix);
        getConfig().set(CFG_PREFIXES + "." + world.getName(), newPrefix);
        saveConfig();
    }

    public void setFormat(String newFormat) {
        format = newFormat;
        getConfig().set(CFG_FORMAT, newFormat);
        saveConfig();
    }

    public void removePrefix(World world) {
        getConfig().set(CFG_PREFIXES + "." + world.getName(), null);
        saveConfig();
        prefixes.remove(world);
    }


    public void reload() {
        reloadConfig();

        format = getConfig().getString(CFG_FORMAT);

        prefixes.clear();
        List<String> invalid = new ArrayList<>();
        for (String str : getConfig().getConfigurationSection(CFG_PREFIXES).getKeys(false)) {
            World world = Bukkit.getWorld(str);
            if (world == null) {
                invalid.add(str);
                getLogger().warning("Removing prefix for \"" + str + "\" as that world does not exist!");
                continue;
            }
            prefixes.put(world, getConfig().getString(CFG_PREFIXES + "." + str));
        }

        for (String str : invalid) {
            getConfig().set(CFG_PREFIXES + "." + str, null);
        }
        saveConfig();
    }

}
