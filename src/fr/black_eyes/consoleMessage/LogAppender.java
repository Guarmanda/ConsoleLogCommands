package fr.black_eyes.consoleMessage;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
// other imports that you need here
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;



public class LogAppender extends AbstractAppender {  
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    // your variables
   
    public LogAppender() {
        // do your calculations here before starting to capture
        super("MyLogAppender", null, null);
        start();
    }

    @Override
    public void append(LogEvent event) {
        // if you don`t make it immutable, than you may have some unexpected behaviours
            LogEvent log = event.toImmutable();

           // do what you have to do with the log

        // you can get only the log message like this:
       String line = log.getMessage().getFormattedMessage();

       // and you can construct your whole log message like this:
       if (log.getLoggerName().equals("Minecraft")) {
    	   line = "[" +formatter.format(new Date(event.getTimeMillis())) + " " + event.getLevel().toString() + "]: " + line;
       }
       else {
    	   line = "[" +formatter.format(new Date(event.getTimeMillis())) + " " + event.getLevel().toString() + "]: [" + log.getLoggerName() + "] " + line;
       }
       for(String message : Main.getInstance().getConfig().getConfigurationSection("messages").getKeys(false)) {
   		if(containsIgnoreCase(line, message)) {
   			for(String command : Main.getInstance().getConfig().getStringList("messages." + message)) {
   		       new BukkitRunnable() {       
   		           @Override
   		           public void run() {
   		        	   	Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command + " ");
   		           }                
   		       }.runTaskLater(Main.getInstance(), (long) 0.1);
   			
   			}
   		}
   	}
     
        
       
    }
    
    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }
}
