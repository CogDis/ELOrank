package com.precipicegames.elorank;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;


public class EntityELOListener extends EntityListener implements Runnable {
	private RankManager rankman; 
	private HashMap<Entity,LastHit> hitlist;
	public class LastHit {
		Player hitter;
		long time;
		public LastHit(Player p) {
			hitter = p;
			time = System.currentTimeMillis();
		}
	}
	public EntityELOListener(RankManager man) {
		rankman = man;
	}
	public void onEntityDeath(EntityDeathEvent event) {
		if(!(event.getEntity() instanceof Player || event.getEntity() instanceof Monster))
			return;
		
		LastHit lh = hitlist.get(event.getEntity());
		if(lh != null && System.currentTimeMillis() - lh.time < 1000*60) {
			if(event.getEntity() instanceof Player) {
				rankman.performupdate(lh.hitter,(Player)event.getEntity());
			} else {
				rankman.performupdate(lh.hitter,null);
			}
			hitlist.remove(lh);
		} else {
			if(event.getEntity() instanceof Player) {
				rankman.performupdate(null,(Player)event.getEntity());
			}
		}
	}
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent devent = (EntityDamageByEntityEvent) event;
			Entity hitter = devent.getDamager();
			hitter = (hitter instanceof Projectile) ? ((Projectile) hitter).getShooter() : hitter;
			if(hitter instanceof Player) {
				LastHit lh = new LastHit((Player)hitter);
				hitlist.get(lh);
			}
		}
	}
	public void run() {
		Iterator<Entity> itr = hitlist.keySet().iterator();
		while(itr.hasNext()) {
			Entity e = itr.next();
			LastHit lh = hitlist.get(e);
			if(System.currentTimeMillis() - lh.time > 1000*60) {
				itr.remove();
			}
		}
	}
}
