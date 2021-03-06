package edu.whimc.worldchatprefixer.commands;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import edu.whimc.worldchatprefixer.Utils;
import edu.whimc.worldchatprefixer.WorldChatPrefixer;

public class WCPSetPrefix extends AbstractSubCommand {

    public WCPSetPrefix(WorldChatPrefixer plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        List<String> parsedArgs = Utils.parseArgs(args);

        if (parsedArgs.size() < 2) {
            sendUsage(sender);
            return false;
        }

        World world = Bukkit.getWorld(parsedArgs.get(0));
        if (world == null) {
            Utils.msgNoWorld(sender, parsedArgs.get(0));
            return false;
        }

        String prefix = String.join(" ", parsedArgs.subList(1, parsedArgs.size()));

        plugin.setPrefix(world, prefix);
        Utils.msg(sender, "&aSet the prefix of &2" + world.getName() + "&a to \"&r" + prefix + "&a\"!");
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
        Utils.msg(sender, "&cIncorrect usage!", "&e/wcp &6setprefix &e<&bworld&e> <&bprefix...&e>");
    }

}
