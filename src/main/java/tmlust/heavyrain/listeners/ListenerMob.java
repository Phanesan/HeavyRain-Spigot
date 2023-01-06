package tmlust.heavyrain.listeners;

import org.bukkit.entity.Endermite;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tmlust.heavyrain.HeavyRain;

public class ListenerMob implements Listener {

    private HeavyRain instance;

    public ListenerMob(HeavyRain instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if(instance.getData().isHeavyRainActivated() && e.getDamager() instanceof Player && (e.getEntity() instanceof Silverfish ||
                                                            e.getEntity() instanceof Endermite)) {

            ((Monster) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1, false, false));
        }
    }

}
