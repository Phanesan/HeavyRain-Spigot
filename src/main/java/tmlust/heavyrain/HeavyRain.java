package tmlust.heavyrain;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tmlust.heavyrain.listeners.ListenerCancel;
import tmlust.heavyrain.listeners.ListenerMobDeath;
import tmlust.heavyrain.listeners.ListenerMobSpawn;
import tmlust.heavyrain.files.Config;
import tmlust.heavyrain.listeners.ListenerMob;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class HeavyRain extends JavaPlugin {
	private String commandLabel;
	private Data data;
	private Config config;
	private Commands commands;
	private Items items;
	private Recipes recipes;
	private Logger logger;
	private ThreadManager threadManager;

	@Override
	public void onLoad() {
		// Init Load
		commandLabel = "heavyrain";
		logger = getLogger();
		config = new Config(this);
		commands = new Commands(this);
		items = new Items();
		recipes = new Recipes(this);
		threadManager = new ThreadManager(this);
		data = new Data(this);

		// Data load
		try {
			data.insertData(Data.loadData(this, "/HeavyRainData.json"));
			logger.info("Datos cargados con exito!");
		} catch (FileNotFoundException e) {
			logger.info("No se encontraron datos guardados, se cargaran los datos por defecto.");
			data.createDataDefault();
		}
	}

	@Override
	public void onEnable() {

		data.applyData();

		CommandsInit();

		ListenersInit();

	}

	@Override
	public void onDisable() {
		try {
			data.saveData(this,"/HeavyRainData.json");
		} catch (IOException e) {
			logger.warning("Algo salio mal y no se pudieron guardar los datos, se cargaran los datos por defecto.");
			data.createDataDefault();

			try {
				data.saveData(this,"/HeavyRainData.json");
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public void CommandsInit(){
		getCommand("heavyrain").setExecutor(commands);
		getCommand("heavyrain").setTabCompleter(new TabCompleter(this));
	}
	public void ListenersInit() {
		Bukkit.getPluginManager().registerEvents(new ListenerCancel(this),this);
		Bukkit.getPluginManager().registerEvents(new ListenerMobSpawn(this), this);
		Bukkit.getPluginManager().registerEvents(new ListenerMob(this), this);
		Bukkit.getPluginManager().registerEvents(new ListenerMobDeath(this), this);
	}
	public Data getData() {
		return data;
	}
	public String getCommandLabel() {
		return commandLabel;
	}
	public Config getConfigFile(){
		return config;
	}
	public Commands getCommands() {
		return commands;
	}
	public Items getItems() {
		return items;
	}
	public ThreadManager getThreadManager() {
		return threadManager;
	}
}
