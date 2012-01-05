package com.precipicegames.elorank.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import com.precipicegames.elorank.PlayerRankEntity;

public class RankChangeEvent extends PlayerEvent {

	private static final long serialVersionUID = 8928056802402693604L;
	private double rank;
	private PlayerRankEntity entity;

	public RankChangeEvent(Player who, double rank,PlayerRankEntity entity) {
		super(Type.CUSTOM_EVENT, who);
		this.rank = rank;
		this.entity = entity;
	}

	public double getRank() {
		return rank;
	}
	public PlayerRankEntity getRankEntity() {
		return entity;
	}

}
