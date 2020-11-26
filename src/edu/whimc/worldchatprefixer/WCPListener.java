package edu.whimc.worldchatprefixer;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class WCPListener implements Listener {

	private WorldChatPrefixer plugin;
	
	public WCPListener(WorldChatPrefixer plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		String prefix = plugin.getPrefix(event.getPlayer().getWorld().getName());
		if (prefix == null) return;
		
		String format = ChatColor.translateAlternateColorCodes('&',
				plugin.getFormat().replace("$prefix$", prefix).replace("$chat$", event.getFormat()));
		event.setFormat(format);
	}
	
}
