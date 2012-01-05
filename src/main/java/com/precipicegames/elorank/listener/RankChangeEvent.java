package com.precipicegames.elorank.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.precipicegames.elorank.PlayerRankEntity;

public class RankChangeEvent extends Event {

	private static final long serialVersionUID = 8928056802402693604L;
	private double rank;
	private PlayerRankEntity entity;
	private Player player;

	public RankChangeEvent(Player who, double rank,PlayerRankEntity entity) {
		super("RankChangeEvent");
		this.player = who;
		this.rank = rank;
		this.entity = entity;
	}

	public double getRank() {
		return rank;
	}
	public PlayerRankEntity getRankEntity() {
		return entity;
	}

	public Player getPlayer() {
		return player;
	}

}
