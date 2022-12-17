package tmlust.heavyrain;

import org.bukkit.plugin.java.JavaPlugin;
import tmlust.heavyrain.commands.CommandMain;
import tmlust.heavyrain.commands.TabCompleter;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.files.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class HeavyRain extends JavaPlugin {
	private final String commandLabel = "heavyrain";
	private final Config config = new Config(this);
	private final CommandMain commands = new CommandMain(this);
	private final Logger logger = getLogger();

	@Override
	public void onLoad() {
		Data dat;
		try {
			dat = Data.loadData(this, "/HeavyRainData.json");
			if(dat != null){
				commands.setCounterEnabled(dat.counterEnabled);
				commands.setSecondsTimer(dat.secondsTimer);
				commands.setSecondsMax(dat.secondsMax);
				logger.info("Datos cargados con exito!");
			}
		} catch (FileNotFoundException e) {
			logger.info("No se encontraron datos guardados, se cargaran los datos por defecto.");
			commands.setCounterEnabled(false);
			commands.setSecondsTimer(1);
			commands.setSecondsMax(28800); // 8 horas
		}
	}

	@Override
	public void onEnable() {

		//Config setup
		config.setup();

		//Commands setup
		CommandsSetup();

		// Scheduler timer
		if(commands.isCounterEnabled()) {
			commands.startTimer();
		}
	}

	@Override
	public void onDisable() {
		File file = new File(this.getDataFolder().getAbsolutePath() + "/HeavyRainData.json");
		if(file.exists()) {
			Data dat = new Data(commands.isCounterEnabled(), commands.getSecondsTimer(), commands.getSecondsMax());
			try {
				dat.saveData(this, "/HeavyRainData.json");
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getCommandLabel() {
		return commandLabel;
	}
	public void CommandsSetup(){
		getCommand("heavyrain").setExecutor(commands);
		getCommand("heavyrain").setTabCompleter(new TabCompleter());
	}
	public Config getConfigFile(){
		return config;
	}
}
