package tmlust.heavyrain;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import tmlust.heavyrain.files.Config;

public class HeavyRain extends JavaPlugin {

	private static HeavyRain instance;

	@Override
	public void onEnable() {
		Config.setup();
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static HeavyRain getInstance() {
		return instance;
	}
}
