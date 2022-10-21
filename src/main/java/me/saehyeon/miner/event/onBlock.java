package me.saehyeon.miner.event;

import me.saehyeon.miner.manager.Miner;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class onBlock implements Listener {
    @EventHandler
    void onBreak(BlockBreakEvent e) {
        Player p        = e.getPlayer();
        Location loc    = e.getBlock().getLocation();

        // 파괴한 블럭이 리젠 작업이 되어야 하는 블럭인지 확인
        if(Miner.isHaveToRegen(loc)) {

            /* 리젠되어야 하는 블럭임 -> 리젠하기 */


        }
    }
}
