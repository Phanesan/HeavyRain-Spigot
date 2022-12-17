package tmlust.heavyrain.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import tmlust.heavyrain.HeavyRain;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.files.Data;
import tmlust.heavyrain.tasks.HeavyRainTask;
import tmlust.heavyrain.utilities.Utility;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CommandMain implements CommandExecutor {

    private final HeavyRain plugin;
    private final String commandLabel;
    private final Config config;
    private BukkitTask timer;
    private long secondsTimer;
    private boolean CounterEnabled;
    private long secondsMax;

    public CommandMain(HeavyRain plugin){
        this.plugin = plugin;
        commandLabel = plugin.getCommandLabel();
        config = plugin.getConfigFile();
        //secondsTimer = 1;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if(args.length == 0){
            printHelpCommands(sender);
            return true;
        } else {
            switch(args[0].toLowerCase()){
                case "timer":
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.YELLOW + "Ingresa " + ChatColor.DARK_GREEN + "on" + ChatColor.YELLOW + " para activar el contador, " +
                        "Ingresa " + ChatColor.DARK_RED + "off" + ChatColor.YELLOW + " para desactivar el contador");
                        return true;
                    }
                    switch(args[1].toLowerCase()){
                        case "on":
                            if(!CounterEnabled) {
                                CounterEnabled = true;
                                sender.sendMessage(ChatColor.GREEN + "El contador de la Heavy Rain ha sido iniciado");
                                startTimer();
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "El contador ya ha sido iniciado");
                            }
                            return true;
                        case "off":
                            if(CounterEnabled) {
                                CounterEnabled = false;
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
                                    String format = String.format("%02dh %02dm %02ds", secondsMax / 3600, (secondsMax % 3600) / 60, secondsMax % 60);
                                    sender.sendMessage(ChatColor.GREEN + "Tiempo configurado a cada " + format + ".");
                                } else {
                                    sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar numeros muy grandes");
                                }
                            } else {
                                sender.sendMessage(ChatColor.DARK_RED + "No puedes ingresar letras");
                            }
                            return true;
                        case "info":
                            String format;

                            if(CounterEnabled) {
                                sender.sendMessage(ChatColor.GREEN + "Heavy Rain:" + ChatColor.DARK_GREEN + " Activado");
                            } else {
                                sender.sendMessage(ChatColor.GREEN + "Heavy Rain:" + ChatColor.DARK_RED + " Desactivado");
                            }

                            format = String.format("%02dh %02dm %02ds", secondsMax / 3600, (secondsMax % 3600) / 60, secondsMax % 60);
                            sender.sendMessage(ChatColor.GREEN + "Tiempo entre eventos: " + ChatColor.YELLOW + format);

                            long secondsRemaining = secondsMax - secondsTimer;
                            format = String.format("%02dh %02dm %02ds", secondsRemaining / 3600, (secondsRemaining % 3600) / 60, secondsRemaining % 60);
                            sender.sendMessage(ChatColor.GREEN + "Tiempo faltante: " + ChatColor.YELLOW + format);
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

    public void startTimer() {
        timer = new BukkitRunnable() {
            @Override
            public void run() {
                secondsTimer++;
                if(secondsTimer > secondsMax){
                    new HeavyRainTask(plugin).runTask(plugin);
                    secondsTimer = 1;
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    public void stopTimer() {
        timer.cancel();
        Data dat = new Data(CounterEnabled, secondsTimer, secondsMax);
        try {
            dat.saveData(plugin, "/HeavyRainData.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getListStringHelpCommands(){
        List<String> stringHelpCommands = new ArrayList<>();

        stringHelpCommands.add(ChatColor.DARK_GREEN + "=========================================");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + ": " + ChatColor.GREEN + "Obten la lista de comandos.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " reload: " + ChatColor.GREEN + "Recarga las configuraciones.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " timer (on/off): " + ChatColor.GREEN + "Inicia la cuenta atras de la Heavy Rain.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config: " + ChatColor.GREEN + "Obten la lista de comandos de configuracion");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "=========================================");

        return stringHelpCommands;
    }

    private List<String> getListStringConfigHelpCommands() {
        List<String> stringConfigCommands = new ArrayList<>();

        stringConfigCommands.add(ChatColor.DARK_GREEN + "=========================================");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config info: " + ChatColor.GREEN + "Obten informacion de la configuracion del plugin");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " config timer (seconds): " + ChatColor.GREEN + "Cambia el tiempo entre cada evento de HeavyRain");
        stringConfigCommands.add(ChatColor.DARK_GREEN + "=========================================");

        return stringConfigCommands;
    }

    public boolean isCounterEnabled() {
        return CounterEnabled;
    }

    public void setCounterEnabled(boolean counterEnabled) {
        CounterEnabled = counterEnabled;
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
