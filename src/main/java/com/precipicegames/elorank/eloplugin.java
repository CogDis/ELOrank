package com.precipicegames.elorank;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class eloplugin extends JavaPlugin {
	
	private RankManager rankmanager;
	public void onDisable() {
		rankmanager.saveAll(false);

	}
	
	public void onEnable() {
		
		//This class is basically the entire plugin, It is used to manage almost all aspects of the ELO system
		//For now lets create it without a configuration
		rankmanager = new RankManager(this, new MemoryConfiguration());
		
		
		//Create the ELO action listener, This object also has the ability to be scheduled in the bukkit system
		EntityELOListener elolisten = new EntityELOListener(rankmanager);
		PlayerHandler playerlisten = new PlayerHandler(rankmanager);
		this.getServer().getPluginManager().registerEvent(Type.ENTITY_DAMAGE, elolisten, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Type.ENTITY_DEATH, elolisten, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Type.PLAYER_QUIT, playerlisten, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Type.PLAYER_JOIN, playerlisten, Priority.Monitor, this);
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, elolisten, 60*20*5 , 60*20*5);

	}
	public RankManager getRankManager() {
		return rankmanager;
	}
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		boolean success = false;
		if(command.getName().equalsIgnoreCase("elo")) {
			if(sender instanceof Player && args.length == 0) {
				Player p = (Player) sender;
				p.sendMessage("Your ELO rank is: " + Math.round(this.rankmanager.getPlayerRankEntity(p).getRank()));
				success = true;
			}
		}
		return success;
	}

}
