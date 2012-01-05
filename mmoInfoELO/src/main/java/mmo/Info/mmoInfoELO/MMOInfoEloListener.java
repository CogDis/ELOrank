package mmo.Info.mmoInfoELO;


import org.getspout.spoutapi.SpoutManager;

import com.precipicegames.elorank.listener.RankChangeEvent;
import com.precipicegames.elorank.listener.RankEventListener;

public class MMOInfoEloListener extends RankEventListener {
	private MMOInfoElo plugin;
	public MMOInfoEloListener(MMOInfoElo p){
		plugin = p;
	}
	public void onRankChange(RankChangeEvent event) {
		plugin.updateDisplay(SpoutManager.getPlayer(event.getPlayer()), event.getRank());
	}

}
