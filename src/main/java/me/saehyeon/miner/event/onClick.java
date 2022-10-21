package me.saehyeon.miner.event;

import me.saehyeon.miner.functions.Locationf;
import me.saehyeon.miner.main.PlayerGlobal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class onClick implements Listener {
    @EventHandler
    void onClick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if(p.getInventory().getItemInMainHand().getType() == Material.NAME_TAG) {

            Block b = e.getClickedBlock();

            if(b != null) {

                Location loc = b.getLocation();

                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                    e.setCancelled(true);
                    PlayerGlobal.pos1.put(p, e.getClickedBlock().getLocation());

                    p.sendMessage("§b§l블럭리젠: §f첫번째 지점이 지정되었습니다. ("+Locationf.toString(loc)+")");

                }

                else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    e.setCancelled(true);
                    PlayerGlobal.pos2.put(p, e.getClickedBlock().getLocation());

                    p.sendMessage("§b§l블럭리젠: §f두번째 지점이 지정되었습니다. ("+Locationf.toString(loc)+")");

                }

            }
        }
    }
}
