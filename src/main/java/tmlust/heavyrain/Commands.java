package tmlust.heavyrain;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.utils.Utility;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Commands implements CommandExecutor {

    private final HeavyRain plugin;
    private final String commandLabel;
    private final Config config;

    public Commands(HeavyRain plugin){
        this.plugin = plugin;
        commandLabel = plugin.getCommandLabel();
        config = plugin.getConfigFile();
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if(args.length == 0){
            printHelpCommands(sender);
            return true;
        } else {
            switch(args[0].toLowerCase()){
                case "enable":
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.YELLOW + "Ingresa " + ChatColor.DARK_GREEN + "on" + ChatColor.YELLOW + " para activar el contador, " +
                        "Ingresa " + ChatColor.DARK_RED + "off" + ChatColor.YELLOW + " para desactivar el contador");
                        return true;
                    }
                    switch(args[1].toLowerCase()){
                        case "on":
                            if(!plugin.getData().isTimerEnabled()) {
                                plugin.getData().setTimerEnabled(true);
                                plugin.getThreadManager().startTimer();
                                sender.sendMessage(ChatColor.GREEN + "El contador de la Heavy Rain ha sido iniciado");
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "El contador ya ha sido iniciado");
                            }
                            return true;
                        case "off":
                            if(plugin.getData().isTimerEnabled()) {
                                plugin.getData().setTimerEnabled(false);
                                plugin.getThreadManager().stopTimer();
                                sender.sendMessage(ChatColor.GREEN + "El contador de la Heavy Rain ha sido detenido");
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "El contador ya ha sido detenido");
                            }
                            return true;
                        default:
                            printHelpCommands(sender);
                            return true;
                    }
                case "reload":
                    config.reload(sender);
                    return true;
                case "config":
                    if(args.length == 1) {
                        printConfigCommands(sender);
                        return true;
                    }
                    switch(args[1].toLowerCase()) {
                        case "info":
                            String format;
                            sender.sendMessage(ChatColor.DARK_GREEN + "=========================================");
                            if(plugin.getData().isHeavyRainActivated())
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&c&lHEAVY RAIN EN CURSO\n"));

                            if(plugin.getData().isTimerEnabled()) {
                                sender.sendMessage(ChatColor.GREEN + "Temporizador Heavy Rain:" + ChatColor.DARK_GREEN + " Activado");
                            } else {
                                sender.sendMessage(ChatColor.GREEN + "Temporizador Heavy Rain:" + ChatColor.DARK_RED + " Desactivado");
                            }

                            format = String.format("%02dh %02dm %02ds", plugin.getData().getSecondsMax() / 3600, (plugin.getData().getSecondsMax() % 3600) / 60, plugin.getData().getSecondsMax() % 60);
                            sender.sendMessage(ChatColor.GREEN + "La Heavy Rain se activa cada " + ChatColor.YELLOW + format);

                            if(plugin.getData().isHeavyRainActivated()) {
                                long secondsRemaining = 720 - plugin.getData().getSecondsTimer();
                                format = String.format("%02dh %02dm %02ds", secondsRemaining / 3600, (secondsRemaining % 3600) / 60, secondsRemaining % 60);
                                sender.sendMessage(ChatColor.DARK_RED + "La Heavy Rain finaliza en " + ChatColor.YELLOW + format);
                            } else {
                                long secondsRemaining = plugin.getData().getSecondsMax() - plugin.getData().getSecondsTimer();
                                format = String.format("%02dh %02dm %02ds", secondsRemaining / 3600, (secondsRemaining % 3600) / 60, secondsRemaining % 60);
                                sender.sendMessage(ChatColor.GREEN + "La Heavy Rain iniciara en " + ChatColor.YELLOW + format);
                            }
                            sender.sendMessage(ChatColor.DARK_GREEN + "=========================================");
                            return true;
                        case "each":
                            if(args.length == 2) {
                                sender.sendMessage(ChatColor.YELLOW + "Ingresa el tiempo en segundos entre cada evento Heavy Rain");
                                return true;
                            }
                            if(Utility.isOnlyNumbers(args[2].toCharArray())){
                                if(new BigDecimal(args[2]).compareTo(new BigDecimal(9223372036854775807L)) <= 0 &&
                                        new BigDecimal(args[2]).compareTo(new BigDecimal(3600)) > 0) {
                                    plugin.getData().setSecondsMax(Long.parseLong(args[2]));
                                    format = String.format("%02dh %02dm %02ds", plugin.getData().getSecondsMax() / 3600, (plugin.getData().getSecondsMax() % 3600) / 60, plugin.getData().getSecondsMax() % 60);
                                    sender.sendMessage(ChatColor.GREEN + "Tiempo configurado a cada " + format + ".");
                                } else if(new BigDecimal(args[2]).compareTo(new BigDecimal(3600)) <= 0) {
                                    sender.sendMessage(ChatColor.DARK_RED + "Ingresa un valor mayor a 3600");
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar numeros muy grandes");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar letras");
                            }
                            return true;
                        case "time":
                            if(args.length == 2) {
                                sender.sendMessage(ChatColor.YELLOW + "Cambia el tiempo transcurrido a la proxima Heavy Rain");
                                return true;
                            }
                            if(Integer.parseInt(args[2]) >= 0 && Integer.parseInt(args[2]) <= plugin.getData().getSecondsMax()) {
                                plugin.getData().setSecondsTimer(Long.parseLong(args[2]));

                                long secondsRemaining = plugin.getData().getSecondsMax() - plugin.getData().getSecondsTimer();
                                format = String.format("%02dh %02dm %02ds", secondsRemaining / 3600, (secondsRemaining % 3600) / 60, secondsRemaining % 60);

                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&aTiempo cambiado, ahora faltan &b" + format + "&a para iniciar la Heavy Rain"));
                            } else {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cNo puedes ingresar un valor mayor a " + plugin.getData().getSecondsMax() + " o menor a 0"));
                            }
                            return true;
                        default:
                            printConfigCommands(sender);
                            return true;
                    }
                case "give":
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&eIngresa el nombre del objeto"));
                        return true;
                    } else {
                        Player p = (Player) sender;
                        switch(args[1].toLowerCase()) {
                            case "omnifire":
                                p.getInventory().addItem(plugin.getItems().OmniFire);
                                return true;
                            case "shardoffire":
                                p.getInventory().addItem(plugin.getItems().ShardOfFire);
                                return true;
                            case "writinglife":
                                p.getInventory().addItem(plugin.getItems().WritingLife);
                                return true;
                            case "arcanebooklife":
                                p.getInventory().addItem(plugin.getItems().ArcaneBookLife);
                                return true;
                            case "kingslimecore":
                                p.getInventory().addItem(plugin.getItems().KingSlimeCore);
                                return true;
                            default:
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cNo existe ese objeto"));
                        }
                    }
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

    private void printConfigCommands(CommandSender sender){
        if(sender instanceof Player){
            Player player = (Player) sender;
            for(String s : getListStringConfigHelpCommands()){
                player.sendMessage(s);
            }
            player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 2);
        } else {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            for(String s : getListStringConfigHelpCommands()){
                console.sendMessage(s);
            }
        }
    }

    private List<String> getListStringHelpCommands(){
        List<String> stringHelpCommands = new ArrayList<>();

        stringHelpCommands.add(ChatColor.DARK_GREEN + "=========================================");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + ": " + ChatColor.GREEN + "Obten la lista de comandos.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " reload: " + ChatColor.GREEN + "Recarga las configuraciones.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " enable (on/off): " + ChatColor.GREEN + "Inicia la cuenta atras de la Heavy Rain.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config: " + ChatColor.GREEN + "Obten la lista de comandos de configuracion");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "=========================================");

        return stringHelpCommands;
    }

    private List<String> getListStringConfigHelpCommands() {
        List<String> stringConfigCommands = new ArrayList<>();

        stringConfigCommands.add(ChatColor.DARK_GREEN + "=========================================");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config info: " + ChatColor.GREEN + "Obten informacion de la configuracion del plugin");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config each (seconds): " + ChatColor.GREEN + "Cambia el tiempo entre cada evento de Heavy Rain");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config time (seconds): " + ChatColor.GREEN + "Cambia el tiempo transcurrido a la proxima Heavy Rain");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "=========================================");

        return stringConfigCommands;
    }

}
