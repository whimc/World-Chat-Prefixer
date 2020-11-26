package edu.whimc.worldchatprefixer.commands;

import java.util.Map;

import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPList extends AbstractSubCommand {

	public WCPList(WorldChatPrefixer plugin) {
		super(plugin);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		Utils.msg(sender, "&eWorld Prefixes:");
		plugin.getPrefixes().entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.forEachOrdered(e -> Utils.msg(sender, " &6" + e.getKey() + " &7-> &f\"" + e.getValue() + "&f\""));
		return true;
	}
	
}
