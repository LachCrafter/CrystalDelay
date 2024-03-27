package de.lachcrafter.crystaldelay;

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
        if (event.getEntity() instanceof EnderCrystal) {
            if (event.getDamager() instanceof Player) {
                event.setCancelled(true);
                EnderCrystal crystal = (EnderCrystal) event.getEntity();
                Bukkit.getScheduler().runTaskLater(this, () -> crystal.remove(), 6L); // 6 ticks = 0.3 seconds
            }
        }
    }
}
