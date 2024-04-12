package de.lachcrafter.crystaldelay;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrystalDelay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderCrystal)) return;
        if (!(event.getDamager() instanceof Player)) return;

        event.setCancelled(true);

        EnderCrystal crystal = (EnderCrystal) event.getEntity();
        Location loc = crystal.getLocation();
        World world = loc.getWorld();
        if (world == null) return;

        getServer().getScheduler().runTaskLater(this, () -> {
            if (crystal.isDead()) return;
            crystal.remove();
            world.createExplosion(loc, 4F, true);
        }, 5L);

    }
}
