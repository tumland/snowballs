package com.coffeejawa.Snowball;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Snowballs extends JavaPlugin {
    public final Logger logger = Logger.getLogger("Minecraft");
    
    @Override
    public void onEnable() 
    {        
        this.reloadConfig();
        getServer().getPluginManager().registerEvents(new SnowballListener(this), this);
        logger.info("[" + this.getDescription().getName() +  "] enabled.");
        
    }
    
    @Override
    public void onDisable() 
    {
        logger.info("[" + this.getDescription().getName() +  "] disabled.");  
        this.saveConfig();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String name = cmd.getName();
        if(!name.equalsIgnoreCase("snowball") && !name.equalsIgnoreCase("sb")){
            return false;
        }
        if(args.length == 0){
            if(!sender.hasPermission("sb.help")){
                sender.sendMessage("Error: permission required: sb.help");
            }
            
            sender.sendMessage("Snowballs Command Interface");
            sender.sendMessage("Usage: /sb <on|off> [freeze time]");
            return false;
        }
        String firstArg = args[0];
        if(firstArg.equalsIgnoreCase("on")){
            if(args.length < 1){
                sender.sendMessage("Usage: /sb on [freeze time]");
                sender.sendMessage("Description: enable freezing. Default time is 5 seconds");
                return true;
            }
            if(!sender.hasPermission("sb.control")){
                sender.sendMessage("Error: permission required: sb.control");
                return true;
            }
            int time = 5;
            if(args.length == 2){
                time = Integer.parseInt(args[1]);
                
            }
            this.getConfig().set("freezeTime", time);
            this.getConfig().set("enabled", true);
            
            sender.sendMessage("Snowballs will now freeze your enemies for " + time + " seconds!");
            return true;
        }
        else if(firstArg.equalsIgnoreCase("off")){
            if(!sender.hasPermission("sb.control")){
                sender.sendMessage("Error: permission required: sb.control");
                return true;
            }
            this.getConfig().set("enabled", false);
            sender.sendMessage("Snowballs freeze is now off.");
            return true;
        }
        
        return true;
    }
}
