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
	private Map<String, String> prefixes = new HashMap<>();
	
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
	
	public Map<String, String> getPrefixes() {
		return prefixes;
	}
	
	public String getPrefix(String worldName) {
		return prefixes.getOrDefault(worldName.toLowerCase(), null);
	}
	
	public String getPrefix(String worldName, String def) {
		return prefixes.getOrDefault(worldName, def);
	}
	
	public String getFormat() {
		return format;
	}
	
	public String getWorldName(String name) {
		World world = Bukkit.getWorld(name);
		if (world == null) return null;
		return world.getName();
	}
	
	public void setPrefix(String worldName, String newPrefix) {
		prefixes.put(worldName, newPrefix);
		getConfig().set(CFG_PREFIXES + "." + worldName, newPrefix);
		saveConfig();
	}
	
	public void setFormat(String newFormat) {
		format = newFormat;
		getConfig().set(CFG_FORMAT, newFormat);
		saveConfig();
	}
	
	public void removePrefix(String worldName) {
		getConfig().set(CFG_PREFIXES + "." + worldName, null);
		saveConfig();
		prefixes.remove(worldName);
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
			prefixes.put(world.getName(), getConfig().getString(CFG_PREFIXES + "." + world.getName()));
		}
		
		for (String str : invalid) {
			getConfig().set(CFG_PREFIXES + "." + str, null);
		}
		saveConfig();
	}
	
}
