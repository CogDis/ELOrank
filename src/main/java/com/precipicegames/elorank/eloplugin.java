package com.precipicegames.elorank;


import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class eloplugin extends JavaPlugin {
	
	private RankManager rankmanager;
	public void onDisable() {
		// TODO Auto-generated method stub

	}
	
	public void onEnable() {
		
		//This class is basically the entire plugin, It is used to manage almost all aspects of the ELO system
		//For now lets create it without a configuration
		rankmanager = new RankManager(this, new MemoryConfiguration());
		
		
		//Create the ELO action listener, This object also has the ability to be scheduled in the bukkit system
		EntityELOListener elolisten = new EntityELOListener(rankmanager);
		this.getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, elolisten, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Type.ENTITY_DEATH, elolisten, Priority.Monitor, this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, elolisten, 60*20*5 , 60*20*5);

	}
	public RankManager getRankManager() {
		return rankmanager;
	}

}
