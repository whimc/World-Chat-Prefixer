package edu.whimc.worldchatprefixer.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public abstract class AbstractSubCommand {

	protected WorldChatPrefixer plugin;

	public AbstractSubCommand(WorldChatPrefixer plugin) {
		this.plugin = plugin;
	}

	public abstract boolean execute(CommandSender sender, String[] args);
	public List<String> tabComplete(String[] args) { return null; }
}
