package edu.whimc.worldchatprefixer.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPSetFormat extends AbstractSubCommand {

	public WCPSetFormat(WorldChatPrefixer plugin) {
		super(plugin);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sendUsage(sender);
			return false;
		}
		
		List<String> parsedArgs = Utils.parseArgs(args);
		String format = String.join(" ", parsedArgs);
		plugin.setFormat(format);
		Utils.msg(sender, "&aNew format set as \"&r" + format + "&a\"!");
		return true;
	}
	
	private void sendUsage(CommandSender sender) {
		Utils.msg(sender, "&cIncorrect usage!",
				"&eUse &6$prefix$&e to represent the world's prefix",
				"&eUse &6$chat$&e to represent the rest of chat",
				"&e/wcp &6setformat &e<&bformat...&e>");
	}

}
