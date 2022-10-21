package me.saehyeon.miner.event;

import me.saehyeon.miner.functions.Locationf;
import me.saehyeon.miner.player.PlayerInfo;
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

        Player p        = e.getPlayer();
        PlayerInfo pi   = PlayerInfo.get(p);
        
        if(p.getInventory().getItemInMainHand().getType() == Material.NAME_TAG) {

            Block b = e.getClickedBlock();

            if(b != null) {

                Location blockLoc = e.getClickedBlock().getLocation();

                /* 첫번째 위치 지정 */
                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                    e.setCancelled(true);

                    pi.setFirstPosition( blockLoc );

                    p.sendMessage("§b§l블럭리젠: §f첫번째 지점이 지정되었습니다. ("+Locationf.toString(pi.getPosition()[0])+")");

                }

                /* 두번째 지점 지정 */
                else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    e.setCancelled(true);

                    pi.setSecondPosition(blockLoc);

                    p.sendMessage("§b§l블럭리젠: §f두번째 지점이 지정되었습니다. ("+Locationf.toString(pi.getPosition()[1])+")");

                }

            }
        }
    }
}
