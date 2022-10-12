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

import java.util.ArrayList;

public class CommandMain implements CommandExecutor {

    private final HeavyRain plugin;
    private final String commandLabel;
    private final Config config;
    BukkitScheduler scheduler = null;
    private int secondsTimer;
    private boolean CounterOn;

    public CommandMain(HeavyRain plugin){
        this.plugin = plugin;
        commandLabel = plugin.getCommandLabel();
        config = plugin.getConfigFile();
        secondsTimer = 1;
        CounterOn = false;
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

    public void startTimer() {
        scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            System.out.println(secondsTimer);
            secondsTimer++;

            if(secondsTimer > plugin.getConfigFile().getConfigFile().getInt("countdown")){
                BukkitTask heavyRainTask = new HeavyRainTask(plugin).runTask(plugin);
                secondsTimer = 1;
            }
        }, 0, 20);
    }

    public void stopTimer() {
        scheduler.cancelTasks(plugin);
        Data dat = new Data(CounterOn, secondsTimer);
        dat.saveData("heavyrain.dat");
    }

    private ArrayList<String> getListStringHelpCommands(){
        ArrayList<String> stringHelpCommands = new ArrayList<>();

        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + ": " + ChatColor.GREEN + "Obten la lista de comandos.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " reload: " + ChatColor.GREEN + "Recarga las configuraciones.");
        stringHelpCommands.add(ChatColor.DARK_GREEN + "/" + commandLabel + " timer (on/off): " + ChatColor.GREEN + "Inicia la cuenta atras de la Heavy Rain.");

        return stringHelpCommands;
    }

    public boolean isCounterOn() {
        return CounterOn;
    }

    public void setCounterOn(boolean counterOn) {
        CounterOn = counterOn;
    }

    public int getSecondsTimer() {
        return secondsTimer;
    }

    public void setSecondsTimer(int secondsTimer) {
        this.secondsTimer = secondsTimer;
    }

}
