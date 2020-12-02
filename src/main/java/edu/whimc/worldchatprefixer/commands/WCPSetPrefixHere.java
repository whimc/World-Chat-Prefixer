package edu.whimc.worldchatprefixer.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPSetPrefixHere extends AbstractSubCommand {

	private WCPSetPrefix setPrefix;
	
	public WCPSetPrefixHere(WorldChatPrefixer plugin, WCPSetPrefix setPrefix) {
		super(plugin);
		this.setPrefix = setPrefix;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			Utils.msgMustBePlayer(sender);
			return false;
		}
		
		List<String> parsedArgs = Utils.parseArgs(args);

		if (parsedArgs.size() == 0) {
			sendUsage(sender);
			return false;
		}
		
		parsedArgs.add(0, ((Player) sender).getWorld().getName());
		return setPrefix.execute(sender, parsedArgs.toArray(new String[0]));
	}

	private void sendUsage(CommandSender sender) {
		Utils.msg(sender, "&cIncorrect usage!", "&e/wcp &6setprefixhere &e<&bprefix...&e>");
	}

}
