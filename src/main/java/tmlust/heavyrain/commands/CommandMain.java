package tmlust.heavyrain.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tmlust.heavyrain.HeavyRain;

import java.util.ArrayList;

public class CommandMain implements CommandExecutor {

    private final String commandLabel = HeavyRain.getPlugin().getCommandLabel();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            printHelpCommands(sender);
            return true;
        } else {
            switch(args[0].toLowerCase()){
                case "startcountdown":
                    // test
                    return true;
                default:
                    printHelpCommands(sender);
                    return true;
            }
        }
    }

    private void printHelpCommands(CommandSender sender){
        if(sender instanceof Player){
            Player player = (Player) sender;
            for(String s : getListStringHelpCommands()){
                player.sendMessage(s);
            }
            player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 2);
        } else {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            for(String s : getListStringHelpCommands()){
                console.sendMessage(s);
            }
        }
    }

    private ArrayList<String> getListStringHelpCommands(){
        ArrayList<String> stringHelpCommands = new ArrayList<String>();

        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + ": " + ChatColor.GREEN + "Obten la lista de comandos.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " StartCountdown: " + ChatColor.GREEN + "Inicia la cuenta atras de la Heavy Rain");

        return stringHelpCommands;
    }
}
