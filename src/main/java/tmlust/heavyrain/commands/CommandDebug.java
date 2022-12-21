package tmlust.heavyrain.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tmlust.heavyrain.HeavyRain;

public class CommandDebug implements CommandExecutor {

    private HeavyRain instance;

    public CommandDebug(HeavyRain instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0) {
            sender.sendMessage("Comando debug");
            return true;
        } else {
            switch(args[0].toLowerCase()) {
                case "settimer":
                    if(args.length == 1) {
                        sender.sendMessage("Set timer");
                        return true;
                    }

                    instance.getCommands().setSecondsTimer(Long.parseLong(args[1]));
                    sender.sendMessage("Tiempo seteado. El tiempo ahora es de " + instance.getCommands().getSecondsTimer());
                    return true;
            }
        }
        return true;
    }
}
