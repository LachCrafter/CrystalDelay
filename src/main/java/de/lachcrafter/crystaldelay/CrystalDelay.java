package de.lachcrafter.crystaldelay;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class CrystalDelay extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof EnderCrystal)) return;
        if (!(event.getDamager() instanceof Player)) return;

        event.setCancelled(true);
        EnderCrystal crystal = (EnderCrystal) event.getEntity();
        Location loc = crystal.getLocation();

        getServer().getScheduler().runTaskLater(this, () -> {
            World world = loc.getWorld();
            if (world != null) {
                crystal.remove(); // Remove the Ender Crystal
                world.createExplosion(loc, 4F, true);
            }
        }, 8L);
    }
}
