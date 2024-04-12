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
        // Register this class as an event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Check if the entity is an Ender Crystal
        if (!(event.getEntity() instanceof EnderCrystal)) return;
        // Check if the damager is a player
        if (!(event.getDamager() instanceof Player)) return;

        // Cancel the event immediately to prevent normal handling
        event.setCancelled(true);

        // Get the crystal and its location
        EnderCrystal crystal = (EnderCrystal) event.getEntity();
        Location loc = crystal.getLocation();
        World world = loc.getWorld();
        if (world == null) return;

        // Delay the actual explosion using a scheduled task
        getServer().getScheduler().runTaskLater(this, () -> {
            if (crystal.isDead()) return;  // Check if the crystal still exists
            crystal.remove();             // Remove the crystal
            world.createExplosion(loc, 4F, true);  // Create the explosion
        }, 5L);

    }
}
