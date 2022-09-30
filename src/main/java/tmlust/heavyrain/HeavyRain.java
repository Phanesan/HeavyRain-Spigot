package tmlust.heavyrain;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tmlust.heavyrain.commands.CommandMain;
import tmlust.heavyrain.files.Config;

public class HeavyRain extends JavaPlugin {

	private static HeavyRain plugin;
	private final String commandLabel = "heavyrain";

	@Override
	public void onEnable() {
		plugin = this;

		//Config setup
		Config.setup();

		//Commands setup
		CommandsSetup();
	}
	
	@Override
	public void onDisable() {
		
	}

	public static HeavyRain getPlugin() {
		return plugin;
	}
	public String getCommandLabel() {
		return commandLabel;
	}
	public void CommandsSetup(){
		plugin.getCommand("heavyrain").setExecutor(new CommandMain());
	}
}
