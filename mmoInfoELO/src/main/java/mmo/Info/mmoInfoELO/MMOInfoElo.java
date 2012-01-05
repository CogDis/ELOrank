/*
 * This file is part of mmoMinecraft (https://github.com/mmoMinecraftDev).
 *
 * mmoMinecraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mmo.Info.mmoInfoELO;

import java.util.HashMap;

import mmo.Core.MMOPlugin;
import mmo.Core.InfoAPI.MMOInfoEvent;
import mmo.Core.MMOListener;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.precipicegames.elorank.RankManager;
import com.precipicegames.elorank.eloplugin;

public class MMOInfoElo extends MMOPlugin {
	private HashMap<SpoutPlayer,GenericLabel> rankLabels;
	private RankManager rankman;
	private class mmoListen extends MMOListener {
		private JavaPlugin jplugin;
		public mmoListen(JavaPlugin jp) {
			this.jplugin = jp;
		}
		public void onMMOInfo(MMOInfoEvent event) {
			if (event.isToken("elo")) {
				SpoutPlayer p = event.getPlayer();
				event.setWidget(this.jplugin, updateDisplay(p,rankman.getPlayerRankEntity(p).getRank()));
			}
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		rankLabels = new HashMap<SpoutPlayer,GenericLabel>();
		
		Plugin plugin = this.getServer().getPluginManager().getPlugin("ELOrank");
		if(!(plugin instanceof eloplugin)) {
			return;
		}
		this.rankman = ((eloplugin) plugin).getRankManager();
		
		
		pm.registerEvent(Type.CUSTOM_EVENT,new mmoListen(this), Priority.Normal, this);
		
		MMOInfoEloListener listen = new MMOInfoEloListener(this);
		pm.registerEvent(Type.CUSTOM_EVENT, listen, Priority.Normal, plugin);
	}
	public Widget updateDisplay(SpoutPlayer player, double rank) {
		GenericLabel text = rankLabels.get(player);
		text = (text == null) ? (GenericLabel) new GenericLabel().setResize(true).setFixed(true) : text;
		if(text.getText() == null || !text.getText().equalsIgnoreCase(Double.toString(Math.round(rank))))
		{
			text.setText(Double.toString(Math.round(rank)));
			text.setDirty(true);
		}
		rankLabels.put(player, text);
		return text;
	}
}
