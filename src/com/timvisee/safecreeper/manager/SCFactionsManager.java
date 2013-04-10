package com.timvisee.safecreeper.manager;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;

public class SCFactionsManager {
	
	private Logger log;
	
	/**
	 * Constructor
	 * @param log Logger
	 */
	public SCFactionsManager(Logger log) {
		this.log = log;
	}
	
	/**
	 * Set up the Factions hook
	 */
	public void setup() {
		// Factions has to be installed/enabled
    	if(!Bukkit.getPluginManager().isPluginEnabled("Factions")) {
    		this.log.info("Disabling Factions usage, plugin not found.");
    		return;
    	}
    	
    	try {
    		// Get the Factions plugin
    		Plugin fPlugin = (Plugin) Bukkit.getPluginManager().getPlugin("Factions");
	        
    		// The factions plugin may not ben ull
	        if (fPlugin == null) {
	        	this.log.info("Unable to hook into Factions, plugin not found!");
	            return;
	        }
	        
	        // Hooked into Factions, show status message
	        this.log.info("Hooked into Factions!");
	        
    	} catch(NoClassDefFoundError ex) {
    		// Unable to hook into Factions, show warning/error message.
    		this.log.info("Error while hooking into Factions!");
    		return;
    	} catch(Exception ex) {
    		// Unable to hook into Factions, show warning/error message.
    		this.log.info("Error while hooking into Factions!");
    		return;
    	}
	}
	
	/**
	 * Get the logger instance
	 * @return Logger instance
	 */
	public Logger getLogger() {
		return this.log;
	}
	
	/**
	 * Set the logger instance
	 * @param log Logger instance
	 */
	public void setLogger(Logger log) {
		this.log = log;
	}
	
	/**
	 * Check if there's any faction ad a location
	 * @param loc Location to check
	 * @return True if there's any faction at the location
	 */
	public boolean isFactionAt(Location loc) {
    	Faction f = Board.getFactionAt(new FLocation(loc));
    	
    	// If returned null, there's no faction found on this area
    	if(f == null)
    		return false;
    	
    	// The faction area has to be 'normal'
    	return (f.isNormal());
    }
    
	/**
	 * Get a faction at a location
	 * @param loc Location to get the faction from
	 * @return Faction
	 */
    public String getFactionAt(Location loc) {
    	Faction f = Board.getFactionAt(new FLocation(loc));
    	
    	// If the faction area equals to null, theres not faction on this area
    	if(f == null)
    		return "";
    	
    	// The faction area has to be 'normal'
    	if(!f.isNormal())
    		return "";
    	
    	// Return the faction name
    	return f.getComparisonTag();
    }
}