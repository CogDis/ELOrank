package com.precipicegames.elorank.listener;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

public class RankEventListener extends CustomEventListener {
	public void onRankChange(RankChangeEvent event) {
		
	}
	public void onCustomEvent(Event event) {
		if(event instanceof RankChangeEvent) {
			onRankChange((RankChangeEvent) event);
		}
	}
}
