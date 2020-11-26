package edu.whimc.worldchatprefixer.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPRemove extends AbstractSubCommand {

	public WCPRemove(WorldChatPrefixer plugin) {
		super(plugin);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sendUsage(sender);
			return false;
		}

		List<String> parsedArgs = Utils.parseArgs(args);
		String world = plugin.getWorldName(parsedArgs.get(0));

		if (!plugin.getPrefixes().containsKey(world)) {
			Utils.msgNoPrefix(sender, parsedArgs.get(0));
			return false;
		}
		
		plugin.removePrefix(world);
		Utils.msg(sender, "&aRemoved the prefix for &2" + world + "&a!");
		return true;
	}
	
	@Override
	public List<String> tabComplete(String[] args) {
		if (args.length > 1) return null;
		
		String hint = args.length == 0 ? "" : args[0];
		return plugin.getPrefixes().keySet()
				.stream()
				.filter(v -> v.toLowerCase().startsWith(hint.toLowerCase()))
				.sorted()
				.collect(Collectors.toList());
	}

	private void sendUsage(CommandSender sender) {
		Utils.msg(sender, "&cIncorrect usage!", "&e/wcp &6remove &e<&bworld&e>");
	}

}
