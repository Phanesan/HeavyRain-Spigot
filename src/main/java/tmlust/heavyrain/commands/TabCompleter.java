package tmlust.heavyrain.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import tmlust.heavyrain.HeavyRain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private HeavyRain instance;

    public TabCompleter(HeavyRain instance) {
        this.instance = instance;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 1) {

            List<String> arguments = new ArrayList<>();
            arguments.add("reload");
            arguments.add("enable");
            arguments.add("config");
            arguments.add("give");

            return checkArgs(args, 0, arguments);

        } else if(args.length == 2) {

            if(args[0].equalsIgnoreCase("enable")) {
                List<String> arguments = new ArrayList<>();
                arguments.add("on");
                arguments.add("off");

                return checkArgs(args, 1, arguments);

            } else if (args[0].equalsIgnoreCase("config")) {

                List<String> arguments = new ArrayList<>();
                arguments.add("info");
                arguments.add("each");
                arguments.add("time");

                return checkArgs(args, 1, arguments);

            } else if (args[0].equalsIgnoreCase("give")) {

                return instance.getItems().getListItem();

            }
        }

        return Collections.EMPTY_LIST;
    }

    /**
     * Valida si lo que escribio el jugador es un argumento valido para enviar un TabCompleter vacio
     * @param args Cadena de argumentos escritos por el jugador
     * @param posArgValidate Posicion del argumento que validara
     * @param validArgs Lista de argumentos validos
     * @return Una lista vacia en caso de encontrar un argumento valido por el usuario o una lista con
     * los argumentos en caso de que el argumento escrito no sea valido
     */
    private List<String> checkArgs(String[] args, int posArgValidate, List<String> validArgs) {
        for(String s : validArgs) {
            if(args[posArgValidate].equalsIgnoreCase(s)) {
                return Collections.EMPTY_LIST;
            }
        }
        return validArgs;
    }
}
