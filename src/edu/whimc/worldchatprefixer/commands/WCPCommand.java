package edu.whimc.worldchatprefixer.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPCommand implements CommandExecutor, TabCompleter {

	private static final String PERM = "worldchatprefixer.admin";
	
	private Map<String, AbstractSubCommand> subCommands = new HashMap<>();
	private static final List<String> SUB_COMMANDS = Arrays.asList(
			"info", "list", "reload", "remove", "setformat", "setprefix", "setprefixhere"
	);
	
	public WCPCommand(WorldChatPrefixer plugin) {
		subCommands.put("info", new WCPInfo(plugin));
		subCommands.put("list", new WCPList(plugin));
		subCommands.put("reload", new WCPReload(plugin));
		subCommands.put("remove", new WCPRemove(plugin));
		subCommands.put("setformat", new WCPSetFormat(plugin));
		WCPSetPrefix setPrefix = new WCPSetPrefix(plugin);
		subCommands.put("setprefix", setPrefix);
		subCommands.put("setprefixhere", new WCPSetPrefixHere(plugin, setPrefix));
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (args.length == 0) {
			return SUB_COMMANDS;
		}
		
		if (args.length == 1) {
			return SUB_COMMANDS
					.stream()
					.filter(v -> v.startsWith(args[0].toLowerCase()))
					.sorted()
					.collect(Collectors.toList());
		}
		
		AbstractSubCommand subCmd = subCommands.getOrDefault(args[0].toLowerCase(), null);
		if (subCmd == null) {
			return null;
		}
		return subCmd.tabComplete(Arrays.copyOfRange(args, 1, args.length));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission(PERM)) {
			Utils.msgNoPermission(sender, PERM);
			return false;
		}

		if (args.length == 0) {
			sendCommands(sender);
			return false;
		}

		AbstractSubCommand subCmd = subCommands.getOrDefault(args[0].toLowerCase(), null);
		if (subCmd == null) {
			Utils.msg(sender, "&cUnknown subcommand \"&4" + args[0] + "&c\"!");
			sendCommands(sender);
			return false;
		}

		return subCmd.execute(sender, Arrays.copyOfRange(args, 1, args.length));
	}

	private void sendCommands(CommandSender sender) {
		Utils.msg(sender,
				"&e/wcp &6list &8- &7List all world prefixes",
				"&e/wcp &6info &e[&bworld&e] &8- &7See current format and prefixes",
				"&e/wcp &6setformat &e<&bformat...&e> &8- &7Set the world prefix chat format",
				"&e/wcp &6setprefix &e<&bworld&e> <&bprefix...&e> &8- &7Set a world's prefix",
				"&e/wcp &6setprefixhere &e<&bprefix...&e> &8- &7Set the prefix for the world you're in",
				"&e/wcp &6remove &e<&bworld&e> &8- &7Delete a world's prefix",
				"&e/wcp &6reload &8- &7Reload the plugin config");
	}

}
