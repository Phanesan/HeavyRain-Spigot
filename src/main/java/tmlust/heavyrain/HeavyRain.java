package tmlust.heavyrain;

import org.bukkit.plugin.java.JavaPlugin;
import tmlust.heavyrain.commands.CommandMain;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.files.Data;

import java.io.File;

public class HeavyRain extends JavaPlugin {
	// Variables de la clase
	private final String commandLabel = "heavyrain";

	// Objetos de clase
	Config config = new Config(this);
	CommandMain commands = new CommandMain(this);

	@Override
	public void onLoad() {
		File fileDat = new File(getDataFolder().getAbsolutePath() + "/heavyrain.dat");
		Data dat;
		if(fileDat.exists()) {
			dat = Data.loadData("heavyrain.dat");
			commands.setCounterOn(dat.counterOn);
			commands.setSecondsTimer(dat.secondsTimer);
			commands.setSecondsMax(dat.secondsMax);
		}
	}

	@Override
	public void onEnable() {

		//Config setup
		config.setup();

		//Commands setup
		CommandsSetup();

		// Scheduler timer
		if(commands.isCounterOn()) {
			commands.startTimer();
		}
	}

	@Override
	public void onDisable() {

		Data dat = new Data(commands.isCounterOn(), commands.getSecondsTimer(), commands.getSecondsMax());
		dat.saveData("heavyrain.dat");

	}

	public String getCommandLabel() {
		return commandLabel;
	}
	public void CommandsSetup(){
		this.getCommand("heavyrain").setExecutor(commands);
	}
	public Config getConfigFile(){
		return config;
	}
}
