package com.precipicegames.elorank;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerHandler extends PlayerListener {
	private RankManager manager;

	public PlayerHandler(RankManager rm){
		manager = rm;
	}
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		manager.scheduleSave(manager.getPlayerRankEntity(p));
	}
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		manager.checkPlayerTimeout(manager.getPlayerRankEntity(p));
	}
}
