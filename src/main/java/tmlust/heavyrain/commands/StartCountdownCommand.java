package tmlust.heavyrain.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCountdownCommand {

    public static void execute(CommandSender sender){
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("In progress...");
        }
    }
}
