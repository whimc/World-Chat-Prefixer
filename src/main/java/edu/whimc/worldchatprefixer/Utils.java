package edu.whimc.worldchatprefixer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

	public static void msg(CommandSender sender, String... msgs) {
		for (String msg : msgs) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
		}
	}
	
	public static void msgNoPermission(CommandSender sender, String perm) {
		Utils.msg(sender, "&cYou do not have the required permission &4" + perm + "&c!");
	}
	
	public static void msgNoWorld(CommandSender sender, String name) {
		Utils.msg(sender, "&cThere is no world with the name \"&4" + name + "&c\"!");
	}
	
	public static void msgNoPrefix(CommandSender sender, String name) {
		Utils.msg(sender, "&c\"&4" + name + "&c\" does not have a prefix!");
	}
	
	
	public static void msgMustBePlayer(CommandSender sender) {
		Utils.msg(sender, "&cYou must be a player to use this command!");
	}

	public static List<String> parseArgs(String[] args) {
		String argsString = String.join(" ", args);
		Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(argsString);
		
		List<String> res = new ArrayList<>();
		while (matcher.find())
		    res.add(matcher.group(1).replace("\"", ""));
		
		return res;
	}
}
