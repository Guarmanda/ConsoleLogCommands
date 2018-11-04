package fr.black_eyes.consoleMessage;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private File configFile;
	private FileConfiguration config;
	private static Main instance;
	private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
	LogAppender appender = new LogAppender();
	
	

	
	public void onEnable() {
		instance = this;
		logger.removeAppender(appender);
		logger.addAppender(appender);
        super.onEnable();
        initFiles();
	}
	
	

	
	public static Main getInstance() {
        return instance;
    }
	public File getConfigF() {
		return this.configFile;
	}
	public FileConfiguration getConfig() {
		return this.config;
	}
	
	private boolean initFiles() {
		//config
	    configFile = new File(getDataFolder(), "config.yml");
	    if (!configFile.exists()) {
	        configFile.getParentFile().mkdirs();
	        saveResource("config.yml", false);
	    }
	    config= new YamlConfiguration();
	    try {
	        config.load(configFile);
	    } catch (IOException | InvalidConfigurationException e) {
	        e.printStackTrace();
	    }
		return true;
	}
}
