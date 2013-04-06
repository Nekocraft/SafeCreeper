package com.timvisee.safecreeper.listener;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

import com.timvisee.safecreeper.SafeCreeper;
import com.timvisee.safecreeper.manager.SCConfigManager;

public class SCWorldListener implements Listener {
	
	@EventHandler
	public void onWorldLoad(WorldLoadEvent e) {
		World w = e.getWorld();
		Location l = w.getSpawnLocation();
		SCConfigManager cm = SafeCreeper.instance.getConfigManager();
		Random rand = new Random();
		
		// Check if it should always rain or thunder
		boolean alwaysRain = cm.getOptionBoolean(w, "WeatherControl", "AlwaysRain", false, true, l);
		boolean alwaysThunderStorm = cm.getOptionBoolean(w, "WeatherControl", "AlwaysThunderStorm", false, true, l);
		
		// If it should always rain, make it rain in the world
		if(alwaysRain && !w.hasStorm()) {
			// Make it storm in the world
			w.setStorm(true);
			
			// Get the default weather duration
			int duration = w.getWeatherDuration();
			
			// Apply the custom weather duration
			if(cm.getOptionBoolean(w, "WeatherControl", "CustomDurations.Enabled", false, true, l)) {
				if(cm.getOptionBoolean(w, "WeatherControl", "CustomDurations.Weather.Enabled", false, true, l)) {
					int minDuration = cm.getOptionInt(w, "WeatherControl", "CustomDurations.Weather.MinDuration", 720, true, l);
					int maxDuration = cm.getOptionInt(w, "WeatherControl", "CustomDurations.Weather.MaxDuration", 1080, true, l);
					
					duration = Math.max(minDuration + rand.nextInt(maxDuration - minDuration), 1);
				}
			}
			
			// Set the weather duration
			w.setWeatherDuration(duration);
		}
		
		// If it should always thunder, make it thunder in the world
		if(alwaysThunderStorm && !w.isThundering()) {
			// Make it thundering in the world
			w.setThundering(true);
			
			// Get the default thunder duration
			int duration = w.getThunderDuration();
			
			// Apply the custom thunder duration
			if(cm.getOptionBoolean(w, "WeatherControl", "CustomDurations.Enabled", false, true, l)) {
				if(cm.getOptionBoolean(w, "WeatherControl", "CustomDurations.ThunderStorm.Enabled", false, true, l)) {
					int minDuration = cm.getOptionInt(w, "WeatherControl", "CustomDurations.ThunderStorm.MinDuration", 240, true, l);
					int maxDuration = cm.getOptionInt(w, "WeatherControl", "CustomDurations.ThunderStorm.MaxDuration", 360, true, l);
					
					duration = Math.max(minDuration + rand.nextInt(maxDuration - minDuration), 1);
				}
			}
			
			// Set the thunder duration
			w.setThunderDuration(duration);
		}
		
		// Is rain, thunder and clear weather allowed in this world
		boolean clearAllowed = true;
		boolean rainAllowed = true;
		boolean thunderAllowed = true;
		if(cm.getOptionBoolean(w, "WeatherControl", "CustomWeatherTypes.Enabled", false, true, l)) {
			clearAllowed = cm.getOptionBoolean(w, "WeatherControl", "CustomWeatherTypes.Clear.Allowed", true, true, l);
			rainAllowed = cm.getOptionBoolean(w, "WeatherControl", "CustomWeatherTypes.Rain.Allowed", true, true, l);
			rainAllowed = cm.getOptionBoolean(w, "WeatherControl", "CustomWeatherTypes.ThunderStorm.Allowed", true, true, l);
		}
		
		// Is clear allowed
		if(!w.hasStorm() && !w.isThundering() && !clearAllowed)
			w.setStorm(true);
		
		// Is rain allowed
		if(w.hasStorm() && !rainAllowed)
			w.setStorm(false);
		
		// Is thunder allowed
		if(w.isThundering() && !thunderAllowed)
			w.setThundering(false);
	}
}
