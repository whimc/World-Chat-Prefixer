package edu.whimc.worldchatprefixer.commands;

import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPReload extends AbstractSubCommand {

	public WCPReload(WorldChatPrefixer plugin) {
		super(plugin);
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		plugin.reload();
		Utils.msg(sender, "&aPlugin reloaded!");
		return true;
	}

}
