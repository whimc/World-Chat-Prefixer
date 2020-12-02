package edu.whimc.worldchatprefixer.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPInfo extends AbstractSubCommand {

    public WCPInfo(WorldChatPrefixer plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0 && !(sender instanceof Player)) {
            sendUsage(sender);
            return false;
        }

        World world = ((Player) sender).getWorld();

        List<String> parsedArgs = Utils.parseArgs(args);
        if (parsedArgs.size() > 0) {
            world = Bukkit.getWorld(parsedArgs.get(0));
        }

        if (world == null) {
            Utils.msgNoWorld(sender, parsedArgs.get(0));
            return false;
        }

        String prefix = plugin.getPrefix(world);
        if (prefix == null) {
            prefix = "&7&oNone";
        } else {
            prefix = "&f\"" + prefix + "&f\"";
        }

        String format = plugin.getConfig().getString("format");
        Utils.msg(sender,
                "&eGlobal Chat Format: &f\"" + format + "&f\"",
                "&6" + world.getName() + "&e Prefix: " + prefix);
        return true;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        if (args.length > 1) return null;

        String hint = args.length == 0 ? "" : args[0];
        return Bukkit.getWorlds()
                .stream()
                .map(World::getName)
                .filter(v -> v.toLowerCase().startsWith(hint.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    private void sendUsage(CommandSender sender) {
        Utils.msg(sender, "&cIncorrect usage!", "&e/wcp &6info &e[&bworld&e]");
    }

}
