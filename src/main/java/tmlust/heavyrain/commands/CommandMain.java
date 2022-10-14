package tmlust.heavyrain.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.files.Data;
import tmlust.heavyrain.tasks.HeavyRainTask;
import tmlust.heavyrain.utilities.Utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommandMain implements CommandExecutor {

    private final HeavyRain plugin;
    private final String commandLabel;
    private final Config config;
    BukkitScheduler scheduler = null;
    private long secondsTimer;
    private boolean CounterOn = false;
    private long secondsMax;

    public CommandMain(HeavyRain plugin){
        this.plugin = plugin;
        commandLabel = plugin.getCommandLabel();
        config = plugin.getConfigFile();
        secondsTimer = 1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            printHelpCommands(sender);
            return true;
        } else {
            switch(args[0].toLowerCase()){
                case "timer":
                    if(args.length == 1) {
                        printHelpCommands(sender);
                        return true;
                    }
                    switch(args[1].toLowerCase()){
                        case "on":
                            if(!CounterOn) {
                                CounterOn = true;
                                sender.sendMessage(ChatColor.GREEN + "El contador de la Heavy Rain ha sido iniciado");
                                startTimer();
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "El contador ya ha sido iniciado");
                            }
                            return true;
                        case "off":
                            if(CounterOn) {
                                CounterOn = false;
                                sender.sendMessage(ChatColor.GREEN + "El contador de la Heavy Rain ha sido detenido");
                                stopTimer();
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
                        case "timer":
                            if(args.length == 2) {
                                sender.sendMessage(ChatColor.YELLOW + "Ingresa el tiempo en segundos entre cada evento Heavy Rain");
                                return true;
                            }
                            if(Utility.isOnlyNumbers(args[2].toCharArray())){
                                if(new BigDecimal(args[2]).compareTo(new BigDecimal(9223372036854775807L)) <= 0) {
                                    secondsMax = Long.parseLong(args[2]);
                                    sender.sendMessage(ChatColor.GREEN + "Tiempo configurado a cada " + secondsMax + " segundos");
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar numeros muy grandes");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar letras");
                            }
                            return true;
                        case "info":
                            if(CounterOn) {
                                sender.sendMessage(ChatColor.GREEN + "Heavy Rain:" + ChatColor.DARK_GREEN + " Activado");
                            } else {
                                sender.sendMessage(ChatColor.GREEN + "Heavy Rain:" + ChatColor.DARK_RED + " Desactivado");
                            }
                            sender.sendMessage(ChatColor.GREEN + "Tiempo entre eventos: " + ChatColor.YELLOW + secondsMax);
                            return true;
                    }
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
            for(String s : getListStringConfigCommands()){
                player.sendMessage(s);
            }
            player.playSound(player, Sound.BLOCK_ANVIL_PLACE, 1, 2);
        } else {
            ConsoleCommandSender console = (ConsoleCommandSender) sender;
            for(String s : getListStringConfigCommands()){
                console.sendMessage(s);
            }
        }
    }

    public void startTimer() {

        scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            secondsTimer++;
            if(secondsTimer > secondsMax){
                BukkitTask heavyRainTask = new HeavyRainTask(plugin).runTask(plugin);
                secondsTimer = 1;
            }
        }, 0, 20);
    }

    public void stopTimer() {
        scheduler.cancelTasks(plugin);
        Data dat = new Data(CounterOn, secondsTimer, secondsMax);
        dat.saveData("heavyrain.dat");
    }

    private List<String> getListStringHelpCommands(){
        List<String> stringHelpCommands = new ArrayList<>();

        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + ": " + ChatColor.GREEN + "Obten la lista de comandos.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " reload: " + ChatColor.GREEN + "Recarga las configuraciones.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " timer (on/off): " + ChatColor.GREEN + "Inicia la cuenta atras de la Heavy Rain.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config: " + ChatColor.GREEN + "Obten la lista de comandos de configuracion");

        return stringHelpCommands;
    }

    private List<String> getListStringConfigCommands() {
        List<String> stringConfigCommands = new ArrayList<>();

        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config info: " + ChatColor.GREEN + "Obten informacion de la configuracion del plugin");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config timer (seconds): " + ChatColor.GREEN + "Cambia el tiempo entre cada evento de HeavyRain");

        return stringConfigCommands;
    }

    public boolean isCounterOn() {
        return CounterOn;
    }

    public void setCounterOn(boolean counterOn) {
        CounterOn = counterOn;
    }

    public long getSecondsTimer() {
        return secondsTimer;
    }

    public void setSecondsTimer(long secondsTimer) {
        this.secondsTimer = secondsTimer;
    }

    public long getSecondsMax() {
        return secondsMax;
    }

    public void setSecondsMax(long secondsMax) {
        this.secondsMax = secondsMax;
    }
}
